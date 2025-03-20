package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.enums.ClientStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientRepositoryTest {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void findByFirstName() {
        List<Client> result = repository.findByFirstName("Anna", Sort.by("lastName"));
        result.forEach(client -> assertEquals("Anna", client.getFirstName()));
        assertEquals(2, result.size());
        assertTrue(result.get(0).getLastName().compareTo(result.get(1).getLastName()) < 0);
    }

    @Test
    void updateStatus() {
        Client before = repository.findById("20980395-20d0-4ea8-8e4b-de2252a028eb").get();
        assertEquals(ClientStatus.INACTIVE, before.getStatus());
        repository.updateStatus("20980395-20d0-4ea8-8e4b-de2252a028eb", ClientStatus.ACTIVE);

        testEntityManager.clear();

        Client after = repository.findById("20980395-20d0-4ea8-8e4b-de2252a028eb").get();
        assertEquals(ClientStatus.ACTIVE, after.getStatus());
    }

}