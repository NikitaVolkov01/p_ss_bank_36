package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.AtmDTO;
import com.bank.publicinfo.entity.Atm;
import com.bank.publicinfo.entity.Branch;
import com.bank.publicinfo.mapper.AtmMapper;
import com.bank.publicinfo.service.AtmService;
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
class AtmRestControllerTest {

    private static final long ID = 1;
    private static final LocalTime START_OF_WORK = LocalTime.of(10, 0, 1);
    private static final LocalTime END_OF_WORK = LocalTime.of(20,0,1);
    private static final LocalTime START_OF_WORK1 = LocalTime.of(10, 0, 0);
    private static final LocalTime END_OF_WORK2 = LocalTime.of(10,0,0);

    @Mock
    private AtmService atmService;

    @InjectMocks
    private AtmRestController atmRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(atmRestController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void findOneAtm() throws Exception {

        Atm atm = new Atm(1L, "Moscow", START_OF_WORK, END_OF_WORK, false, null);

        when(atmService.findOne(ID)).thenReturn(atm);
        mockMvc.perform(get("/atms/{id}", ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Moscow"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.start_of_work").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.end_of_work").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.all_hours").value(false));

        verify(atmService, times(1)).findOne(ID);
    }

    @Test
    void findAllAtm() throws Exception {

        when(atmService.findAll()).thenReturn(getListAtm());

        mockMvc.perform(get("/atms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(atmService, times(1)).findAll();
    }

    @Test
    void createAtm() throws Exception {
        List<AtmDTO> atmDTOs = getListAtmDTO();
        Atm result = AtmMapper.INSTANCE.toEntity(atmDTOs.get(0));
        String atmJson = objectMapper.writeValueAsString(atmDTOs.get(0));

        mockMvc.perform(post("/atms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(atmJson))
                .andExpect(status().isOk());
        verify(atmService, times(1)).save(result);
    }

    @Test
    void createAtmAllHoursTrue() throws Exception {
        Atm result = AtmMapper.INSTANCE.toEntity(getAtmDtoWithAllHoursTrue());
        String atmJson = objectMapper.writeValueAsString(getAtmDtoWithAllHoursTrue());

        mockMvc.perform(post("/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(atmJson))
                .andExpect(status().isOk());
        verify(atmService, times(1)).save(result);
    }

    @Test
    void updateAtm() {
        ResponseEntity<HttpStatus> response = atmRestController.updateAtm(ID, getListAtmDTO().get(0));
        verify(atmService, times(1)).update(ID, AtmMapper.INSTANCE.toEntity(getListAtmDTO().get(0)));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateAtmAllHoursTrue() {
        ResponseEntity<HttpStatus> response = atmRestController.updateAtm(ID, getAtmDtoWithAllHoursTrue());
        verify(atmService, times(1)).update(ID, AtmMapper.INSTANCE.toEntity(getAtmDtoWithAllHoursTrue()));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteAtm() throws Exception {
        mockMvc.perform(delete("/atms/{id}", ID)).andExpect(status().isOk());
        verify(atmService, times(1)).delete(ID);
    }

    private List<AtmDTO> getListAtmDTO() {

        AtmDTO firstTestAtmDTO = new AtmDTO();
        AtmDTO secondTestAtmDTO = new AtmDTO();

        firstTestAtmDTO.setAddress("Saint-P");
        firstTestAtmDTO.setStart_of_work(START_OF_WORK);
        firstTestAtmDTO.setEnd_of_work(END_OF_WORK);
        firstTestAtmDTO.setAll_hours(false);
        firstTestAtmDTO.setBranch(null);

        secondTestAtmDTO.setAddress("Moscow");
        secondTestAtmDTO.setStart_of_work(START_OF_WORK1);
        secondTestAtmDTO.setEnd_of_work(END_OF_WORK2);
        secondTestAtmDTO.setAll_hours(true);
        secondTestAtmDTO.setBranch(null);

        return List.of(firstTestAtmDTO, secondTestAtmDTO);
    }

    private List<Atm> getListAtm() {

        Atm firstTestAtm = new Atm();

        Atm secondTestAtm = new Atm();

        firstTestAtm.setId(1L);
        firstTestAtm.setAddress("Saint-P");
        firstTestAtm.setStart_of_work(START_OF_WORK);
        firstTestAtm.setEnd_of_work(END_OF_WORK);
        firstTestAtm.setAll_hours(false);
        firstTestAtm.setBranch(null);

        secondTestAtm.setId(2L);
        secondTestAtm.setAddress("Moscow");
        secondTestAtm.setStart_of_work(START_OF_WORK);
        secondTestAtm.setEnd_of_work(END_OF_WORK);
        secondTestAtm.setAll_hours(false);
        secondTestAtm.setBranch(getBranch());

        return List.of(firstTestAtm, secondTestAtm);
    }

    private Branch getBranch() {
        return Branch.builder()
                .id(1L)
                .address("test")
                .phoneNumber(123456L)
                .city("test_city")
                .startOfWork(START_OF_WORK)
                .endOfWork(END_OF_WORK)
                .build();
    }

    private AtmDTO getAtmDtoWithAllHoursTrue() {
        return new AtmDTO("D", START_OF_WORK1, END_OF_WORK2, true, null);
    }
}