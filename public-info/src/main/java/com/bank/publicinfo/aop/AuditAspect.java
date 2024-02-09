package com.bank.publicinfo.aop;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
@Component
@Aspect
public class AuditAspect {

    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AuditAspect(AuditService auditService, ObjectMapper objectMapper) {
        this.auditService = auditService;
        this.objectMapper = objectMapper;
    }

    @AfterReturning("com.bank.publicinfo.aop.Pointcuts.allSaveMethods() && args(entity)")
    public void entityCreate(Object entity) throws JsonProcessingException {
        if (entity != null) {
            Audit audit = new Audit();
            audit.setEntityType(entity.getClass().getSimpleName());
            if (entity.toString().contains("id = null")) {
                audit.setOperationType("ATTEMPT TO CREATE");
            } else {
                audit.setOperationType("CREATE");
            }
            audit.setCreatedBy("postgres");
            audit.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            audit.setEntityJson(objectMapper.writeValueAsString(entity));
            auditService.createAudit(audit);
        }
    }

    @AfterReturning(value = "com.bank.publicinfo.aop.Pointcuts.allUpdateMethods() && args(id, entity)")
    public void entityUpdate(Long id, Object entity) throws JsonProcessingException {
        Audit audit = new Audit();
        audit.setEntityType(entity.getClass().getSimpleName());
        audit.setOperationType("UPDATE");
        audit.setCreatedBy("postgres");
        audit.setModifiedBy("postgres");
        audit.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        audit.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        audit.setEntityJson(objectMapper.writeValueAsString(entity));
        audit.setNewEntityJson(objectMapper.writeValueAsString(entity));
        auditService.createAudit(audit);
    }

    @AfterReturning("com.bank.publicinfo.aop.Pointcuts.allDeleteMethods() && args(id)")
    public void entityDelete(JoinPoint joinPoint, Long id) {
        Audit audit = new Audit();
        String className = joinPoint.getTarget().getClass().getSimpleName().replace("ServiceImpl", "");
        audit.setEntityType(className);
        audit.setOperationType("DELETE");
        audit.setCreatedBy("postgres");
        audit.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        audit.setEntityJson("Deleted entity with id: " + id);
        auditService.createAudit(audit);
    }
}
