package com.serhiihurin.privattestapi5.dao;

import com.serhiihurin.privattestapi5.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
}
