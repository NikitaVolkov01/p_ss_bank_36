package com.bank.publicinfo.aop;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.service.AuditService;
import com.bank.publicinfo.util.UtilStrings;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuditAspectTest {

    @Mock
    private AuditService auditService;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private UtilStrings utilStrings;
    @Mock
    Audit audit;
    @InjectMocks
    private AuditAspect auditAspect;

    Long id = 1L;

    @Test
    public void entityCreate() throws JsonProcessingException {
        auditAspect.entityCreate(getEntity());
        Mockito.verify(auditService, Mockito.times(1)).createAudit(Mockito.any(Audit.class));
    }

    @Test
    public void entityUpdate() throws JsonProcessingException {
        auditAspect.entityUpdate(id, getEntity());
        Mockito.verify(auditService, Mockito.times(1)).createAudit(Mockito.any(Audit.class));
    }

    @Test
    public void entityDelete() {
        auditAspect.entityDelete(getEntity(), id);
        Mockito.verify(auditService, Mockito.times(1)).createAudit(Mockito.any(Audit.class));
    }

    private Object getEntity(){
        return new Object();
    }


}
