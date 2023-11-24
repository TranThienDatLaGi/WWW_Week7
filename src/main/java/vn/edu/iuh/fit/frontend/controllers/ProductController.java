package vn.edu.iuh.fit.frontend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.dto.ProductWithNewestPrice;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.repositories.ProductImageRepository;
import vn.edu.iuh.fit.backend.repositories.ProductPriceRepository;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;
import vn.edu.iuh.fit.backend.services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin/products")
@AllArgsConstructor
public class ProductController {
    private ProductRepository productRepository;
    private ProductService productService;
    private ProductPriceRepository productPriceRepository;
    private Environment environment;
    private ProductImageRepository productImageRepository;


    @GetMapping("")
    public String showProductListPaging(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size)
    {
        int pageNum = page.orElse(1);
        int sizeNum = size.orElse(10);

        Page<Product> products = productService.findPaginatedSortByIndex(pageNum - 1,
                sizeNum, "product_id", "asc");

//        Start Handle Pagination
        int currentPage = products.getNumber() + 1;
        int totalPages = products.getTotalPages();

        int start = Math.max(1, currentPage - 1);
        int end = Math.min(totalPages, start + 2);

        if (totalPages == end)
            start -= 1;

        model.addAttribute("pagesFirst", IntStream.range(1, Math.min(4, start)).boxed().toList());
        model.addAttribute("showFirst", start > 4);
        model.addAttribute("pagesCurrent", IntStream.range(start, end + 1).boxed().toList());
        model.addAttribute("showLast", end < Math.max(end + 1, totalPages - 2) - 1);
        model.addAttribute("pagesLast", IntStream.range(Math.max(end + 1, totalPages - 2), totalPages + 1).boxed().toList());
//        End Handle Pagination
        model.addAttribute("products", products);

        List<ProductWithNewestPrice> productDTO = new ArrayList<>();
        for (Product p : products){
            ProductPrice productPrice = productPriceRepository
                    .findProductPriceNewestByProductID(p.getProduct_id()).orElse(null);
            if (productPrice != null){
                productDTO.add(new ProductWithNewestPrice(p, productPrice));
            }
        }
        model.addAttribute("products", products);
        model.addAttribute("productWithNewestPrices", productDTO);
        return "admin/products/Products";
    }
    @GetMapping("/delete/{id}")
    public String handleHiddenProduct(@PathVariable("id") long productID){
        productService.hiddenProduct(productID);
        return "redirect:/admin/products";
    }
    @GetMapping("/edit/{id}")
    public ModelAndView handleOpenProductEditPage(@PathVariable("id") long productID){
        ModelAndView modelAndView = new ModelAndView();
        Product product = productRepository.findById(productID).orElse(null);
        if (product == null){
            return new ModelAndView("redirect:/admin/products");
        }
        modelAndView.addObject("product", product);
        modelAndView.addObject("productStatuss", ProductStatus.values());
        modelAndView.setViewName("admin/products/editProduct");
        return modelAndView;
    }
    @PostMapping("/edit")
    public String handleEditProduct(@ModelAttribute("product") Product product, Model model){
        try {
            productRepository.save(product);
        } catch (Exception e){
            model.addAttribute("errUpdProduct", "Cập nhật thất bại!");
            return "admin/products/editProduct";
        }
        return "redirect:/admin/products";
    }

}
