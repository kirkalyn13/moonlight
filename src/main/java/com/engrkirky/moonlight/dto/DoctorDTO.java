package com.engrkirky.moonlight.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDTO {
    private Integer id;
    @JsonIgnore
    private String username;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private double longitude;
    private double latitude;
    private String contactNumber;
    private String email;
}
