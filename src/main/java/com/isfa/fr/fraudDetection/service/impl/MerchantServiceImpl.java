package com.isfa.fr.fraudDetection.service.impl;

import com.isfa.fr.fraudDetection.model.entities.Merchant;
import com.isfa.fr.fraudDetection.repository.MerchantRepository;
import com.isfa.fr.fraudDetection.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    public Page<Merchant> getAll(Pageable pageable) {
        return merchantRepository.findAll(pageable);
    }

    public Merchant getById(Long id) {
        return merchantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marchand not found"));
    }

    public Merchant create(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    public Merchant update(Long id, Merchant m) {
        Merchant marchand = getById(id);

        marchand.setName(m.getName());
        marchand.setCategory(m.getCategory());
        marchand.setCity(m.getCity());
        marchand.setState(m.getState());
        marchand.setLatitude(m.getLatitude());
        marchand.setLongitude(m.getLongitude());

        return merchantRepository.save(marchand);
    }

    public void delete(Long id) {
        merchantRepository.deleteById(id);
    }

}
