package com.isfa.fr.fraudDetection.service;

import com.isfa.fr.fraudDetection.model.entities.Client;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {

    Page<Client> getAll(int page, int size);
    Client getById(Long id);
    Client create(Client client);
    Client update(Long id, Client details);
    void delete(Long id);

}
