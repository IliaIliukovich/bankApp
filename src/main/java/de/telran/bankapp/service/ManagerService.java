package de.telran.bankapp.service;

import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.entity.enums.ManagerStatus;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.repository.ManagerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    private static Logger logger = LogManager.getLogger(ManagerService.class);

    private ManagerRepository repository;

    @Autowired
    public ManagerService(ManagerRepository repository) {
        this.repository = repository;
    }

    public List<Manager> getAll(){
        List<Manager> managers = repository.findAll();
        logger.debug("Managers retrieved from db");
        logger.debug("manager ids: {}", () -> managers.stream().map(Manager::getId).toList());
        return managers;
    }

    public List<Manager> findByName(String firstName){
//        List<Manager> optional = repository.findByFirstName(firstName);
//        if(!optional.isEmpty()){
//            return optional.get();
//        }
//        throw  new BankAppResourceNotFoundException("Manager with first name = " + firstName + " not found in database");
        return repository.findByFirstName(firstName);
    }

    public  List<Manager> findByFirstNameAndLastName(String firstName,String lastName){
        return repository.findByFirstNameAndLastName(firstName,lastName);
    }

    public List<Manager> findFirstLetterFromFirstNameAndFirstLetterFromLastName(String firstName,String lastName){
//        List<Manager> optional = repository.findFirstLetterFromFirstNameAndFirstLetterFromLastName(firstName,lastName);
//        if(!optional.isEmpty()){
//            return optional;
//        }
//        throw  new BankAppResourceNotFoundException("Manager with first name with first letter: "
//                + firstName + " and last name with first letter: "
//                +lastName+ " not found in database");
        return repository.findFirstLetterFromFirstNameAndFirstLetterFromLastName(firstName,lastName);
    }

    public Manager addManager(Manager manager){
        return repository.save(manager);
    }

    public Manager updateManager(Manager manager) {
        Long id = manager.getId();
        Optional<Manager> optional = repository.findById(id);
        if (optional.isPresent()) {
            return repository.save(manager);
        }
        throw  new BankAppResourceNotFoundException("Manager with id = " + id + " not found in database");
    }

    public Manager getManagerById(Long id) {
        Optional<Manager> optional = repository.findById(id);
        if (optional.isPresent()) {
            Manager found = optional.get();
            return found;
        }
        throw  new BankAppResourceNotFoundException("Manager with id = " + id + " not found in database");
    }

    public void changeManagerStatus(Long id,ManagerStatus status){
        ManagerStatus managerstatus = status == null ? ManagerStatus.ACTIVE : status;
        int updated =  repository.updateStatus(id,managerstatus);
        if(updated == 0)  throw new BankAppResourceNotFoundException("Manager with id = " + id + " not found in database");
    }

    public void deleteManager(Long id){
        Optional<Manager> managerForDelete = repository.findById(id);
        if (managerForDelete.isPresent()) {
            repository.deleteById(id);
        }
        throw  new BankAppResourceNotFoundException("Manager with id = " + id + " not found in database");
    }

    public Manager updateLastName(Long id, String newLastName){
        Optional<Manager> optional = repository.findById(id);
        if(optional.isPresent()){
            Manager manager = optional.get();
            manager.setLastName(newLastName);
            Manager managerWithNewLastName = repository.save(manager);
            return managerWithNewLastName;
        }
        throw  new BankAppResourceNotFoundException("Manager with id = " + id + " not found in database");
    }



}
