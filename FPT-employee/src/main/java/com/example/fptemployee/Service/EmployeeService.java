package com.example.fptemployee.Service;

import com.example.fptemployee.Entity.Employee;
import com.example.fptemployee.Repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository.findByKeyword(keyword);
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}