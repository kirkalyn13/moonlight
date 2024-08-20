package com.engrkirky.moonlight.mapper;

import com.engrkirky.moonlight.dto.MoonlightDTO;
import com.engrkirky.moonlight.model.Moonlight;
import org.springframework.stereotype.Component;

@Component
public class MoonlightMapperImpl implements MoonlightMapper {
    @Override
    public MoonlightDTO convertToDTO(Moonlight moonlight) {
        return new MoonlightDTO(
                moonlight.getId(),
                moonlight.getHospital(),
                moonlight.getCity(),
                moonlight.getLongitude(),
                moonlight.getLatitude(),
                moonlight.getStartDate(),
                moonlight.getEndDate(),
                moonlight.getContactNumber()
        );
    }

    @Override
    public Moonlight convertToEntity(MoonlightDTO moonlightDTO) {
        return Moonlight.builder()
                .id(moonlightDTO.id())
                .hospital(moonlightDTO.hospital())
                .city(moonlightDTO.city())
                .longitude(moonlightDTO.longitude())
                .latitude(moonlightDTO.latitude())
                .startDate(moonlightDTO.startDate())
                .endDate(moonlightDTO.endDate())
                .contactNumber(moonlightDTO.contactNumber())
                .build();
    }
}
