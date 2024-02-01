package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;

    @Autowired
    public LicenseServiceImpl(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    @Override
    public License findOne(Long id) {
        return licenseRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<License> findAll() {
        return licenseRepository.findAll();
    }

    @Override
    public void save(License license) {
        licenseRepository.save(license);
    }

    @Override
    public void update(Long id, License license) {
        if (licenseRepository.existsById(id)) {
            license.setId(id);
            licenseRepository.save(license);
        } else {
            throw new IllegalArgumentException("License with id " + id + " does not exist");
        }
    }

    @Override
    public void delete(Long id) {
        licenseRepository.deleteById(id);
    }
}
