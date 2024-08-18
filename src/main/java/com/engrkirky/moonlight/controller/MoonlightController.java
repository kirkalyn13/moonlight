package com.engrkirky.moonlight.controller;

import com.engrkirky.moonlight.dto.MoonlightDTO;
import com.engrkirky.moonlight.service.MoonlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/moonlight")
public class MoonlightController {
    private final MoonlightService moonlightService;

    @Autowired
    public MoonlightController(MoonlightService moonlightService) {
        this.moonlightService = moonlightService;
    }

    @GetMapping
    public ResponseEntity<Page<MoonlightDTO>> getMoonlights(
            @RequestParam("offset") Integer offset,
            @RequestParam("size") Integer size
    ) {
        Pageable pageable = PageRequest.of(offset, size);
        Page<MoonlightDTO> results = moonlightService.getMoonlights(pageable);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoonlightDTO> getMoonlightByID(@PathVariable("id") Integer id) {
        MoonlightDTO result = moonlightService.getMoonlightByID(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> addMoonlight(@RequestBody MoonlightDTO moonlightDTO) {
        Integer result = moonlightService.addMoonlight(moonlightDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoonlightDTO> editMoonlight(@PathVariable("id") Integer id, @RequestBody MoonlightDTO moonlightDTO) {
        MoonlightDTO result = moonlightService.editMoonlight(id, moonlightDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMoonlight(@PathVariable("id") Integer id) {
        moonlightService.deleteMoonlight(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
