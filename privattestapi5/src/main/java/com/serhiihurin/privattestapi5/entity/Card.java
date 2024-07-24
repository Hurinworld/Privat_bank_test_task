package com.serhiihurin.privattestapi5.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "cards")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cardNumber;
    private String expiryDate;
    private Long cvv;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
