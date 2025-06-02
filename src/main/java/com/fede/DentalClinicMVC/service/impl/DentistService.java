package com.fede.DentalClinicMVC.service.impl;

import com.fede.DentalClinicMVC.entity.Dentist;
import com.fede.DentalClinicMVC.exception.ResourceNotFoundException;
import com.fede.DentalClinicMVC.repository.IDentistRepository;
import com.fede.DentalClinicMVC.service.IDentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistService implements IDentistService {

    private IDentistRepository dentistRepository;

    @Autowired
    public DentistService(IDentistRepository dentistRepository) {
        this.dentistRepository = dentistRepository;
    }

    @Override
    public Dentist save(Dentist dentist) {
        return dentistRepository.save(dentist);
    }

    @Override
    public Optional<Dentist> findById(Long id) {
        return dentistRepository.findById(id);
    }

    @Override
    public void update(Dentist dentist) {
        dentistRepository.save(dentist);

    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {

        Optional<Dentist> dentistToLookFor = findById(id);
        if (dentistToLookFor.isPresent()) {
            dentistRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("El odontologo con id " + id + " no existe.");
        }



    }

    @Override
    public List<Dentist> findAll() {
        return dentistRepository.findAll();
    }

    @Override
    public Optional<Dentist> findByRegistration(Integer registration) {
        return dentistRepository.findByRegistration(registration);
    }
}





