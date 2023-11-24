package vn.edu.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.dto.DTOCartList;
import vn.edu.iuh.fit.backend.dto.ProductWithNewestPrice;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.repositories.ProductPriceRepository;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;
import vn.edu.iuh.fit.backend.services.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/client")
@AllArgsConstructor
public class ClientControllers {
    private ProductRepository productRepository;
    private ProductService productService;
    private ProductPriceRepository productPriceRepository;

    @GetMapping("")
    public String openClientPage(){
        return "client/client";
    }

    @GetMapping("/products")
    public String openProductList(Model model){
        Page<Product> products = productRepository.findAllByStatusNot(ProductStatus.TERMINATED, Pageable.unpaged());
        List<ProductWithNewestPrice> productWithNewestPrices = new ArrayList<>();
        for(Product product : products){
            ProductPrice productNewestPrice = productPriceRepository
                    .findProductPriceNewestByProductID(product.getProduct_id()).orElse(null);
            if (productNewestPrice == null){
                continue;
            }
            productWithNewestPrices.add(new ProductWithNewestPrice(product, productNewestPrice));
        }
        model.addAttribute("productWithNewestPrices", productWithNewestPrices);
        return "client/productListPage";
    }
    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("productID")long productID, @RequestParam("quantity") int quantity,
                            HttpServletRequest request, Model model){
        if (quantity <= 0){
            System.out.println(quantity);
            model.addAttribute("noticeAddToCart", "quantity phải > 0");
            return "redirect:/client/products";
        }
        HashMap<Long, Integer> mapCarts = new HashMap<>();
        HttpSession session = request.getSession();
        Object carts = session.getAttribute("carts");
        if (carts == null){
            session.setAttribute("carts", mapCarts);
        }
        HashMap<Long, Integer> cartSession = (HashMap<Long, Integer>) session.getAttribute("carts");
        int newQuantity = cartSession.containsKey(productID) ? cartSession.get(productID) + quantity : quantity;
        cartSession.put(productID, newQuantity);
        session.setAttribute("carts", cartSession);
        model.addAttribute("noticeAddToCart", "Thêm thành công");
        return "redirect:/client/products";
    }
    @GetMapping("/carts")
    public String openCartsList(Model model, HttpServletRequest request){
        Object carts = request.getSession().getAttribute("carts");
        if (carts == null){
            return "redirect:/client/products";
        }
        HashMap<Long, Integer> cartsMap = (HashMap<Long, Integer>) carts;

        List<DTOCartList> listCarts = new ArrayList<>();
        cartsMap.entrySet().stream().forEach((e)-> {
            Product product = productRepository.findById(e.getKey()).orElse(null);
            ProductPrice productPrice = productPriceRepository
                    .findProductPriceNewestByProductID(product.getProduct_id()).orElse(null);
            int quantity = e.getValue();
            listCarts.add(new DTOCartList(product, productPrice.getPrice() * quantity, quantity));
        });
        model.addAttribute("carts", listCarts);
        return "client/cartList";
    }
    @GetMapping("/deleteItemCart/{id}")
    public String handleDeleteItem(@PathVariable("id") long productID, Model model, HttpServletRequest request){
        Object carts = request.getSession().getAttribute("carts");
        if (carts == null){
            return "redirect:/client/products";
        }
        HashMap<Long, Integer> cartsMap = (HashMap<Long, Integer>) carts;
        cartsMap.remove(productID);
        request.getSession().setAttribute("carts", cartsMap);
        return "redirect:/client/carts";
    }
    @GetMapping("/increase/{id}")
    public String handleIncreaseForItemCart(@PathVariable("id") long productID, Model model
            , HttpServletRequest request){
        Object carts = request.getSession().getAttribute("carts");
        if (carts == null){
            return "redirect:/client/products";
        }
        HashMap<Long, Integer> cartsMap = (HashMap<Long, Integer>) carts;
        if (cartsMap.containsKey(productID)){
            int newQuantity = cartsMap.get(productID) + 1;
            cartsMap.put(productID, newQuantity);
        }
        request.getSession().setAttribute("carts", cartsMap);
        return "redirect:/client/carts";
    }
    @GetMapping("/decrease/{id}")
    public String handleDecreaseForItemCart(@PathVariable("id") long productID, Model model
            , HttpServletRequest request){
        Object carts = request.getSession().getAttribute("carts");
        if (carts == null){
            return "redirect:/client/products";
        }
        HashMap<Long, Integer> cartsMap = (HashMap<Long, Integer>) carts;
        if (cartsMap.containsKey(productID)){
            if (cartsMap.get(productID) == 1){
                return "redirect:/client/carts";
            }
            int newQuantity = cartsMap.get(productID) - 1;
            cartsMap.put(productID, newQuantity);
        }
        request.getSession().setAttribute("carts", cartsMap);
        return "redirect:/client/carts";
    }

}
