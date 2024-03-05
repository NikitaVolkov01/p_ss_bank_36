package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.repository.LicenseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LicenseServiceImplTest {

    private static final long ID = 1;
    byte[] photo = { 72, 101, 108, 108, 111 };
    @Mock
    LicenseRepository licenseRepository;
    @InjectMocks
    LicenseServiceImpl licenseServiceImpl;

    @Test
    void findOne() {
        Mockito.when(licenseRepository.findById(ID)).thenReturn(Optional.of(getListLicense().get(0)));

        License result = licenseServiceImpl.findOne(ID);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(ID, result.getId());
    }

    @Test
    void findAll() {
        Mockito.when(licenseRepository.findAll()).thenReturn(getListLicense());

        List<License> result = licenseServiceImpl.findAll();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void save(){
        licenseServiceImpl.save(getListLicense().get(0));

        Mockito.verify(licenseRepository, Mockito.times(1)).save(getListLicense().get(0));
    }

    @Test
    void update(){
        when(licenseRepository.existsById(ID)).thenReturn(true);

        licenseServiceImpl.update(ID, getListLicense().get(0));

        Mockito.verify(licenseRepository).save(getListLicense().get(0));
    }

    @Test
    public void testUpdateAccountDetailsIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () ->
                licenseServiceImpl.update(null, getListLicense().get(0)));
    }

    @Test
    void delete() {
        licenseServiceImpl.delete(ID);

        Mockito.verify(licenseRepository, Mockito.times(1)).deleteById(ID);
    }

    private List<License> getListLicense(){
        License license1 = new License(1L, photo, null);
        License license2 = new License(2L, photo, null);
        return List.of(license1, license2);
    }
}
