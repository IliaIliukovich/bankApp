package de.telran.bankapp.repository;

import de.telran.bankapp.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {

    Optional<AppUser> findAppUserByEmail(String email);


}
