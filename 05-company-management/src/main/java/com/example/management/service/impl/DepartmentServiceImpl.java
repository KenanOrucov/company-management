package com.example.management.service.impl;

import com.example.management.entity.Branch;
import com.example.management.entity.Company;
import com.example.management.entity.Department;
import com.example.management.repository.DepartmentRepository;
import com.example.management.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getAll() {
        log.info("Fetching all departments");
        List<Department> departments = departmentRepository.findAll();
        for (Department department: departments){
            setEmployeeNumber(department);
        }
        return departments;
    }

    @Override
    public Department getDepartmentById(Long id) {
        log.info("Fetching department by id: {}", id);
        Department department = null;
        Optional<Department> optional = departmentRepository.findById(id);
        if (optional.isPresent()){
            department = optional.get();
        }
        setEmployeeNumber(department);
        return department;
    }

    @Override
    public Department getDepartmentByName(String name) {
        log.info("Fetching department by name: {}", name);
        Department department = departmentRepository.findByName(name);
        setEmployeeNumber(department);
        return department;
    }

    @Override
    @Transactional
    public Department createDepartment(Department department) {
        log.info("Saving new department: {}" + department.getName());
        setEmployeeNumber(department);
        return departmentRepository.save(department);
    }

    @Override
    @Transactional
    public Boolean deleteDepartment(Long id) {
        log.info("Deleting department: {}" + id);
        Optional<Department> optional = departmentRepository.findById(id);
        if (optional.isPresent()){
            departmentRepository.deleteById(id);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void setEmployeeNumber(Department department){
        List<Branch> branches = department.getBranches();
        int number = 0;
        if (branches != null) {
            for (Branch branch : branches) {
                number += branch.getEmployees().size();
            }
        }
        department.setEmployeeNumber(number);
    }
}
