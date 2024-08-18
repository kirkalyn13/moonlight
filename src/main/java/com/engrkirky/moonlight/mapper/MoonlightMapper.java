package com.engrkirky.moonlight.mapper;

import com.engrkirky.moonlight.dto.MoonlightDTO;
import com.engrkirky.moonlight.model.Moonlight;

public interface MoonlightMapper {
    MoonlightDTO convertToDTO(Moonlight moonlight);
    Moonlight convertToEntity(MoonlightDTO moonlightDTO);
}
