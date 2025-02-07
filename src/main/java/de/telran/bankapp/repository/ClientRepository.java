package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.enums.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    List<Client> findByFirstName(String name);

}
