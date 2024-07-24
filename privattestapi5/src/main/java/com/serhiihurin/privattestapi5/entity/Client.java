package com.serhiihurin.privattestapi5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "clients")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    private String id;
    private String fullName;
    private String phoneNumber;
    private String address;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private List<Card> cards;
}
