package vn.edu.iuh.fit.frontend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.pks.ProductPricePK;
import vn.edu.iuh.fit.backend.repositories.ProductPriceRepository;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin/product-prices")
@AllArgsConstructor
public class ProductPriceController {
    private ProductPriceRepository productPriceRepository;
    private ProductRepository productRepository;

    @GetMapping("/{id}")
    public String handleOpenProductPricesPage(@PathVariable("id") long productID, Model model){
        Product product = productRepository.findById(productID).orElse(null);
        List<ProductPrice> productPrices = productPriceRepository.findProductPricesByProduct_Product_id(productID);
        model.addAttribute("productPrices", productPrices);
        model.addAttribute("product", product);
        return "admin/product-prices/crudProductPrices";
    }
    @GetMapping("/add/{id}")
    public ModelAndView handleOpenAddProductPricesPage(@PathVariable("id") long productID){
        ModelAndView modelAndView = new ModelAndView();
        ProductPrice productPrice = new ProductPrice();
        Product product = productRepository.findById(productID).orElse(null);
        productPrice.setProduct(product);
        modelAndView.addObject("productPrice", productPrice);
        modelAndView.setViewName("admin/product-prices/addProductPrices");
        return modelAndView;
    }
    @GetMapping("/edit")
    public ModelAndView handleOpenEditPage(@RequestParam("productID") long productID,
                                           @RequestParam("date")LocalDateTime price_date_time){
        ModelAndView modelAndView = new ModelAndView();
        ProductPrice productPrice = productPriceRepository.findById(
                new ProductPricePK(productID, price_date_time)
        ).orElse(null);

        modelAndView.addObject("productPrice", productPrice);
        modelAndView.setViewName("admin/product-prices/editProductPrices");
        return modelAndView;
    }
    @PostMapping("/edit")
    public String handleEditProductPrice(@ModelAttribute("productPrice") ProductPrice productPrice
    , Model model){
        try {
            productPriceRepository.save(productPrice);
        } catch (Exception e){
            model.addAttribute("errUpdProductPrice", "Cập nhật thất bại!");
            return "admin/product-prices/editProductPrices";
        }
        return "redirect:/admin/product-prices/" + productPrice.getProduct().getProduct_id();
    }
    @PostMapping("/add/{id}")
    public String handleAddProductPrice(@ModelAttribute("productPrice") ProductPrice productPrice
    , @PathVariable("id") long productID, Model model){
        try {
            productPriceRepository.save(productPrice);
        } catch (Exception e){
            model.addAttribute("errAddProductPrice", "Thêm thất bại!");
            return "admin/product-prices/addProductPrices";
        }
        return "redirect:/admin/product-prices/" + productID;
    }
    @GetMapping("/delete")
    public String handleDeleteProductPrice(@RequestParam("productID") long productID,
                                           @RequestParam("date")LocalDateTime price_date_time){
        productPriceRepository.deleteById(new ProductPricePK(productID, price_date_time));
        return "redirect:/admin/product-prices/" + productID;
    }

}
