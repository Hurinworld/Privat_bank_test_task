package com.serhiihurin.privattestapi3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
public record ClientDataDTO (
        @JsonProperty String clientId,
        @JsonProperty String fullName,
        @JsonProperty String phoneNumber
) implements Serializable {
}
