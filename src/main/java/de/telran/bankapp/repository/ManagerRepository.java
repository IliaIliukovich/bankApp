package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Account;
import de.telran.bankapp.entity.Client;
import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.entity.enums.ManagerStatus;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Repository
public class ManagerRepository {
    Random rand = new Random();
    List<Manager> managers = new ArrayList<>();
    @PostConstruct
    public void ManagerRepository() {
        managers.add(new Manager(1L, "Schmidt", "Hans", ManagerStatus.ACTIVE));
        managers.add(new Manager(2L, "Müller", "Anna", ManagerStatus.INACTIVE));
        managers.add(new Manager(3L, "Weber", "Peter", ManagerStatus.ACTIVE));
        managers.add(new Manager(4L, "Fischer", "Claudia", ManagerStatus.ACTIVE));
        managers.add(new Manager(5L, "Meyer", "Thomas", ManagerStatus.INACTIVE));
        managers.add(new Manager(6L, "Wagner", "Sophia", ManagerStatus.ACTIVE));
        managers.add(new Manager(7L, "Becker", "Stefan", ManagerStatus.INACTIVE));
        managers.add(new Manager(8L, "Schulz", "Julia", ManagerStatus.ACTIVE));
        managers.add(new Manager(9L, "Hoffmann", "Markus", ManagerStatus.INACTIVE));
        managers.add(new Manager(10L, "Lehmann", "Laura", ManagerStatus.ACTIVE));
    }

    public List<Manager> findAllManagers() {
        return managers;
    }

    public Optional<Manager> findManagerById(Long id) {
        return managers.stream().filter(manager -> manager.getId().equals(id)).findAny();
    }

    public Manager addManager(Manager manager) {
        manager.setId(rand.nextLong());
        managers.add(manager);
        return manager;
    }

    public void removeManager(Long id){
        managers.removeIf(manager -> manager.getId().equals(id));
    }
}


