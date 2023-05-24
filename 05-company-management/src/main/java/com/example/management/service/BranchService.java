package com.example.management.service;

import com.example.management.entity.Branch;

import java.util.List;

public interface BranchService {
    List<Branch> getAll();
    Branch getBranchById(Long id);
    Branch getBranchByName(String name);
    Branch createBranch(Branch branch);
    Boolean deleteBranch(Long id);

}
