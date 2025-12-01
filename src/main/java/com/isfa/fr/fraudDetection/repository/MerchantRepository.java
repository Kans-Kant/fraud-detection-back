package com.isfa.fr.fraudDetection.repository;

import com.isfa.fr.fraudDetection.model.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
