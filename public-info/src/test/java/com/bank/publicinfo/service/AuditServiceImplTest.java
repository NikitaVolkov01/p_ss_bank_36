package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Audit;
import com.bank.publicinfo.repository.AuditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

@ExtendWith(MockitoExtension.class)
public class AuditServiceImplTest {

    Timestamp a = Timestamp.valueOf("2017-10-08 17:24:06");
    Timestamp b = Timestamp.valueOf("2017-10-08 17:24:06");
    @Mock
    AuditRepository auditRepository;
    @InjectMocks
    AuditServiceImpl auditService;

    @Test
    void save() {
        auditService.createAudit(getAudit());

        Mockito.verify(auditRepository, Mockito.times(1)).save(getAudit());
    }

    @Test
    void searchCustom() {
        auditService.searchCustom();

        Mockito.verify(auditRepository, Mockito.times(1)).searchCustom();
    }

    private Audit getAudit() {
        return new Audit(1L, "Atm", "CREATE", "postgres", "postgres" , a, b, "test", "test_new");
    }
}
