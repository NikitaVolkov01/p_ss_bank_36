package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.CertificateDTO;
import com.bank.publicinfo.entity.Certificate;
import com.bank.publicinfo.mapper.CertificateMapper;
import com.bank.publicinfo.service.CertificateService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.Base64;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CertificateRestControllerTest {

    private static final long ID = 1;

    byte[] photo = { 72, 101, 108, 108, 111 };
    String base64Encoded = Base64.getEncoder().encodeToString(photo);

    @Mock
    private CertificateService certificateService;

    @InjectMocks
    private CertificateRestController certificateRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(certificateRestController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void findOneCertificate() throws Exception {
        when(certificateService.findOne(ID)).thenReturn(getListCertificate().get(0));

        mockMvc.perform(get("/certificates/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.photo").value(base64Encoded));

        verify(certificateService, times(1)).findOne(ID);
    }

    @Test
    void findAllCertificates() throws Exception {
        when(certificateService.findAll()).thenReturn(getListCertificate());

        mockMvc.perform(get("/certificates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(certificateService, times(1)).findAll();
    }

    @Test
    void createCertificate() throws Exception {
        List<CertificateDTO> certificateDTO = getListCertificateDTO();
        Certificate result = CertificateMapper.INSTANCE.toEntity(certificateDTO.get(0));
        String certificateJson = objectMapper.writeValueAsString(certificateDTO.get(0));

        mockMvc.perform(MockMvcRequestBuilders.post("/certificates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(certificateJson))
                        .andExpect(status().isOk());

        verify(certificateService, times(1)).save(result);
    }

    @Test
    void updateCertificate() {
        ResponseEntity<HttpStatus> response = certificateRestController.updateCertificate(ID, getListCertificateDTO().get(0));
        verify(certificateService, times(1)).update(ID, CertificateMapper.INSTANCE.toEntity(getListCertificateDTO().get(0)));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteCertificate() throws Exception {
        mockMvc.perform(delete("/certificates/{id}", ID)).andExpect(status().isOk());
        verify(certificateService, times(1)).delete(ID);
    }

    private List<Certificate> getListCertificate(){
        Certificate certificate1 = new Certificate(1L, photo, null);
        Certificate certificate2 = new Certificate(2L, photo, null);
        return List.of(certificate1, certificate2);
    }

    private List<CertificateDTO> getListCertificateDTO() {
        CertificateDTO certificateDTO1 = new CertificateDTO(photo, null);
        CertificateDTO certificateDTO2 = new CertificateDTO(photo, null);
        return List.of(certificateDTO1, certificateDTO2);
    }
}
