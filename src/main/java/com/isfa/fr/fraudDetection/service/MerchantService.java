package com.isfa.fr.fraudDetection.service;

import com.isfa.fr.fraudDetection.model.entities.Merchant;
import org.springframework.data.domain.Page;

public interface MerchantService {

    Page<Merchant> getAll(int page, int size);
    Merchant getById(Long id);
    Merchant create(Merchant merchant);
    Merchant update(Long id, Merchant m);
    void delete(Long id);

}
