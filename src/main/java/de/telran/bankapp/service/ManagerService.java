package de.telran.bankapp.service;

import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.entity.enums.ClientStatus;
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

    public  List<Manager> findByFirstNameAndLastName(String firstName,String lastName){
        return repository.findByFirstNameAndLastName(firstName,lastName);
    }

    public List<Manager> findFirstLetterFromFirstNameAndFirstLetterFromLastName(String firstName,String lastName){
        return repository.findFirstLetterFromFirstNameAndFirstLetterFromLastName(firstName,lastName);
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

    public Integer changeManagerStatus(Long id,ManagerStatus status){
        ManagerStatus managerstatus = status == null ? ManagerStatus.ACTIVE : status;
        return repository.updateStatus(id,managerstatus);
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
