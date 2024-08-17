package com.engrkirky.moonlight.repository;

import com.engrkirky.moonlight.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByIsAvailableTrue();
}
