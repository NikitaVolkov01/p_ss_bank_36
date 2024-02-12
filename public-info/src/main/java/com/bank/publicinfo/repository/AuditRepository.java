package com.bank.publicinfo.repository;

import com.bank.publicinfo.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuditRepository extends JpaRepository<Audit, Long> {

    List<Audit> findAuditByEntityTypeAndOperationType(String entityType, String operationType);

    @Query("select t.entityJson, t.entityType from Audit t where t.operationType = 'CREATE'")
    List<String> searchCustom();
}
