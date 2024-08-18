package com.engrkirky.moonlight.service;

import com.engrkirky.moonlight.dto.MoonlightDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MoonlightService {
    Page<MoonlightDTO> getMoonlights(Pageable pageable);
    MoonlightDTO getMoonlightByID(Integer id);
    Integer addMoonlight(MoonlightDTO moonlightDTO);
    MoonlightDTO editMoonlight(Integer id, MoonlightDTO moonlightDTO);
    void deleteMoonlight(Integer id);
}
