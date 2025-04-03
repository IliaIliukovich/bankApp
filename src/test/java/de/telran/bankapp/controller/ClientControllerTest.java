package de.telran.bankapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.bankapp.config.SecurityConfig;
import de.telran.bankapp.dto.ClientCreateDto;
import de.telran.bankapp.dto.ClientDto;
import de.telran.bankapp.entity.enums.ClientStatus;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.security.JwtProvider;
import de.telran.bankapp.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClientController.class)
@Import(SecurityConfig.class)
class ClientControllerTest {

    @MockitoBean
    private ClientService service;

    @MockitoBean
    private JwtProvider jwtProvider;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @WithMockUser(username = "Test user", roles = {"ADMIN"})
    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/client/all").contentType("application/json"))
                .andExpect(status().isOk());
        verify(service).getAll();
    }

    @WithMockUser(username = "Test user", roles = {"CLIENT"})
    @Test
    void getAllAccessDenied() throws Exception {
        mockMvc.perform(get("/client/all").contentType("application/json"))
                .andExpect(status().is4xxClientError());
        verify(service, never()).getAll();
    }

    @WithMockUser(username = "Test user", roles = {"ADMIN"})
    @Test
    void findByName() throws Exception {
        mockMvc.perform(get("/client/search")
                        .queryParam("name", "Anna")
                        .queryParam("sort", "lastName")
                        .contentType("application/json"))
                .andExpect(status().isOk());
        verify(service).findByName("Anna", Sort.by("lastName"));

        mockMvc.perform(get("/client/search")
                        .queryParam("name", "Anna")
                        .contentType("application/json"))
                .andExpect(status().isOk());
        verify(service).findByName("Anna", Sort.unsorted());
    }

    @WithMockUser(username = "Test user", roles = {"ADMIN"})
    @Test
    void addClient() throws Exception {
        ClientCreateDto clientCreate = new ClientCreateDto("New", "New", "DE987654321", "a.mueller@example.com", "Munich, Germany", "+49 89 7654321", 1L, null);
        ClientDto client = new ClientDto("1", "New", "New", "DE987654321", "a.mueller@example.com", "Munich, Germany", "+49 89 7654321", ClientStatus.ACTIVE, 1L, null);
        when(service.addClient(eq(clientCreate))).thenReturn(client);

        MvcResult mvcResult = mockMvc.perform(post("/client")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(clientCreate)))
//                        .content("{\"lastName\":\"New\",\"firstName\":\"New\",\"taxCode\":\"DE987654321\",\"email\":\"a.mueller@example.com\",\"address\":\"Munich, Germany\",\"phone\":\"+49 89 7654321\",\"managerId\":1,\"age\":null}"))
                .andExpect(status().isCreated())
                .andReturn();
        verify(service).addClient(eq(clientCreate));

        String json = mvcResult.getResponse().getContentAsString();
        assertEquals(mapper.writeValueAsString(client), json);


        client.setEmail("email");
        mockMvc.perform(post("/client")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(client)))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(username = "Test user", roles = {"ADMIN"})
    @Test
    void updateClientNotFound() throws Exception {
        ClientDto client = new ClientDto("1", "New", "New", "DE987654321", "a.mueller@example.com", "Munich, Germany", "+49 89 7654321", ClientStatus.ACTIVE, 1L, null);
        when(service.updateClient(eq(client))).thenThrow(BankAppResourceNotFoundException.class);

        mockMvc.perform(put("/client")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(client)))
                .andExpect(status().isNotFound());
        verify(service).updateClient(eq(client));
    }
}