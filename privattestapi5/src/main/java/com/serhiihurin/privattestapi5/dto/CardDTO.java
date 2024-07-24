package com.serhiihurin.privattestapi5.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
public record CardDTO (
        @JsonProperty Long cardNumber,
        @JsonProperty String expiryDate,
        @JsonProperty Long cvv
) implements Serializable {
}
