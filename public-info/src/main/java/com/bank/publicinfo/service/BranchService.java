package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Branch;

import java.util.List;

public interface BranchService {

    Branch findOne(Long id);

    List<Branch> findAll();

    void save(Branch branch);

    void delete(Long id);

    void update(Long id, Branch branch);
}
