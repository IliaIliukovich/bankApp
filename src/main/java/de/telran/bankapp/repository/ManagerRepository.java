package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.entity.enums.ClientStatus;
import de.telran.bankapp.entity.enums.ManagerStatus;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long> {

    List<Manager> findByFirstName(String name);

    List<Manager> findByFirstNameAndLastName(String firstName,String lastname);

    @Query("select m from Manager m where m.lastName like :lastname% and m.firstName like :firstName%")
    List<Manager> findFirstLetterFromFirstNameAndFirstLetterFromLastName(String firstName, String lastname);

    @Query("update Manager m set m.status = :status where m.id = :id")
    @Modifying
    @Transactional
    int updateStatus(Long id, ManagerStatus status);

}


