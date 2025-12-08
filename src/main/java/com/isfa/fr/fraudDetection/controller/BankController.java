package com.isfa.fr.fraudDetection.controller;

import com.isfa.fr.fraudDetection.constants.Paths;
import com.isfa.fr.fraudDetection.dto.PageDto;
import com.isfa.fr.fraudDetection.model.entities.Bank;
import com.isfa.fr.fraudDetection.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Paths.BANK)
public class BankController {

    @Autowired
    private BankService bankService;

    @GetMapping
    public PageDto<Bank> getAll(Pageable pageable) {
        Page<Bank> banksPage = bankService.getAll(pageable);

        return new PageDto<>(
                banksPage.getContent(),
                banksPage.getNumber(),
                banksPage.getSize(),
                banksPage.getTotalElements(),
                banksPage.getTotalPages()
        );
    }

    @GetMapping("/{id}")
    public Bank getById(@PathVariable Long id) {
        return bankService.getById(id);
    }

    @PostMapping
    public Bank create(@RequestBody Bank bank) {
        return bankService.create(bank);
    }

    @PutMapping("/{id}")
    public Bank update(@PathVariable Long id, @RequestBody Bank banque) {
        return bankService.update(id, banque);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        bankService.delete(id);
        return true;
    }

}
