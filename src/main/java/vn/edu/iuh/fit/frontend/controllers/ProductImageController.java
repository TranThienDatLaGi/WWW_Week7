package vn.edu.iuh.fit.frontend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductImage;
import vn.edu.iuh.fit.backend.repositories.ProductImageRepository;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;
import java.util.List;

@Controller
@RequestMapping("/admin/product-images")
@AllArgsConstructor
public class ProductImageController {
    private ProductRepository productRepository;
    private ProductImageRepository productImageRepository;
    @GetMapping("/{id}")
    public String handleOpenListProductImages(@PathVariable("id") long productID, Model model){
        List<ProductImage> productImages = productImageRepository.findProductImagesByProductID(productID);
        Product product = productRepository.findById(productID).orElse(null);
        model.addAttribute("product", product);
        model.addAttribute("productImages", productImages);
        return "admin/product-images/crudProductImages";
    }

    @GetMapping("/delete/{id}")
    public String handleDeleteProductImage(@PathVariable("id") long imageID, Model model){
        ProductImage productImage = productImageRepository.findById(imageID).orElse(null);
        long productID = productImage.getProduct().getProduct_id();
        productImageRepository.deleteById(imageID);
        return "redirect:/admin/product-images/" + productID;
    }
}
