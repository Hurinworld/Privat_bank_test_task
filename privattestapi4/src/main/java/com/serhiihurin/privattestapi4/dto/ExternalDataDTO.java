package com.serhiihurin.privattestapi4.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
public record ExternalDataDTO (
        @JsonProperty String clientId,
        @JsonProperty String fullName,
        @JsonProperty String phoneNumber,
        @JsonProperty String address,
        @JsonProperty List<CardDTO> cards
) implements Serializable {
}
