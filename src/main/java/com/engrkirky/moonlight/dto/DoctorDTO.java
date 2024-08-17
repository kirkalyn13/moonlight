package com.engrkirky.moonlight.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DoctorDTO (
    Integer id,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String username,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password,
    String firstName,
    String lastName,
    double longitude,
    double latitude,
    String contactNumber,
    String email,
    boolean isAvailable
) {}
