package com.serhiihurin.privattestapi2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
public record ExternalDataDTO (
        @JsonProperty String clientId,
        @JsonProperty String fullName,
        @JsonProperty String phoneNumber
) implements Serializable {
}
