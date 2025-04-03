package de.telran.bankapp.controller;

import de.telran.bankapp.config.SecurityConfig;
import de.telran.bankapp.security.JwtProvider;
import de.telran.bankapp.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
@Import(SecurityConfig.class)
class TransactionControllerTest {

    @MockitoBean
    private TransactionService service;

    @MockitoBean
    private JwtProvider jwtProvider;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username = "Test user", roles = {"ADMIN"})
    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/transaction/all").contentType("application/json"))
                .andExpect(status().isOk());
        verify(service).getAllTransactions();
    }

    @WithMockUser(username = "Test user", roles = {"ADMIN"})
    @Test
    void getTransactionById() throws Exception {
        mockMvc.perform(get("/transaction/4ea37144-df60-4681-b796-760345166d39")
                        .contentType("application/json"))
                .andExpect(status().isOk());
        verify(service).getTransactionById("4ea37144-df60-4681-b796-760345166d39");
    }

    @WithMockUser(username = "Test user", roles = {"ADMIN"})
    @Test
    void getTransactionByIdBadRequest() throws Exception {
        mockMvc.perform(get("/transaction/1")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

}