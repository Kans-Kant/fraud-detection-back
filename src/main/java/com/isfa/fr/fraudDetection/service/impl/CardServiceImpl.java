package com.isfa.fr.fraudDetection.service.impl;

import com.isfa.fr.fraudDetection.model.entities.Bank;
import com.isfa.fr.fraudDetection.model.entities.Card;
import com.isfa.fr.fraudDetection.model.entities.Client;
import com.isfa.fr.fraudDetection.repository.BankRepository;
import com.isfa.fr.fraudDetection.repository.CardRepository;
import com.isfa.fr.fraudDetection.repository.ClientRepository;
import com.isfa.fr.fraudDetection.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BankRepository bankRepository;


    public Page<Card> getAll(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    public Card getById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carte not found"));
    }

    public Card create(Card card, Long clientId, Long bankId) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("Banque not found"));

        card.setClient(client);
        card.setBank(bank);

        return cardRepository.save(card);
    }

    public Card update(Long id, Card details) {
        Card carte = getById(id);

        carte.setCcNum(details.getCcNum());
        carte.setCardType(details.getCardType());
        carte.setExpDate(details.getExpDate());

        return cardRepository.save(carte);
    }

    public void delete(Long id) {
        cardRepository.deleteById(id);
    }

}
