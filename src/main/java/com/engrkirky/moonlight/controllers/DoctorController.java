package com.engrkirky.moonlight.controllers;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.services.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getDoctors() {
        List<DoctorDTO> results = doctorService.getDoctors();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable("id") Integer id) {
        DoctorDTO result = doctorService.getDoctorById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> addDoctor(DoctorDTO doctorDTO) {
        Integer result = doctorService.addDoctor(doctorDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> editDoctor(@PathVariable("id") Integer id, @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO result = doctorService.editDoctor(id, doctorDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable("id") Integer id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
