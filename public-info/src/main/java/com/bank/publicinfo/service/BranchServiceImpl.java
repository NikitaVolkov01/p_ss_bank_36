package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public Branch findOne(Long id) {
        return branchRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    @Override
    public void save(Branch branch) {
        branchRepository.save(branch);
    }

    @Override
    public void update(Long id, Branch branch) {
        if (branchRepository.existsById(id)) {
            branch.setId(id);
            branchRepository.save(branch);
        } else {
            throw new IllegalArgumentException("Branch with id " + id + " does not exist");
        }
    }

    @Override
    public void delete(Long id) {
        branchRepository.deleteById(id);
    }
}
