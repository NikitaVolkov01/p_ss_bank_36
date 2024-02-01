package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.BankDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankDetailsServiceImpl implements BankDetailsService {

    private final BankDetailsRepository bankDetailsRepository;

    @Autowired
    public BankDetailsServiceImpl(BankDetailsRepository bankDetailsRepository) {
        this.bankDetailsRepository = bankDetailsRepository;
    }

    @Override
    public BankDetails findOne(Long id) {
        return bankDetailsRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<BankDetails> findAll() {
        return bankDetailsRepository.findAll();
    }

    @Override
    public void save(BankDetails bankDetails) {
        bankDetailsRepository.save(bankDetails);
    }

    @Override
    public void update(Long id, BankDetails bankDetails) {
        if (bankDetailsRepository.existsById(id)) {
            bankDetails.setId(id);
            bankDetailsRepository.save(bankDetails);
        } else {
            throw new IllegalArgumentException("BankDetails with id " + id + " does not exist");
        }
    }

    @Override
    public void delete(Long id) {
        bankDetailsRepository.deleteById(id);
    }
}
