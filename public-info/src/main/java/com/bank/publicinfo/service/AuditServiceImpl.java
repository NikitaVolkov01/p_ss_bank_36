package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Autowired
    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public void createAudit(Audit audit) {
        auditRepository.save(audit);
    }
}
