package com.fede.DentalClinicMVC.controller;


import com.fede.DentalClinicMVC.entity.Patient;
import com.fede.DentalClinicMVC.exception.ResourceNotFoundException;
import com.fede.DentalClinicMVC.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PatientController {

    private IPatientService patientService;

    @Autowired
    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> findAll(){
        return patientService.findAll();
    }


    //endpoint para agregar paciente
    @PostMapping
    public Patient save(@RequestBody Patient patient){
        return patientService.save(patient);
    }

    //endpoint para actualizar paciente
    @PutMapping
    public void update(@RequestBody Patient patient){
       patientService.update(patient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete (@PathVariable Long id) throws ResourceNotFoundException {
        patientService.delete(id);
        return ResponseEntity.ok("Se eliminó el paciente con id: " + id);
    }

}
