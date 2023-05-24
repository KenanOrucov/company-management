package com.example.management.service;

import com.example.management.entity.Company;
import com.example.management.entity.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> getAll();
    Department getDepartmentById(Long id);
    Department getDepartmentByName(String name);
    Department createDepartment(Department department);
    Boolean deleteDepartment(Long id);
}
