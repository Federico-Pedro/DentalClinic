package com.fede.DentalClinicMVC.controller;

import com.fede.DentalClinicMVC.entity.Dentist;
import com.fede.DentalClinicMVC.service.IDentistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class DentistControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IDentistService dentistService;


    public void dataLoad() {
        Dentist dentist = new Dentist();
        dentist.setRegistration(555);
        dentist.setName("Hernan");
        dentist.setLastName("Agudiak");
        dentistService.save(dentist);
    }

    @Test
    public void testGetDentistById() throws Exception {
        dataLoad();
        mockMvc.perform(get("/odontologos/2") //puse Id 2 pq el test anterior ya carga un odontologo y quedaba con el id 1
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registration").value("555"))
                .andExpect(jsonPath("$.name").value("Hernan"))
                .andExpect(jsonPath("$.lastName").value("Agudiak"));
    }

    @Test
    public void testPostDentist() throws Exception {
        String dentistSaved =  "{\"registration\": \"125\", \"name\": \"Juan\", \"lastName\": \"Perez\"}";

        mockMvc.perform(post("/odontologos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dentistSaved)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registration").value("125"))
                .andExpect(jsonPath("$.name").value("Juan"))
                .andExpect(jsonPath("$.lastName").value("Perez"));

    }

    @Test
    public void testGetAllDentists() throws Exception {
        mockMvc.perform(get("/odontologos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

    }

}
//puedo usar la anotación @order(1) a cada test y agregar la anotacion @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//para que los tests se ejecuten en un determinado orden