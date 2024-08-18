package com.engrkirky.moonlight.repository;

import com.engrkirky.moonlight.model.Moonlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoonlightRepository extends JpaRepository<Moonlight, Integer> {
}
