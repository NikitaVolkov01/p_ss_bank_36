package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.repository.AtmRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AtmServiceImplTest {

    private static final Long ID = 1L;
    @Mock
    AtmRepository atmRepository;
    @InjectMocks
    AtmServiceImpl  atmServiceImpl;


    @Test
    void findOne() {
        when(atmRepository.findById(ID)).thenReturn(Optional.of(getListAtm().get(0)));

        Atm result = atmServiceImpl.findOne(ID);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(ID, result.getId());
    }

    @Test
    void findAll() {
        when(atmRepository.findAll()).thenReturn(getListAtm());

        List<Atm> result = atmServiceImpl.findAll();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void save(){
        atmServiceImpl.save(getListAtm().get(0));

        verify(atmRepository, times(1)).save(getListAtm().get(0));
    }

    @Test
    void update(){
        when(atmRepository.existsById(ID)).thenReturn(true);

        atmServiceImpl.update(ID, getListAtm().get(0));

        verify(atmRepository).save(getListAtm().get(0));
    }

    @Test
    public void testUpdateAccountDetailsIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () ->
                atmServiceImpl.update(null, getListAtm().get(0)));
    }

    @Test
    void delete() {
        atmServiceImpl.delete(ID);

        verify(atmRepository, times(1)).deleteById(ID);
    }

    private List<Atm> getListAtm() {

        Atm firstTestAtm = new Atm();

        Atm secondTestAtm = new Atm();

        firstTestAtm.setId(1L);
        firstTestAtm.setAddress("Saint-P");

        secondTestAtm.setId(2L);
        secondTestAtm.setAddress("Moscow");

        return List.of(firstTestAtm, secondTestAtm);
    }
}
