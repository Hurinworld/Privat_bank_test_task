package com.serhiihurin.privattestapi1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record ClientDataDTO (@JsonProperty String clientId) implements Serializable {
}
