package com.isfa.fr.fraudDetection.service.impl;

import com.isfa.fr.fraudDetection.model.entities.Bank;
import com.isfa.fr.fraudDetection.repository.BankRepository;
import com.isfa.fr.fraudDetection.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankRepository bankRepository;

    public Page<Bank> getAll(Pageable pageable) {
        return bankRepository.findAll(pageable);
    }

    public Bank getById(Long id) {
        return bankRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banque not found"));
    }

    public Bank create(Bank bank) {
        return bankRepository.save(bank);
    }

    public Bank update(Long id, Bank bankDetails) {
        Bank bank = getById(id);
        bank.setName(bankDetails.getName());
        bank.setSwiftCode(bankDetails.getSwiftCode());
        bank.setCountry(bankDetails.getCountry());
        bank.setCity(bankDetails.getCity());
        return bankRepository.save(bank);
    }

    public void delete(Long id) {
        bankRepository.deleteById(id);
    }

}
