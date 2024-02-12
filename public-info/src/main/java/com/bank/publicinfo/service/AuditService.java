package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Audit;

import java.util.List;

public interface AuditService {

    void createAudit(Audit audit);

    List<Audit> getSpecialAudit(String entityType, String operationType);

    List<String> searchCustom();
}
