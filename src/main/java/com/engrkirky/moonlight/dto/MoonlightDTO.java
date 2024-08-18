package com.engrkirky.moonlight.dto;

import java.util.Date;

public record MoonlightDTO (
        Integer id,
        String hospital,
        String city,
        double longitude,
        double latitude,
        Date startDate,
        Date endDate
) {
}
