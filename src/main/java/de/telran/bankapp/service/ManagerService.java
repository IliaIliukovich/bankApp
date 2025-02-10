package de.telran.bankapp.service;

import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.entity.enums.ManagerStatus;
import de.telran.bankapp.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {
    private ManagerRepository repository;

    @Autowired
    public ManagerService(ManagerRepository repository) {
        this.repository = repository;
    }

    public List<Manager> getAll(){
        return repository.findAll();
    }

    public List<Manager> findByName(String firstName){
        return repository.findByFirstName(firstName);
    }

    public Manager addManager(Manager manager){
        return repository.save(manager);
    }

    public Optional<Manager> updateManager(Manager manager) {
        Long id = manager.getId();
        Optional<Manager> optional = repository.findById(id);
        if (optional.isPresent()) {
            Manager updatedManager = repository.save(manager);
            return Optional.of(updatedManager);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Manager> getManagerById(Long id) {
        Optional<Manager> optional = repository.findById(id);
        if (optional.isPresent()) {
            Manager found = optional.get();
            return Optional.of(found);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Manager> changeManagerStatus( Long id,  String status){
        Optional<Manager> optional = repository.findById(id);
        if (optional.isPresent()) {
            Manager changedStatusManager = optional.get();
            ManagerStatus changedStatusManagerStatus = status == null ? ManagerStatus.ACTIVE : ManagerStatus.valueOf(status);
            changedStatusManager.setStatus(changedStatusManagerStatus);
            Manager saved = repository.save(changedStatusManager);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }

    public Boolean deleteManager(Long id){
        Boolean flag = false;
        Optional<Manager> managerForDelete = getManagerById(id);
        if(managerForDelete.isPresent()) {
            repository.deleteById(id);
            flag = true;
        }
        return flag;
    }

}
