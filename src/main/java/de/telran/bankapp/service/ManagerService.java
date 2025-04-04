package de.telran.bankapp.service;

import de.telran.bankapp.dto.ClientDto;
import de.telran.bankapp.dto.ManagerCreateDto;
import de.telran.bankapp.dto.ManagerDto;
import de.telran.bankapp.entity.Manager;
import de.telran.bankapp.entity.enums.ManagerStatus;
import de.telran.bankapp.exception.BankAppResourceNotFoundException;
import de.telran.bankapp.mapper.ClientMapper;
import de.telran.bankapp.mapper.ManagerMapper;
import de.telran.bankapp.repository.ClientRepository;
import de.telran.bankapp.repository.ManagerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import de.telran.bankapp.entity.Client;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ManagerService {

    private static Logger logger = LogManager.getLogger(ManagerService.class);

    private ManagerRepository repository;
    private ManagerMapper mapper;
    private ClientRepository clientRepository;
    public ClientMapper clientMapper;

    @Autowired
    public ManagerService(ManagerRepository repository, ManagerMapper mapper, ClientRepository clientRepository, ClientMapper clientMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
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
    /*
    corect query in Postman:
    get: http://localhost:8080/manager/search?firstName=PeterNew
     */

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
    /*
    corect query in Postman:
    get: http://localhost:8080/manager/searchByFirstLetterFromFirstNameAndFirstLetterFromLastName?firstName=P&&lastName=W
     */

    @Transactional
    public ManagerDto addManager( ManagerCreateDto dto){
        Manager manager = mapper.createDtoToEntity(dto);
        Manager savedManager = repository.save(manager); // persist
        return mapper.entityToDto(savedManager);
    }
    /*
    corect query in Postman:
    post: http://localhost:8080/manager
    {
    "lastName": "WeberNew",
    "firstName": "PeterNew",
    "clientsDto": [
        {
            "id": "20980395-20d0-4ea8-8e4b-de2252a028eb",
            "lastName": "Müller",
            "firstName": "Anna",
            "taxCode": "DE987654321",
            "email": "a.mueller@example.com",
            "address": "Munich, Germany",
            "phone": "+49 89 7654321",
            "status": "INACTIVE"
        }
    ]
}
N.B.!! [] is very important becouse ManagerCreateDto has field: private List<ClientDto> clientsDto;
which in JSON is [] !!!
     */

    @Transactional
    public ManagerDto updateManager(ManagerDto managerDto) {
        Optional<Manager> optional = repository.findById(managerDto.getId());
        if (optional.isPresent()) {
            Manager manager = mapper.dtoToEntity(managerDto);
            Manager saved = repository.save(manager); // merge
            return mapper.entityToDto(saved);
        }
        throw new BankAppResourceNotFoundException("Manager with id = " + managerDto.getId() + " not found in database");
    }
    /*
    corect query in Postman:
    put: http://localhost:8080/manager
    {"id": 11,
    "lastName": "WeberNewNew",
    "firstName": "PeterNewOld",
    "status": "ACTIVE",
    "clientsDto": [
        {
            "id": "20980395-20d0-4ea8-8e4b-de2252a028eb",
            "lastName": "Müller",
            "firstName": "Anna",
            "taxCode": "DE987654321",
            "email": "a.mueller@example.com",
            "address": "Munich, Germany",
            "phone": "+49 89 7654321",
            "status": "INACTIVE"
        },
        {
                "id": "b03dbcfc-d047-49a7-acbb-f3b1329e1fee",
                "lastName": "Fischer",
                "firstName": "Lukas",
                "taxCode": "DE234567890",
                "email": "l.fischer@example.com",
                "address": "Stuttgart, Germany",
                "phone": "+49 711 9876543",
                "status": "INACTIVE",
                "managerId": 3
            }
    ]
}
     */

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
