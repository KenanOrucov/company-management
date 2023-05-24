package com.example.management.service;

import com.example.management.entity.Branch;
import com.example.management.entity.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getAll();
    Company getCompanyById(Long id);
    Company getCompanyByName(String name);
    Company createCompany(Company company);
    Boolean deleteCompany(Long id);
}
