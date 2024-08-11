package com.engrkirky.moonlight.controllers;

import com.engrkirky.moonlight.dto.DoctorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getDoctors() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorByID(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<Integer> addDoctor(DoctorDTO doctorDTO) {
        return null;
    }

    @PutMapping
    public ResponseEntity<DoctorDTO> editDoctor(DoctorDTO doctorDTO) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDoctor(Integer id) {
        return null;
    }
}
