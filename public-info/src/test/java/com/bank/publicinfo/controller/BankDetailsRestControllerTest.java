package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDTO;
import com.bank.publicinfo.entity.BankDetails;
import com.bank.publicinfo.mapper.BankDetailsMapper;
import com.bank.publicinfo.service.BankDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BankDetailsRestControllerTest {
    private static final long ID = 1;

    @Mock
    private BankDetailsService bankDetailsService;

    @InjectMocks
    private BankDetailsRestController bankDetailsRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bankDetailsRestController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void findOneBankDetails() throws Exception {
        when(bankDetailsService.findOne(ID)).thenReturn(getListBankDetails().get(0));

        mockMvc.perform(get("/bankDetails/{id}", ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bik").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.inn").value(3L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.kpp").value(4L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cor_account").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("SPB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.joint_stock_company").value("OAO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("tex"));

       verify(bankDetailsService, times(1)).findOne(ID);
    }

    @Test
    void findAllBankDetails() throws Exception {
        when(bankDetailsService.findAll()).thenReturn(getListBankDetails());

        mockMvc.perform(get("/bankDetails"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(bankDetailsService, times(1)).findAll();
    }

    @Test
    void createBankDetails() throws Exception {
        List<BankDetailsDTO> bankDetailsDTO = getListBankDetailsDTO();
        BankDetails result = BankDetailsMapper.INSTANCE.toEntity(bankDetailsDTO.get(0));
        String bankDetailsJson = objectMapper.writeValueAsString(bankDetailsDTO.get(0));

        mockMvc.perform(post("/bankDetails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bankDetailsJson))
                .andExpect(status().isOk());

        verify(bankDetailsService, times(1)).save(result);
    }

    @Test
    void updateBankDetails() {
        ResponseEntity<HttpStatus> response = bankDetailsRestController.updateBankDetails(ID, getListBankDetailsDTO().get(0));
        verify(bankDetailsService, times(1)).update(ID, BankDetailsMapper.INSTANCE.toEntity(getListBankDetailsDTO().get(0)));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteBankDetails() throws Exception {
        mockMvc.perform(delete("/bankDetails/{id}", ID)).andExpect(status().isOk());
        verify(bankDetailsService, times(1)).delete(ID);
    }

    private List<BankDetails> getListBankDetails() {
        BankDetails bankDetails1 = new BankDetails(1L, 2L, 3L, 4L,  5, "SPB", "OAO", "tex", null, null);
        BankDetails bankDetails2 = new BankDetails(2L, 3L, 4L, 5L,  6, "MSK", "OOO", "ui", null, null);
        return List.of(bankDetails1 ,bankDetails2);
    }

    private List<BankDetailsDTO> getListBankDetailsDTO() {
        BankDetailsDTO bankDetailsDTO1 = new BankDetailsDTO(2L, 3L, 4L,  5, "SPB", "OAO", "tex");
        BankDetailsDTO bankDetailsDTO2 = new BankDetailsDTO(2L, 3L, 4L,  5, "SPB", "OAO", "tex");
        return List.of(bankDetailsDTO1, bankDetailsDTO2);
    }
}
