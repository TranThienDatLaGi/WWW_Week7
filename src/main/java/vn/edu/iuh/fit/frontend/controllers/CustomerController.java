package vn.edu.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.models.Customer;
import vn.edu.iuh.fit.backend.repositories.CustomerRepository;
import vn.edu.iuh.fit.backend.services.CustomerService;

@Controller
@RequestMapping("/admin/customers")
public class CustomerController {
    private CustomerRepository customerRepository;
    private CustomerService customerService;
    @Autowired
    public CustomerController(CustomerRepository customerRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }


    @GetMapping({"/viewAllCustomer", ""})
    public String viewAllCustomer(Model model){
        System.out.println(customerRepository.findAll());
        model.addAttribute("customers", customerRepository.findAll());
        return "admin/customers/crudCustomer";
    }
    @GetMapping("/add")
    public ModelAndView handleOpenAddCustomerPage(){
        ModelAndView modelAndView = new ModelAndView();
        Customer customer = new Customer();
        modelAndView.addObject("customer", customer);
        modelAndView.setViewName("admin/customers/addCustomer");

        return modelAndView;
    }
    @PostMapping("/add")
    public String handleAddCustomer(@ModelAttribute("customer") Customer customer, Model model){

        try {
            customerRepository.save(customer);
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errAddCust", "Email đã tồn tại!");
            return "admin/customers/addCustomer";
        }
        return "redirect:/admin/customers";
    }
    @GetMapping("/edit/{id}")
    public ModelAndView handleOpenEditCustomer(@PathVariable("id") long customerID){

        Customer customer = customerRepository.findById(customerID).orElse(null);
        if (customer == null){
            return new ModelAndView("redirect:/admin/customers");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(customer);
        modelAndView.setViewName("admin/customers/editCustomer");
        return modelAndView;
    }
    @PostMapping("/edit/{id}")
    public String handleUpdateCustomer(@PathVariable("id") long customerID
            , @ModelAttribute("customer") Customer customer, Model model){
        try {
            Customer newCustomer = customerRepository.save(customer);
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errUpdCust", "Email đã tồn tại!");
            return "admin/customers/editCustomer";
        }
        return "redirect:/admin/customers";
    }
    @GetMapping("/delete/{id}")
    public String handleDeleteCustomer(@PathVariable("id") long customerID){
        customerRepository.deleteById(customerID);
        return "redirect:/admin/customers";
    }
}
