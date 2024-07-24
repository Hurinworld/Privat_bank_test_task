package com.serhiihurin.privattestapi4.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
public record ClientDataDTO (
        @JsonProperty String clientId,
        @JsonProperty String fullName,
        @JsonProperty String phoneNumber,
        @JsonProperty String address
) implements Serializable {
}
