package com.isfa.fr.fraudDetection.repository;

import com.isfa.fr.fraudDetection.model.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank,Long> {
}
