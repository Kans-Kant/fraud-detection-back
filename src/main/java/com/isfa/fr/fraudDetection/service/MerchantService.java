package com.isfa.fr.fraudDetection.service;

import com.isfa.fr.fraudDetection.model.entities.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MerchantService {

    Page<Merchant> getAll(Pageable pageable);
    Merchant getById(Long id);
    Merchant create(Merchant merchant);
    Merchant update(Long id, Merchant m);
    void delete(Long id);

}
