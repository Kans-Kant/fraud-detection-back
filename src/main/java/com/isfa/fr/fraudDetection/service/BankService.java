package com.isfa.fr.fraudDetection.service;

import com.isfa.fr.fraudDetection.model.entities.Bank;
import org.springframework.data.domain.Page;

public interface BankService {

    Page<Bank> getAll(int page, int size);
    Bank getById(Long id);
    Bank create(Bank bank);
    Bank update(Long id, Bank bankDetails);
    void delete(Long id);

}
