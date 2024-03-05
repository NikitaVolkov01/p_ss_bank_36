package com.bank.publicinfo.aop;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.service.AuditService;
import com.bank.publicinfo.util.UtilStrings;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Aspect
@Component
public class AuditAspect {

    private final AuditService auditService;
    private final ObjectMapper objectMapper;
    private final UtilStrings utilStrings;
    private Timestamp createdAt;
    private String createdBy;

    @Autowired
    public AuditAspect(AuditService auditService, ObjectMapper objectMapper, UtilStrings utilStrings) {
        this.auditService = auditService;
        this.objectMapper = objectMapper;
        this.utilStrings = utilStrings;
    }

    @AfterReturning("com.bank.publicinfo.aop.Pointcuts.allSaveMethods() && args(entity)")
    public void entityCreate(Object entity) throws JsonProcessingException {
        if (entity != null) {
            Audit audit = new Audit();
            audit.setEntityType(entity.getClass().getSimpleName());
            audit.setOperationType("CREATE");
            audit.setCreatedBy(utilStrings.getDbUsername());
            createdBy = audit.getCreatedBy();
            audit.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            createdAt = audit.getCreatedAt();
            audit.setEntityJson(objectMapper.writeValueAsString(entity));
            auditService.createAudit(audit);
        }
    }

    @AfterReturning(value = "com.bank.publicinfo.aop.Pointcuts.allUpdateMethods() && args(id, entity)")
    public void entityUpdate(Long id, Object entity) throws JsonProcessingException {
        if (id != null && entity != null) {
            Audit audit = new Audit();
            audit.setEntityType(entity.getClass().getSimpleName());
            audit.setOperationType("UPDATE");
            audit.setCreatedBy(createdBy);
            audit.setModifiedBy(utilStrings.getDbUsername());
            audit.setCreatedBy(utilStrings.getDbUsername());
            audit.setCreatedAt(createdAt);
            audit.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            audit.setNewEntityJson(objectMapper.writeValueAsString(entity));

            List<String> customAudit = auditService.searchCustom();

            for(String i: customAudit){
                if (audit.getEntityType().equals("Branch") && i.contains("Branch") && i.substring(1,8).equals(audit.getNewEntityJson().substring(1,8))) {
                    audit.setEntityJson(i);
                } else if (audit.getEntityType().equals("Atm") && i.contains("Atm") && i.substring(1,8).equals(audit.getNewEntityJson().substring(1,8))) {
                    audit.setEntityJson(i);
                } else if (audit.getEntityType().equals("BankDetails") && i.contains("BankDetails") && i.substring(1,8).equals(audit.getNewEntityJson().substring(1,8))) {
                    audit.setEntityJson(i);
                }else if (audit.getEntityType().equals("Certificate") && i.contains("Certificate") && i.substring(1,8).equals(audit.getNewEntityJson().substring(1,8))) {
                    audit.setEntityJson(i);
                } else if (audit.getEntityType().equals("License") && i.contains("License") && i.substring(1,8).equals(audit.getNewEntityJson().substring(1,8))) {
                    audit.setEntityJson(i);
                }
            }
            auditService.createAudit(audit);
        }
    }

    @AfterReturning("com.bank.publicinfo.aop.Pointcuts.allDeleteMethods() && args(id, entity)")
    public void entityDelete(Object entity, Long id) {
        if (id != null) {
            Audit audit = new Audit();
            audit.setEntityType(entity.getClass().getSimpleName());
            audit.setOperationType("DELETE");
            audit.setCreatedBy(utilStrings.getDbUsername());
            audit.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            audit.setEntityJson("Deleted entity with id: " + id);
            auditService.createAudit(audit);
        }
    }
}
