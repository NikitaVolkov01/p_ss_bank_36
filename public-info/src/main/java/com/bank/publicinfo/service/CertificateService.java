package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Certificate;

import java.util.List;

public interface CertificateService {

    Certificate findOne(Long id);

    List<Certificate> findAll();

    void save(Certificate certificate);

    void update(Long id, Certificate certificate);

    void delete(Long id);

}
