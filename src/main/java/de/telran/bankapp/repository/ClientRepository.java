package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    List<Client> findByFirstName(String name);

    List<Client> findByLastNameStartingWithAndAddressContainingIgnoreCase(String surname, String address);

    @Query("select c from Client c where c.lastName like :surname% and c.address like %:address%")
    List<Client> customQuery(String surname, String address);

    @NativeQuery("select * from client c where c.last_name like :surname% and c.address like %:address%")
    List<Client> nativeQuery(String surname, String address);
}
