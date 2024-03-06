package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.repository.BankDetailsRepository;
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
class BankDetailsServiceImplTest {

    private static final Long ID = 1L;
    @Mock
    BankDetailsRepository bankDetailsRepository;
    @InjectMocks
    BankDetailsServiceImpl bankDetailsService;

    @Test
    void findOne() {
        when(bankDetailsRepository.findById(ID)).thenReturn(Optional.of(getListBankDetails().get(0)));

        BankDetails result = bankDetailsService.findOne(ID);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(ID, result.getId());
    }

    @Test
    void findAll() {
        when(bankDetailsRepository.findAll()).thenReturn(getListBankDetails());

        List<BankDetails> result = bankDetailsService.findAll();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void save(){
        bankDetailsService.save(getListBankDetails().get(0));

        verify(bankDetailsRepository, times(1)).save(getListBankDetails().get(0));
    }

    @Test
    void update(){
        when(bankDetailsRepository.existsById(ID)).thenReturn(true);

        bankDetailsService.update(ID, getListBankDetails().get(0));

        verify(bankDetailsRepository).save(getListBankDetails().get(0));
    }

    @Test
    public void testUpdateAccountDetailsIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () ->
                bankDetailsService.update(null, getListBankDetails().get(0)));
    }

    @Test
    void delete() {
        bankDetailsService.delete(ID);

        verify(bankDetailsRepository, times(1)).deleteById(ID);
    }

    private List<BankDetails> getListBankDetails() {
        BankDetails bankDetails1 = new BankDetails(1L, 2L, 3L, 4L,  5, "SPB", "OAO", "tex", null, null);
        BankDetails bankDetails2 = new BankDetails(2L, 3L, 4L, 5L,  6, "MSK", "OOO", "ui", null, null);
        return List.of(bankDetails1 ,bankDetails2);
    }
}
