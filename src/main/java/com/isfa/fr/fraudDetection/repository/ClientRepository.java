package com.isfa.fr.fraudDetection.repository;

import com.isfa.fr.fraudDetection.model.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c " +
            "WHERE LOWER(c.firstName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :query, '%'))" +
            "   OR LOWER(c.country) LIKE LOWER(CONCAT('%', :query, '%'))" +
            "   OR LOWER(c.city) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Client> searchByQuery(@Param("query") String query);

}
