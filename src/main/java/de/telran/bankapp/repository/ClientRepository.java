package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.enums.ClientStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    List<Client> findByFirstName(String name);

    Optional<Client> findByFirstNameAndLastName(String firstName, String lastName);

    List<Client> findByLastNameStartingWithAndAddressContainingIgnoreCase(String surname, String address);

    @Query("select c from Client c where c.lastName like :surname% and c.address like %:address%")
    List<Client> customQuery(String surname, String address);

    @NativeQuery("select * from client c where c.last_name like :surname% and c.address like %:address%")
    List<Client> nativeQuery(String surname, String address);

    @Query("update Client c set c.status = :status where c.id = :id")
    @Modifying
    @Transactional
    int updateStatus(String id, ClientStatus status);

}
