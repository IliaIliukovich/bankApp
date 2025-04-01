package de.telran.bankapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.bankapp.security.JwtProvider;
import de.telran.bankapp.service.ManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ManagerController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class ManagerControllerTest {

    @MockitoBean
    private ManagerService managerService;

    @MockitoBean
    private JwtProvider jwtProvider;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    void getAllManagers() throws Exception {
        mockMvc.perform(get("/manager/all").contentType("application/json"))
                .andExpect(status().isOk());
        verify(managerService).getAll();
    }

    @Test
    void changeLastName() throws Exception{
        mockMvc.perform(patch("/manager/changeLastName?id=1&newLastName=NewSchmidt")
                        .contentType("application/json"))
                .andExpect(status().isAccepted());
        verify(managerService).updateLastName(1L,"NewSchmidt" );

        mockMvc.perform(patch("/manager/changeLastName?id=1&newLastName=New!Schmidt")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());

///changeLastName?id=1&&newLastName=NewSchmidt in Postmann
    }
}