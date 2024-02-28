package com.example.fptemployee.Controller;

import com.example.fptemployee.Entity.Employee;
import com.example.fptemployee.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employees/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    @PostMapping("/employees/add")
    public String addEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "add-employee";
        }

        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/search")
    public String searchEmployees(@RequestParam("keyword") String keyword, Model model) {
        List<Employee> employees = employeeRepository.findByNameContainingIgnoreCase(keyword);
        model.addAttribute("employees", employees);
        return "search-results";
    }
}