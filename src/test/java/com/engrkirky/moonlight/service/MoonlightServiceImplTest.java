package com.engrkirky.moonlight.service;

import com.engrkirky.moonlight.dto.MoonlightDTO;
import com.engrkirky.moonlight.mapper.MoonlightMapper;
import com.engrkirky.moonlight.model.Moonlight;
import com.engrkirky.moonlight.repository.MoonlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoonlightServiceImplTest {
    @Mock private MoonlightRepository moonlightRepository;
    @Mock private DoctorService doctorService;
    @Mock private MoonlightMapper moonlightMapper;
    @Mock private EmailSenderService emailSenderService;
    @InjectMocks private MoonlightServiceImpl underTest;
    Moonlight moonlight = Moonlight.builder()
            .id(1)
            .hospital("Angeles University Foundation")
            .city("Angeles City")
            .longitude(120.59498)
            .latitude(15.14511)
            .startDate(Date.from(Instant.parse("2024-08-23T22:00:00.511Z")))
            .endDate(Date.from(Instant.parse("2024-08-24T02:00:00.511Z")))
            .contactNumber("09876543211")
            .build();

    MoonlightDTO moonlightDTO = new MoonlightDTO(
            1,
            "Angeles University Foundation",
            "Angeles City",
            120.59498,
            15.14511,
            Date.from(Instant.parse("2024-08-23T22:00:00.511Z")),
            Date.from(Instant.parse("2024-08-24T02:00:00.511Z")),
            "09123456789");

    @BeforeEach
    void setup () {
        underTest = new MoonlightServiceImpl(moonlightRepository, doctorService, moonlightMapper, emailSenderService);
    }

    @Test
    void getMoonlights() {
        Pageable pageable = PageRequest.of(0, 10);

        when(moonlightRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(moonlight)));
        when(moonlightMapper.convertToDTO(moonlight)).thenReturn(moonlightDTO);

        Page<MoonlightDTO> result = underTest.getMoonlights(pageable);

        verify(moonlightRepository).findAll(pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals(moonlightDTO, result.getContent().get(0));
    }

    @Test
    void getMoonlightByID() {
        Integer id = moonlightDTO.id();

        when(moonlightRepository.findById(id)).thenReturn(Optional.of(moonlight));
        when(moonlightMapper.convertToDTO(moonlight)).thenReturn(moonlightDTO);

        MoonlightDTO result = underTest.getMoonlightByID(id);

        verify(moonlightRepository).findById(id);
        assertEquals(moonlightDTO, result);
    }

    @Test
    void addMoonlight() {
        when(moonlightMapper.convertToEntity(moonlightDTO)).thenReturn(moonlight);
        when(moonlightRepository.save(moonlight)).thenReturn(moonlight);

        Integer result = underTest.addMoonlight(moonlightDTO);
        ArgumentCaptor<Moonlight> moonlightArgumentCaptor = ArgumentCaptor.forClass(Moonlight.class);

        verify(moonlightRepository).save(moonlightArgumentCaptor.capture());
        assertEquals(moonlight, moonlightArgumentCaptor.getValue());
        assertEquals(moonlight.getId(), result);
    }

    @Test
    void willThrowWhenInvalidMoonlightAdded() {
        MoonlightDTO invalidMoonlightDTO = new MoonlightDTO(
                1,
                "Angeles University Foundation",
                "Angeles City",
                12000,
                15000,
                Date.from(Instant.parse("2024-08-23T22:00:00.511Z")),
                Date.from(Instant.parse("2024-08-24T02:00:00.511Z")),
                "0912345");

        assertThrows(HttpClientErrorException.class, () -> underTest.addMoonlight(invalidMoonlightDTO));
    }

    @Test
    void editMoonlight() {
        Integer id = moonlightDTO.id();
        when(moonlightRepository.findById(id)).thenReturn(Optional.of(moonlight));
        when(moonlightRepository.save(moonlight)).thenReturn(moonlight);
        when(moonlightMapper.convertToDTO(moonlight)).thenReturn(moonlightDTO);

        MoonlightDTO result = underTest.editMoonlight(id, moonlightDTO);

        verify(moonlightRepository).save(moonlight);
        assertEquals(moonlightDTO, result);
    }

    @Test
    void deleteMoonlight() {
        Integer id = moonlightDTO.id();
        when(moonlightRepository.findById(id)).thenReturn(Optional.of(moonlight));

        underTest.deleteMoonlight(id);

        verify(moonlightRepository).deleteById(id);
    }
}