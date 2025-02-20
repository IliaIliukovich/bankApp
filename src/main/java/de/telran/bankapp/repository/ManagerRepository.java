package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.entity.enums.ManagerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long> {

    List<Manager> findByFirstName(String name);

    List<Manager> findByFirstNameAndLastName(String firstName,String lastname);

    @Query("select m from Manager m where m.lastName like :lastname% and m.firstName like :firstName%")
    List<Manager> findFirstLetterFromFirstNameAndFirstLetterFromLastName(String firstName, String lastname);

    @Query("update Manager m set m.status = :status where m.id = :id")
    @Modifying
    int updateStatus(Long id, ManagerStatus status);

}


