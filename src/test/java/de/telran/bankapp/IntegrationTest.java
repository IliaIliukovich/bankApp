package de.telran.bankapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.telran.bankapp.dto.ClientCreateDto;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void contextLoads() {
    }

    @WithMockUser(username = "Test user", roles = {"CLIENT"})
    @Test
    void addAndDeleteClient() throws Exception {
        ClientCreateDto clientCreate = new ClientCreateDto("New", "New", "DE987654321", "a.mueller@example.com", "Munich, Germany", "+49 89 7654321", 1L, null);

        mockMvc.perform(post("/client")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(clientCreate)))
                .andExpect(status().isCreated());

        List<Client> clients = clientRepository.findByFirstName("New", Sort.unsorted());
        assertEquals(1, clients.size());
        assertEquals("New", clients.get(0).getFirstName());
        String id = clients.get(0).getId();

        mockMvc.perform(delete("/client/" + id).contentType("application/json"))
                .andExpect(status().isAccepted());

        clients = clientRepository.findByFirstName("New", Sort.unsorted());
        assertEquals(0, clients.size());
    }


}
