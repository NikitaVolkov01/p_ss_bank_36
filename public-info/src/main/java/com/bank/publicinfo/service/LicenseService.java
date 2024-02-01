package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.License;

import java.util.List;

public interface LicenseService {

    License findOne(Long id);

    List<License> findAll();

    void save(License license);

    void update(Long id, License license);

    void delete(Long id);
}
