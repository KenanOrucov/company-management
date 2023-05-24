package com.example.management.service.impl;

import com.example.management.entity.Branch;
import com.example.management.entity.Department;
import com.example.management.repository.BranchRepository;
import com.example.management.service.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BranchServiceImpl implements BranchService{

    private BranchRepository branchRepository;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public List<Branch> getAll() {
        log.info("Fetching all branches");
        List<Branch> branches=  branchRepository.findAll();
        for (Branch branch: branches){
            getEmployeeNumber(branch);
        }
        return branches;
    }

    @Override
    public Branch getBranchById(Long id) {
        log.info("Fetching branch by id: ", id);
        Branch branch = null;
        Optional<Branch>optional = branchRepository.findById(id);
        if (optional.isPresent()){
            branch = optional.get();
        }
        getEmployeeNumber(branch);
        return branch;
    }

    @Override
    public Branch getBranchByName(String name) {
        log.info("Fetching branch by name: ", name);
        Branch branch = branchRepository.findByName(name);
        getEmployeeNumber(branch);
        return branchRepository.findByName(name);
    }

    @Override
    @Transactional
    public Branch createBranch(Branch branch) {
        log.info("Saving new branch: " + branch.getName());
        getEmployeeNumber(branch);
        return branchRepository.save(branch);
    }


    @Override
    @Transactional
    public Boolean deleteBranch(Long id) {
        log.info("Deleting branch: " + id);
        Optional<Branch> optional = branchRepository.findById(id);
        if (optional.isPresent()){
            branchRepository.deleteById(id);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void getEmployeeNumber(Branch branch){
        if (branch.getEmployees() != null) {
            branch.setEmployeeNumber(branch.getEmployees().size());
        }
    }
}
