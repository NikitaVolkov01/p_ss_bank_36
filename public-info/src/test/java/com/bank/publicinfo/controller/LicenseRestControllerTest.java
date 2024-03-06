package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.LicenseDTO;
import com.bank.publicinfo.entity.License;
import com.bank.publicinfo.mapper.LicenseMapper;
import com.bank.publicinfo.service.LicenseService;
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
public class LicenseRestControllerTest {

    private static final long ID = 1;

    byte[] photo = { 72, 101, 108, 108, 111 };

    String base64Encoded = Base64.getEncoder().encodeToString(photo);

    @Mock
    private LicenseService licenseService;

    @InjectMocks
    private LicenseRestController licenseRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(licenseRestController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void findOneLicense() throws Exception {
        when(licenseService.findOne(ID)).thenReturn(getListLicense().get(0));

        mockMvc.perform(get("/licenses/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.photo").value(base64Encoded));

        verify(licenseService, times(1)).findOne(ID);
    }

    @Test
    void findAllLicenses() throws Exception {
        when(licenseService.findAll()).thenReturn(getListLicense());

        mockMvc.perform(get("/licenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(licenseService, times(1)).findAll();
    }

    @Test
    void createLicense() throws Exception {
        List<LicenseDTO> licenseDTO = getListLicenseDTO();
        License result = LicenseMapper.INSTANCE.toEntity(licenseDTO.get(0));
        String licenseJson = objectMapper.writeValueAsString(licenseDTO.get(0));

        mockMvc.perform(MockMvcRequestBuilders.post("/licenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(licenseJson))
                .andExpect(status().isOk());

        verify(licenseService, times(1)).save(result);
    }

    @Test
    void updateLicense() {
        ResponseEntity<HttpStatus> response = licenseRestController.updateLicense(ID, getListLicenseDTO().get(0));

        verify(licenseService, times(1)).update(ID, LicenseMapper.INSTANCE.toEntity(getListLicenseDTO().get(0)));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteLicense() throws Exception {
        mockMvc.perform(delete("/licenses/{id}", ID)).andExpect(status().isOk());

        verify(licenseService, times(1)).delete(ID);
    }

    private List<License> getListLicense(){
        License license1 = new License(1L, photo, null);
        License license2 = new License(2L, photo, null);
        return List.of(license1, license2);
    }

    private List<LicenseDTO> getListLicenseDTO() {
        LicenseDTO licenseDTO1 = new LicenseDTO(photo, null);
        LicenseDTO licenseDTO2 = new LicenseDTO(photo, null);
        return List.of(licenseDTO1, licenseDTO2);
    }
}
