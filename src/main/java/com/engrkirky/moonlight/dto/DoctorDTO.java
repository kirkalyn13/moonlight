package com.engrkirky.moonlight.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private double longitude;
    private double latitude;
    private String contactNumber;
    private String email;
}
