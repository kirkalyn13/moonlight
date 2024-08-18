package com.engrkirky.moonlight.controller;

import com.engrkirky.moonlight.dto.DoctorDTO;
import com.engrkirky.moonlight.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<Page<DoctorDTO>> getDoctors(
            @RequestParam("offset") Integer offset,
            @RequestParam("size") Integer size,
            @RequestParam("search") String search) {
        Pageable pageable = PageRequest.of(offset, size);
        Page<DoctorDTO> results = doctorService.getDoctors(search, pageable);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable("id") Integer id) {
        DoctorDTO result = doctorService.getDoctorById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/available")
    public ResponseEntity<List<DoctorDTO>> getAvailableDoctors() {
        List<DoctorDTO> results = doctorService.getAvailableDoctors();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> addDoctor(@RequestBody DoctorDTO doctorDTO) {
        Integer result = doctorService.addDoctor(doctorDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable("id") Integer id, @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO result = doctorService.updateDoctor(id, doctorDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable("id") Integer id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
