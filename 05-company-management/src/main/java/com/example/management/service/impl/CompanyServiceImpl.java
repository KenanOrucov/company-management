package com.example.management.service.impl;

import com.example.management.entity.Branch;
import com.example.management.entity.Company;
import com.example.management.entity.Department;
import com.example.management.repository.CompanyRepository;
import com.example.management.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAll() {
        log.info("Fetching all companies");
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        log.info("Fetching company by id: {}", id);
        Company company = null;
        Optional<Company> optional = companyRepository.findById(id);
        if (optional.isPresent()){
            company = optional.get();
        }
        return company;
    }

    @Override
    public Company getCompanyByName(String name) {
        log.info("Fetching company by name: {}", name);
        return companyRepository.findByName(name);
    }

    @Override
    @Transactional
    public Company createCompany(Company company) {
        log.info("Saving new company: {}" + company.getName());
        return companyRepository.save(company);
    }


    @Override
    @Transactional
    public Boolean deleteCompany(Long id) {
        log.info("Deleting company: {}" + id);
        Optional<Company> optional = companyRepository.findById(id);
        if (optional.isPresent()){
            companyRepository.deleteById(id);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
