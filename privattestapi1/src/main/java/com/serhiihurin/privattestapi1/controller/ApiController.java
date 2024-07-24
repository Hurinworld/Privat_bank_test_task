package com.serhiihurin.privattestapi1.controller;

import com.serhiihurin.privattestapi1.dto.ClientDataDTO;
import com.serhiihurin.privattestapi1.service.interfaces.MessageSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final MessageSenderService messageSenderService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody ClientDataDTO clientDataDTO) {
        messageSenderService.sendMessage(clientDataDTO);
        return ResponseEntity.ok("Data sent to MQ Broker");
    }
}
