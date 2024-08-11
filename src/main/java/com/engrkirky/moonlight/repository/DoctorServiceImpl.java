package com.engrkirky.moonlight.repository;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.services.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Override
    public List<DoctorDTO> getDoctors() {
        return null;
    }

    @Override
    public DoctorDTO getDoctorByID(Integer id) {
        return null;
    }

    @Override
    public Integer addDoctor(DoctorDTO doctorDTO) {
        return null;
    }

    @Override
    public DoctorDTO editDoctor(DoctorDTO doctorDTO) {
        return null;
    }

    @Override
    public void deleteDoctor(Integer id) {

    }
}
