package com.engrkirky.moonlight.mapper;

import com.engrkirky.moonlight.dto.MoonlightDTO;
import com.engrkirky.moonlight.model.Moonlight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoonlightMapperImplTest {
    private MoonlightMapperImpl underTest;
    Moonlight entity = Moonlight.builder()
            .id(1)
            .hospital("Angeles University Foundation")
            .city("Angeles City")
            .longitude(120.59498)
            .latitude(15.14511)
            .startDate(Date.from(Instant.parse("2024-08-23T22:00:00.511Z")))
            .endDate(Date.from(Instant.parse("2024-08-24T02:00:00.511Z")))
            .contactNumber("09274055567")
            .build();

    MoonlightDTO dto = new MoonlightDTO(
            1,
            "Angeles University Foundation",
            "Angeles City",
            120.59498,
            15.14511,
            Date.from(Instant.parse("2024-08-23T22:00:00.511Z")),
            Date.from(Instant.parse("2024-08-24T02:00:00.511Z")),
            "09274055567");

    @BeforeEach
    void setup() {
        underTest = new MoonlightMapperImpl();
    }

    @Test
    void convertToDTO() {
        MoonlightDTO result = underTest.convertToDTO(entity);
        assertEquals(result, dto);
    }

    @Test
    void convertToEntity() {
        Moonlight result = underTest.convertToEntity(dto);
        assertEquals(result, entity);
    }
}