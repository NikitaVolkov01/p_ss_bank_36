package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.repository.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Audit> getSpecialAudit(String entityType, String operationType) {
        return auditRepository.findAuditByEntityTypeAndOperationType(entityType,operationType);
    }

    @Override
    public List<String> searchCustom() {
        return auditRepository.searchCustom();
    }
}
