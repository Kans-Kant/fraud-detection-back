package com.isfa.fr.fraudDetection.service.impl;

import com.isfa.fr.fraudDetection.model.entities.Client;
import com.isfa.fr.fraudDetection.repository.ClientRepository;
import com.isfa.fr.fraudDetection.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Page<Client> getAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public Client create(Client client) {
        return clientRepository.save(client);
    }

    public Client update(Long id, Client details) {
        Client client = getById(id);

        client.setFirstName(details.getFirstName());
        client.setLastName(details.getLastName());
        client.setGender(details.getGender());
        client.setStreet(details.getStreet());
        client.setCity(details.getCity());
        client.setLatitude(details.getLatitude());
        client.setLongitude(details.getLongitude());

        return clientRepository.save(client);
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> searchByQuery(String queryText) {
        return clientRepository.searchByQuery(queryText);
    }

}
