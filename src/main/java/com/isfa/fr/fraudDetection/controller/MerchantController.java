package com.isfa.fr.fraudDetection.controller;

import com.isfa.fr.fraudDetection.constants.Paths;
import com.isfa.fr.fraudDetection.dto.PageDto;
import com.isfa.fr.fraudDetection.model.entities.Merchant;
import com.isfa.fr.fraudDetection.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Paths.MERCHANT)
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @GetMapping
    public PageDto<Merchant> getAll(Pageable pageable) {
        Page<Merchant> merchantPage =  merchantService.getAll(pageable);

        return new PageDto<>(
                merchantPage.getContent(),
                merchantPage.getNumber(),
                merchantPage.getSize(),
                merchantPage.getTotalElements(),
                merchantPage.getTotalPages()
        );
    }

    @GetMapping("/{id}")
    public Merchant getById(@PathVariable Long id) {
        return merchantService.getById(id);
    }

    @PostMapping
    public Merchant create(@RequestBody Merchant marchand) {
        return merchantService.create(marchand);
    }

    @PutMapping("/{id}")
    public Merchant update(@PathVariable Long id, @RequestBody Merchant marchand) {
        return merchantService.update(id, marchand);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        merchantService.delete(id);
        return true;
    }

}
