package com.serhiihurin.privattestapi2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
public record ClientDataDTO (
        @JsonProperty String clientId
) implements Serializable {
}
