package com.isfa.fr.fraudDetection.controller;

import com.isfa.fr.fraudDetection.constants.Paths;
import com.isfa.fr.fraudDetection.dto.PageDto;
import com.isfa.fr.fraudDetection.model.entities.Client;
import com.isfa.fr.fraudDetection.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Paths.CLIENT)
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public PageDto<Client> getAll(Pageable pageable) {
        Page<Client> clientsPage = clientService.getAll(pageable);

        return new PageDto<>(
                clientsPage.getContent(),
                clientsPage.getNumber(),
                clientsPage.getSize(),
                clientsPage.getTotalElements(),
                clientsPage.getTotalPages()
        );
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id) {
        return clientService.getById(id);
    }

    @GetMapping("/search")
    public List<Client> getById(@RequestParam String queryText) {
        return clientService.searchByQuery(queryText);
    }

    @PostMapping
    public Client create(@RequestBody Client client) {
        return clientService.create(client);
    }

    @PutMapping("/{id}")
    public Client update(@PathVariable Long id, @RequestBody Client client) {
        return clientService.update(id, client);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        clientService.delete(id);
        return true;
    }

}
