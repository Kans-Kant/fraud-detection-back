package com.isfa.fr.fraudDetection.service;

import com.isfa.fr.fraudDetection.model.entities.Card;
import org.springframework.data.domain.Page;

public interface CardService {

    Page<Card> getAll(int page, int size);
    Card getById(Long id);
    Card create(Card card, Long clientId, Long bankId);
    Card update(Long id, Card details);
    void delete(Long id);
}
