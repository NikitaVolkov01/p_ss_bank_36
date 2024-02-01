package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.AtmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtmServiceImpl implements AtmService {

    private final AtmRepository atmRepository;

    @Autowired
    public AtmServiceImpl(AtmRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Override
    public Atm findOne(Long id) {
        return atmRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Atm> findAll() {
        return atmRepository.findAll();
    }

    @Override
    public void save(Atm atm) {
        atmRepository.save(atm);
    }

    @Override
    public void delete(Long id) {
        atmRepository.deleteById(id);
    }

    @Override
    public void update(Long id, Atm atm) {
        if (atmRepository.existsById(id)) {
            atm.setId(id);
            atmRepository.save(atm);
        } else {
            throw new IllegalArgumentException("Atm with id " + id + " does not exist");
        }
    }
}
