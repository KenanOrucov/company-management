package com.example.management.service;

import com.example.management.entity.Branch;
import com.example.management.entity.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    List<Employee> getAll();
    Employee getEmployeeById(Long id);
    List<Employee> getEmployeeByName(String name);
    Employee createEmployee(Long id, String firstName, String lastName, String speciality, Integer salary, String email, String phoneNumber, List<Branch> branches, MultipartFile multipartFile) throws IOException;
    Employee updateImage(String email, MultipartFile profileImage) throws IOException ;
    Boolean deleteEmployee(Long id);
}
