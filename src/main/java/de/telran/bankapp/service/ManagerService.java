package de.telran.bankapp.service;

import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.entity.enums.ManagerStatus;
import de.telran.bankapp.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {
    private ManagerRepository repository;

    @Autowired
    public ManagerService(ManagerRepository repository) {
        this.repository = repository;
    }


    public List<Manager> getAllManagers(){return repository.findAllManagers();}

    public Manager addManager(Manager manager){return repository.addManager(manager);}

    public Optional<Manager> updateManager(Manager manager) {
        Long id = manager.getId();
        Optional<Manager> optional = repository.findManagerById(id);
        if (optional.isPresent()) {
            Manager found = optional.get();
            found.setLastName(manager.getLastName());
            found.setFirstName(manager.getFirstName());
            found.setStatus(manager.getStatus());
            return Optional.of(found);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Manager> getManagerById(Long id) {
        Optional<Manager> optional = repository.findManagerById(id);
        if (optional.isPresent()) {
            Manager found = optional.get();
            return Optional.of(found);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Manager> changeManagerStatus(@RequestParam Long id, @RequestParam(required = false) String status){
        Optional<Manager> optional = repository.findManagerById(id);
        if (optional.isPresent()) {
            Manager found = optional.get();
            ManagerStatus managerStatus = status == null ? ManagerStatus.ACTIVE : ManagerStatus.valueOf(status);
            found.setStatus(managerStatus);
            return Optional.of(found);
        } else {
            return Optional.empty();
        }
    }

    public Boolean deleteManager(Long id){
        Boolean flag = false;
        Optional<Manager> managerForDelete = getManagerById(id);
        if(managerForDelete.isPresent()) {
            repository.removeManager(id);
            flag = true;
        }
        return flag;
    }

}
