package com.engrkirky.moonlight.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DoctorLocationDTO implements Serializable {
    private double longitude;
    private double latitude;
}
