package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService{

    private final CertificateRepository certificateRepository;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Override
    public Certificate findOne(Long id) {
        return certificateRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Certificate> findAll() {
        return certificateRepository.findAll();
    }

    @Override
    public void save(Certificate certificate) {
        certificateRepository.save(certificate);
    }

    @Override
    public void update(Long id, Certificate certificate) {
        if (certificateRepository.existsById(id)) {
            certificate.setId(id);
            certificateRepository.save(certificate);
        } else {
            throw new IllegalArgumentException("Certificate with id " + id + " does not exist");
        }
    }
    @Override
    public void delete(Long id) {
        certificateRepository.deleteById(id);
    }
}
