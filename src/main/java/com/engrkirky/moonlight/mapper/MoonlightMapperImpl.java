package com.engrkirky.moonlight.mapper;

import com.engrkirky.moonlight.dto.MoonlightDTO;
import com.engrkirky.moonlight.model.Doctor;
import com.engrkirky.moonlight.model.Moonlight;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MoonlightMapperImpl implements MoonlightMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public MoonlightMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MoonlightDTO convertToDTO(Moonlight moonlight) {
        return new MoonlightDTO(
                moonlight.getId(),
                moonlight.getHospital(),
                moonlight.getCity(),
                moonlight.getLongitude(),
                moonlight.getLatitude(),
                moonlight.getStartDate(),
                moonlight.getEndDate()
        );
    }

    @Override
    public Moonlight convertToEntity(MoonlightDTO moonlightDTO) {
        return modelMapper.map(moonlightDTO, Moonlight.class);
    }
}
