package com.fede.DentalClinicMVC.repository;

import com.fede.DentalClinicMVC.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Long> {
}
