package de.telran.bankapp.repository;

import de.telran.bankapp.entity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementRepository extends JpaRepository<Agreement, String> {
}
