package vn.edu.iuh.fit.frontend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.models.OrderDetail;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.pks.OrderDetailPK;
import vn.edu.iuh.fit.backend.repositories.OrderDetailRepository;
import vn.edu.iuh.fit.backend.repositories.OrderRepository;
import vn.edu.iuh.fit.backend.repositories.ProductPriceRepository;
import vn.edu.iuh.fit.backend.repositories.ProductRepository;
import vn.edu.iuh.fit.backend.services.OrderDetailService;

import java.util.List;

@Controller
@RequestMapping("/admin/order-details")
@AllArgsConstructor
public class OrderDetailController {
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private ProductRepository productRepository;
    private OrderDetailService orderDetailService;
    private ProductPriceRepository productPriceRepository;

    @GetMapping("/{id}")
    public String handleOpenOrderDetailPage(Model model, @PathVariable("id") long orderID){
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrder_Order_id(orderID);
        Order order = orderRepository.findById(orderID).orElse(null);
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetails);
        return "admin/order-details/crudOrderDetails";
    }
    @GetMapping("/add/{id}")
    public ModelAndView handleOpenAddDetailPage(@PathVariable("id") long orderID){
        ModelAndView modelAndView = new ModelAndView();
        Order order = orderRepository.findById(orderID).orElse(null);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        modelAndView.addObject("orderDetail", orderDetail);
        modelAndView.setViewName("admin/order-details/addOrderDetail");
        return modelAndView;
    }
    @PostMapping("/add")
    public String handleAddDetailPage(@ModelAttribute("orderDetail") OrderDetail orderDetail, Model model){
        Product product = productRepository.findById(orderDetail.getProduct().getProduct_id()).orElse(null);
        if (product == null){
            model.addAttribute("errAddOrderDetail", "Product ID không tồn tại!");
            return "admin/order-details/addOrderDetail";
        }
        OrderDetail orderDetailCheckExists = orderDetailRepository.findById(
                new OrderDetailPK(
                orderDetail.getOrder().getOrder_id(), orderDetail.getProduct().getProduct_id()
        )).orElse(null);
        if (orderDetailCheckExists != null){
            model.addAttribute("errAddOrderDetail", "Order Detail này đã tồn tại!");
            return "admin/order-details/addOrderDetail";
        }
        ProductPrice productPrice = productPriceRepository.findProductPriceNewestByProductID(orderDetail.getProduct().getProduct_id()).orElse(null);
        orderDetail.setPrice(productPrice.getPrice());
        orderDetailRepository.save(orderDetail);
        return "redirect:/admin/order-details/" + orderDetail.getOrder().getOrder_id();
    }
    //@RequestParam("orderID") long orderID
    //            , @RequestParam("productID") long productID
    @GetMapping("/edit")
    public ModelAndView handleOpenEditPage(@RequestParam("orderID") long orderID
        , @RequestParam("productID") long productID){
        ModelAndView modelAndView = new ModelAndView();
        OrderDetail orderDetail = orderDetailRepository.findById(new OrderDetailPK(
                orderID, productID
        )).orElse(null);
        modelAndView.addObject("orderDetail", orderDetail);
        modelAndView.setViewName("admin/order-details/editOrderDetail");
        return modelAndView;
    }
    @PostMapping("/edit")
    public String handleEditOrderDetail(@ModelAttribute("orderDetail") OrderDetail orderDetail, Model model){
        Product product = productRepository.findById(orderDetail.getProduct().getProduct_id()).orElse(null);

        ProductPrice productPrice = productPriceRepository.findProductPriceNewestByProductID(orderDetail.getProduct()
                .getProduct_id()).orElse(null);
        orderDetail.setPrice(productPrice.getPrice());
        orderDetailRepository.save(orderDetail);
        return "redirect:/admin/order-details/" + orderDetail.getOrder().getOrder_id();
    }
    @GetMapping("/delete")
    public String handleDeleteOrderDetail(@RequestParam("orderID") long orderID
            , @RequestParam("productID") long productID, Model model){
        orderDetailRepository.deleteById(new OrderDetailPK(orderID, productID));
        return "redirect:/admin/order-details/" + orderID;
    }
}
