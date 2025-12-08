package com.isfa.fr.fraudDetection.service;

import com.isfa.fr.fraudDetection.model.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {

    Page<Client> getAll(Pageable pageable);
    Client getById(Long id);
    Client create(Client client);
    Client update(Long id, Client details);
    void delete(Long id);
    List<Client> searchByQuery(String queryText);

}
