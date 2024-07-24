package com.serhiihurin.privattestapi5.controller;

import com.serhiihurin.privattestapi5.entity.Client;
import com.serhiihurin.privattestapi5.facade.interfaces.DatabaseInfoSaverFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/db-service")
@RequiredArgsConstructor
public class DatabaseServiceController {
    private final DatabaseInfoSaverFacade databaseInfoSaverFacade;

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(databaseInfoSaverFacade.getClients());
    }
}
