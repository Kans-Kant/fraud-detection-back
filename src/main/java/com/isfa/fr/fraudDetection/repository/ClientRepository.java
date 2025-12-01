package com.isfa.fr.fraudDetection.repository;

import com.isfa.fr.fraudDetection.model.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
