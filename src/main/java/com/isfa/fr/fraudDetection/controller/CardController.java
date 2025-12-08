package com.isfa.fr.fraudDetection.controller;

import com.isfa.fr.fraudDetection.constants.Paths;
import com.isfa.fr.fraudDetection.dto.PageDto;
import com.isfa.fr.fraudDetection.model.entities.Card;
import com.isfa.fr.fraudDetection.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Paths.CARD)
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public Card create(@RequestBody Card carte, @RequestParam Long clientId, @RequestParam Long bankId) {
        return cardService.create(carte, clientId, bankId);
    }

    @GetMapping
    public PageDto<Card> getAll(Pageable pageable) {

        Page<Card> cardsPage = cardService.getAll(pageable);

        return new PageDto<>(
                cardsPage.getContent(),
                cardsPage.getNumber(),
                cardsPage.getSize(),
                cardsPage.getTotalElements(),
                cardsPage.getTotalPages()
        );
    }

    @GetMapping("/{id}")
    public Card getById(@PathVariable Long id) {
        return cardService.getById(id);
    }

    @PutMapping("/{id}")
    public Card update(@PathVariable Long id, @RequestBody Card card) {
        return cardService.update(id, card);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        cardService.delete(id);
        return true;
    }
}
