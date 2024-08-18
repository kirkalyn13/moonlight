package com.engrkirky.moonlight.service;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.dto.MoonlightDTO;
import com.engrkirky.moonlight.mapper.MoonlightMapper;
import com.engrkirky.moonlight.repository.MoonlightRepository;
import com.engrkirky.moonlight.util.LocationUtil;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class MoonlightServiceImpl implements MoonlightService {
    private final MoonlightRepository moonlightRepository;
    private final DoctorService doctorService;
    private final MoonlightMapper moonlightMapper;

    @Autowired
    public MoonlightServiceImpl(MoonlightRepository moonlightRepository, DoctorService doctorService, MoonlightMapper moonlightMapper) {
        this.moonlightRepository = moonlightRepository;
        this.doctorService = doctorService;
        this.moonlightMapper = moonlightMapper;
    }

    @Override
    public Page<MoonlightDTO> getMoonlights(Pageable pageable) {
        return moonlightRepository
                .findAll(pageable)
                .map(moonlightMapper::convertToDTO);
    }

    @Override
    public MoonlightDTO getMoonlightByID(Integer id) {
        return moonlightMapper.convertToDTO(moonlightRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Moonlight with ID of %d not found.", id))));
    }

    @Override
    @Transactional
    public Integer addMoonlight(MoonlightDTO moonlightDTO) {
        if (!validateMoonlight(moonlightDTO)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        notifyAvailableDoctors(moonlightDTO);
        return moonlightRepository.save(moonlightMapper.convertToEntity(moonlightDTO)).getId();
    }

    @Override
    @Transactional
    public MoonlightDTO editMoonlight(Integer id, MoonlightDTO moonlightDTO) {
        return moonlightRepository
                .findById(id)
                .map(moonlight -> {
                    if (moonlightDTO.hospital() != null && !moonlightDTO.hospital().equals(Strings.EMPTY)) moonlight.setHospital(moonlightDTO.hospital());
                    if (moonlightDTO.city() != null && !moonlightDTO.city().equals(Strings.EMPTY)) moonlight.setCity(moonlightDTO.city());
                    if (LocationUtil.isValidLongitude(moonlightDTO.longitude())) moonlight.setLongitude(moonlightDTO.longitude());
                    if (LocationUtil.isValidLatitude(moonlightDTO.latitude())) moonlight.setLatitude(moonlightDTO.latitude());
                    if (moonlightDTO.startDate() != null) moonlight.setStartDate(moonlightDTO.startDate());
                    if (moonlightDTO.endDate() != null) moonlight.setEndDate(moonlightDTO.endDate());
                    return moonlightMapper.convertToDTO(moonlightRepository.save(moonlight));
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("Moonlight with ID of %d not found.", id)));
    }

    @Override
    @Transactional
    public void deleteMoonlight(Integer id) {
        moonlightRepository
                .findById(id)
                .map(moonlight -> {
                    moonlightRepository.deleteById(id);
                    return moonlight;
                })
                .orElseThrow(() -> new EntityNotFoundException(String.format("Moonlight with ID of %d not found.", id)));
    }

    private void notifyAvailableDoctors(MoonlightDTO moonlightDTO) {
        List<DoctorDTO> availableDoctors = doctorService.getAvailableDoctors();
        availableDoctors.stream()
                .filter(doctor -> LocationUtil.calculateDistance(
                        doctor.longitude(),
                        doctor.latitude(),
                        moonlightDTO.longitude(),
                        moonlightDTO.latitude()) <= doctor.preferredDistance())
                .forEach(doctor -> System.out.println("Notifying Doctor " + doctor.firstName() + " " + doctor.lastName()));
    }

    private static boolean validateMoonlight(MoonlightDTO moonlightDTO) {
        if (moonlightDTO.hospital() == null || moonlightDTO.hospital().equals(Strings.EMPTY)) return false;
        if (moonlightDTO.city() == null || moonlightDTO.city().equals(Strings.EMPTY)) return false;
        if (!LocationUtil.isValidLongitude(moonlightDTO.longitude())) return false;
        if (!LocationUtil.isValidLatitude(moonlightDTO.latitude())) return false;
        if (moonlightDTO.startDate() == null) return false;

        return moonlightDTO.endDate() != null;
    }
}
