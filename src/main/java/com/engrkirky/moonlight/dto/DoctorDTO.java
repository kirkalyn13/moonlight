package com.engrkirky.moonlight.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DoctorDTO implements Serializable {
    private Integer id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String firstName;
    private String lastName;
    private double longitude;
    private double latitude;
    private String contactNumber;
    private String email;
}
