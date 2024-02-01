package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Atm;

import java.util.List;

public interface AtmService {

    Atm findOne(Long id);

    List<Atm> findAll();

    void save(Atm atm);

    void update(Long id, Atm atm);

    void delete(Long id);

}
