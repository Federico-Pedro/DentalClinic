package com.fede.DentalClinicMVC.service.impl;


import com.fede.DentalClinicMVC.entity.Dentist;
import com.fede.DentalClinicMVC.entity.Patient;
import com.fede.DentalClinicMVC.exception.ResourceNotFoundException;
import com.fede.DentalClinicMVC.repository.IPatientRepository;
import com.fede.DentalClinicMVC.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService implements IPatientService {

    private IPatientRepository patientRepository;

    @Autowired
    public PatientService(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public void update(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Patient> patientToLookFor = findById(id);
        if (patientToLookFor.isPresent()) {
            patientRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("El paciente con id " + id + " no existe.");
        }

    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }
}
