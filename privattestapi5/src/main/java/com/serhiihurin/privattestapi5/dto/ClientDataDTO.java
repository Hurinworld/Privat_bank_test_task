package com.serhiihurin.privattestapi5.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

public record ClientDataDTO (
        @JsonProperty String clientId,
        @JsonProperty String fullName,
        @JsonProperty String phoneNumber,
        @JsonProperty String address,
        @JsonProperty List<CardDTO> cards
) implements Serializable {
}
