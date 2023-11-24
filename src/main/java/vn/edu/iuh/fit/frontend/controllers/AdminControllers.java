package vn.edu.iuh.fit.frontend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.iuh.fit.backend.models.Employee;
import vn.edu.iuh.fit.backend.repositories.EmployeeRepository;
import vn.edu.iuh.fit.backend.repositories.OrderCount;
import vn.edu.iuh.fit.backend.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminControllers {
    @GetMapping("")
    public String showAdminPage(){
        return "/admin/admin-page";
    }

}
