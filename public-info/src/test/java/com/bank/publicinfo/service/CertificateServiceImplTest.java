package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.repository.CertificateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    private static final long ID = 1;
    byte[] photo = { 72, 101, 108, 108, 111 };
    @Mock
    CertificateRepository certificateRepository;
    @InjectMocks
    CertificateServiceImpl certificateServiceImpl;

    @Test
    void findOne() {
        when(certificateRepository.findById(ID)).thenReturn(Optional.of(getListCertificate().get(0)));

        Certificate result = certificateServiceImpl.findOne(ID);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(ID, result.getId());
    }

    @Test
    void findAll() {
        when(certificateRepository.findAll()).thenReturn(getListCertificate());

        List<Certificate> result = certificateServiceImpl.findAll();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void save(){
        certificateServiceImpl.save(getListCertificate().get(0));

        verify(certificateRepository, times(1)).save(getListCertificate().get(0));
    }

    @Test
    void update(){
        when(certificateRepository.existsById(ID)).thenReturn(true);

        certificateServiceImpl.update(ID, getListCertificate().get(0));

        verify(certificateRepository).save(getListCertificate().get(0));
    }

    @Test
    public void testUpdateAccountDetailsIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () ->
                certificateServiceImpl.update(null, getListCertificate().get(0)));
    }

    @Test
    void delete() {
        certificateServiceImpl.delete(ID);

        verify(certificateRepository, times(1)).deleteById(ID);
    }

    private List<Certificate> getListCertificate(){
        Certificate certificate1 = new Certificate(1L, photo, null);
        Certificate certificate2 = new Certificate(2L, photo, null);
        return List.of(certificate1, certificate2);
    }
}
