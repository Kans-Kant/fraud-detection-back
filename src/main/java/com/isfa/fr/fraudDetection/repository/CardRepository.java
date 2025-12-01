package com.isfa.fr.fraudDetection.repository;

import com.isfa.fr.fraudDetection.model.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
