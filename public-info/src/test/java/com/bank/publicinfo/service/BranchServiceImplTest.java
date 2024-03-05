package com.bank.publicinfo.service;

import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.repository.BranchRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchServiceImplTest {

    private static final Long ID = 1L;
    private static final LocalTime START_OF_WORK = LocalTime.of(10, 0, 1);
    private static final LocalTime END_OF_WORK = LocalTime.of(20,0,1);
    @Mock
    BranchRepository branchRepository;
    @InjectMocks
    BranchServiceImpl branchServiceImpl;

    @Test
    void findOne() {
        Mockito.when(branchRepository.findById(ID)).thenReturn(Optional.of(getListBranch().get(0)));

        Branch result = branchServiceImpl.findOne(ID);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(ID, result.getId());
    }

    @Test
    void findAll() {
        Mockito.when(branchRepository.findAll()).thenReturn(getListBranch());

        List<Branch> result = branchServiceImpl.findAll();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void save(){
        branchServiceImpl.save(getListBranch().get(0));

        Mockito.verify(branchRepository, Mockito.times(1)).save(getListBranch().get(0));
    }

    @Test
    void update(){
        when(branchRepository.existsById(ID)).thenReturn(true);

        branchServiceImpl.update(ID, getListBranch().get(0));

        Mockito.verify(branchRepository).save(getListBranch().get(0));
    }

    @Test
    public void testUpdateAccountDetailsIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () ->
                branchServiceImpl.update(null, getListBranch().get(0)));
    }

    @Test
    void delete() {
        branchServiceImpl.delete(ID);

        Mockito.verify(branchRepository, Mockito.times(1)).deleteById(1L);
    }

    private List<Branch> getListBranch() {
        Branch branch1 = new Branch(1L, "Lenina 26", 8904325L, "SPB", START_OF_WORK, END_OF_WORK, null);
        Branch branch2 = new Branch(2L, "Lenina 30", 8911874L, "MSK", START_OF_WORK, END_OF_WORK, null);
        return List.of(branch1, branch2);
    }
}
