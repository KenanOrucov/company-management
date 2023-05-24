package com.example.management.service.impl;

import com.example.management.entity.Branch;
import com.example.management.entity.Department;
import com.example.management.entity.Employee;
import com.example.management.repository.EmployeeRepository;
import com.example.management.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAll() {
        log.info("Fetching all employees");
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        log.info("Fetching employee by id: {}", id);
        Employee employee = null;
        Optional<Employee> optional = employeeRepository.findById(id);
        if (optional.isPresent()){
            employee = optional.get();
        }
        return employee;
    }

    @Override
    public List<Employee> getEmployeeByName(String name) {
        log.info("Fetching employee by name: {}", name);
        return employeeRepository.findByFirstName(name);
    }

    @Override
    @Transactional
    public Employee createEmployee(Long id, String firstName, String lastName, String speciality, Integer salary, String email, String phoneNumber,  List<Branch> branches, MultipartFile multipartFile) throws IOException {
        log.info("Saving new employee: {}" + firstName);
        branches.add(new Branch());
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setSpeciality(speciality);
        employee.setSalary(salary);
        employee.setEmail(email);
        employee.setPhoneNumber(phoneNumber);
        employee.setImageUrl(setProfileImageUrl(multipartFile));
        if (StringUtils.isNotBlank(branches.get(0).getBranchName())){
            employee.setBranches(branches);
        }
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    @Transactional
    public Boolean deleteEmployee(Long id) {
        log.info("Deleting employee: {}" + id);
        Optional<Employee> optional = employeeRepository.findById(id);
        if (optional.isPresent()) {
            employeeRepository.deleteById(id);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    @Transactional
    public Employee updateImage(String email, MultipartFile profileImage) throws IOException {
        Employee employee = employeeRepository.findByEmail(email);
        log.info(String.valueOf(employee));
        employee.setImageUrl(setProfileImageUrl(profileImage));
        return employee;
    }

    private String setProfileImageUrl(MultipartFile profileImage) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/employee/image/" + profileImage.getOriginalFilename()).toUriString();
    }
}
