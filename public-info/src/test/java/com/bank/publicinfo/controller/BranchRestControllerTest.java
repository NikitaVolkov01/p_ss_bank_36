package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BranchDTO;
import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.mapper.BranchMapper;
import com.bank.publicinfo.service.BranchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BranchRestControllerTest {

    private static final long ID = 1;
    private static final LocalTime START_OF_WORK = LocalTime.of(10, 0, 1);
    private static final LocalTime END_OF_WORK = LocalTime.of(20,0,1);

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchRestController branchRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(branchRestController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void findOneBranch() throws Exception {
        Mockito.when(branchService.findOne(ID)).thenReturn(getListBranch().get(0));

        mockMvc.perform(get("/branches/{id}", ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Lenina 26"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(8904325L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("SPB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startOfWork").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.endOfWork").isArray());

        Mockito.verify(branchService, Mockito.times(1)).findOne(ID);
    }

    @Test
    void findAllBranches() throws Exception {
        Mockito.when(branchService.findAll()).thenReturn(getListBranch());

        mockMvc.perform(get("/branches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        Mockito.verify(branchService, Mockito.times(1)).findAll();
    }

    @Test
    void createBranch() throws Exception {
        List<BranchDTO> branchDTO = getListBranchDTO();
        Branch result = BranchMapper.INSTANCE.toEntity(branchDTO.get(0));
        String branchJson = objectMapper.writeValueAsString(branchDTO.get(0));

        mockMvc.perform(post("/branches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(branchJson))
                .andExpect(status().isOk());
        Mockito.verify(branchService, Mockito.times(1)).save(result);
    }

    @Test
    void updateBranch() {
        ResponseEntity<HttpStatus> response = branchRestController.updateBranch(ID, getListBranchDTO().get(0));
        Mockito.verify(branchService, Mockito.times(1)).update(ID, BranchMapper.INSTANCE.toEntity(getListBranchDTO().get(0)));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteBranch() throws Exception {
        mockMvc.perform(delete("/branches/{id}", ID)).andExpect(status().isOk());
        Mockito.verify(branchService, Mockito.times(1)).delete(ID);
    }

    private List<Branch> getListBranch() {
        Branch branch1 = new Branch(1L, "Lenina 26", 8904325L, "SPB", START_OF_WORK, END_OF_WORK, null);
        Branch branch2 = new Branch(2L, "Lenina 30", 8911874L, "MSK", START_OF_WORK, END_OF_WORK, null);
        return List.of(branch1, branch2);
    }

    private List<BranchDTO> getListBranchDTO() {
        BranchDTO branchDTO1 = new BranchDTO("Lenina 26", 8904325L, "SPB", START_OF_WORK, END_OF_WORK);
        BranchDTO branchDTO2 = new BranchDTO("Lenina 30", 8911874L, "MSK", START_OF_WORK, END_OF_WORK);
        return List.of(branchDTO1, branchDTO2);
    }
}
