package com.example.fptemployee.Controller;

import com.example.fptemployee.Entity.Employee;
import com.example.fptemployee.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/search")
    public String searchEmployees(@RequestParam("keyword") String keyword, Model model) {
        List<Employee> employees = employeeRepository.findByNameContainingIgnoreCase(keyword);
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employees/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employees-add";
    }

    @PostMapping("/employees/add")
    public String addEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "employees-add";
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!employee.getEmail().matches(emailRegex)) {
            result.rejectValue("email", "error.employee", "Invalid email format");
            return "employees-add";
        }

        employeeRepository.save(employee);

        return "redirect:/employees";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/employees/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            model.addAttribute("employee", optionalEmployee.get());
            return "employees-edit";
        } else {
            return "redirect:/employees";
        }
    }

    @PostMapping("/employees/edit/{id}")
    public String editEmployee(@PathVariable("id") Long id, @ModelAttribute("employee") @Valid Employee updatedEmployee,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "employees-edit";
        }
        
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!updatedEmployee.getEmail().matches(emailRegex)) {
            result.rejectValue("email", "error.employee", "Invalid email format");
            return "employees-edit";
        }
        
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setBirthday(updatedEmployee.getBirthday());
            existingEmployee.setNumberphone(updatedEmployee.getNumberphone());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            employeeRepository.save(existingEmployee);
            return "redirect:/employees";
        } else {
            return "redirect:/employees";
        }
    }
    @PostMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }
}
