package de.telran.bankapp.service;

import de.telran.bankapp.entity.Client;
import de.telran.bankapp.dto.ManagerCreateDto;
import de.telran.bankapp.dto.ManagerDto;
import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.entity.enums.ManagerStatus;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.mapper.ManagerMapper;
import de.telran.bankapp.repository.ClientRepository;
import de.telran.bankapp.repository.ManagerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ManagerService {

    private static Logger logger = LogManager.getLogger(ManagerService.class);

    private final ManagerRepository repository;
    private final ClientRepository clientRepository;
    private final ManagerMapper mapper;

    @Autowired
    public ManagerService(ManagerRepository repository, ClientRepository clientRepository, ManagerMapper mapper) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.mapper = mapper;
    }

    public List<ManagerDto> getAll(){
        List<Manager> managers = repository.findAll();
        logger.debug("Managers retrieved from db");
        logger.debug("manager ids: {}", () -> managers.stream().map(Manager::getId).toList());
        return mapper.entityListToDto(managers);
    }

    public List<ManagerDto> findByName(String firstName){
        List<Manager> managers = repository.findByFirstName(firstName);
        return mapper.entityListToDto(managers);
    }

    public  List<ManagerDto> findByFirstNameAndLastName(String firstName,String lastName){
        List<Manager> managers = repository.findByFirstNameAndLastName(firstName,lastName);
        return mapper.entityListToDto(managers);
    }

    public List<ManagerDto> findFirstLetterFromFirstNameAndFirstLetterFromLastName(
            String firstName,String lastName){
        List<Manager> managers = repository.findFirstLetterFromFirstNameAndFirstLetterFromLastName(
                firstName,lastName);
        return mapper.entityListToDto(managers);
    }

    @Transactional
    public ManagerDto addManager( ManagerCreateDto dto){
        Manager manager = mapper.createDtoToEntity(dto);
        Manager savedManager = repository.save(manager);
        return mapper.entityToDto(savedManager);
    }

    @Transactional
    public ManagerDto updateManager(ManagerDto manager) {
        Long id = manager.getId();
        Optional<Manager> optional = repository.findById(id);
        if (optional.isPresent()) {
            Manager savedManager = repository.save(mapper.dtoToEntity(manager));
            return mapper.entityToDto(savedManager);
        }
        throw new BankAppResourceNotFoundException("Manager with id = " + id + " not found in database");
    }

    public ManagerDto getManagerById(Long id) {
        Optional<Manager> optional = repository.findById(id);
        if (optional.isPresent()) {
            ManagerDto found = mapper.entityToDto(optional.get()) ;
            return found;
        }
        throw new BankAppResourceNotFoundException("Manager with id = " + id + " not found in database");
    }

    @Transactional
    public void changeManagerStatus(Long id,ManagerStatus status){
        ManagerStatus managerstatus = status == null ? ManagerStatus.ACTIVE : status;
        int updated =  repository.updateStatus(id,managerstatus);
        if(updated == 0)  throw new BankAppResourceNotFoundException("Manager with id = " + id + " not found in database");
    }

    @Transactional
    public void deleteManager(Long id){
        Optional<Manager> managerForDelete = repository.findById(id);
        if (managerForDelete.isPresent()) {
            List<Client> clients = managerForDelete.get().getClients();
            for (Client c  : clients) {
                c.setManager(null);
            }
            clientRepository.saveAll(clients);
            repository.deleteById(id);
        } else {
            throw new BankAppResourceNotFoundException("Manager with id = " + id + " not found in database");
        }
    }

    @Transactional
    public ManagerDto updateLastName(Long id, String newLastName){
        Optional<Manager> optional = repository.findById(id);
        if(optional.isPresent()){
            Manager manager = optional.get();
            manager.setLastName(newLastName);
            Manager managerWithNewLastName = repository.save(manager);
            return mapper.entityToDto(managerWithNewLastName);
        }
        throw new BankAppResourceNotFoundException("Manager with id = " + id + " not found in database");
    }



}
