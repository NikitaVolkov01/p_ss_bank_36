package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.BankDetails;

import java.util.List;

public interface BankDetailsService {

    BankDetails findOne(Long id);

    List<BankDetails> findAll();

    void save(BankDetails bankDetails);

    void update(Long id, BankDetails bankDetails);

    void delete(Long id);

}
