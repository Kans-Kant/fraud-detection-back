package com.isfa.fr.fraudDetection.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isfa.fr.fraudDetection.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private Long fraud;
    private Long gloStep;
    private Long step;
    private Long ts;

    private LocalDateTime transactionTime;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    // Le score de fraude sera calculé à la volée, donc pas persisté
    @Transient
    private Double fraudScore;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
