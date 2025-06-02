package com.fede.DentalClinicMVC.controller;

import com.fede.DentalClinicMVC.dto.AppointmentDTO;
import com.fede.DentalClinicMVC.entity.Appointment;
import com.fede.DentalClinicMVC.exception.ResourceNotFoundException;
import com.fede.DentalClinicMVC.service.IAppointmentService;
import com.fede.DentalClinicMVC.service.IDentistService;
import com.fede.DentalClinicMVC.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class AppointmentController {

    private IAppointmentService appointmentService;
    private IDentistService dentistService;
    private IPatientService patientService;

    @Autowired
    public AppointmentController(IAppointmentService appointmentService, IDentistService dentistService, IPatientService patientService) {
        this.appointmentService = appointmentService;
        this.dentistService = dentistService;
        this.patientService = patientService;
    }




    //Endpoint consulta todos los turnos
    @GetMapping

    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> save(@RequestBody AppointmentDTO appointmentDTO){
        ResponseEntity<AppointmentDTO> response;

        //chequea la existencia del odontólogo y del paciente
        if(dentistService.findById(appointmentDTO.getDentist_id()).isPresent()
        && patientService.findById(appointmentDTO.getPatient_id()).isPresent()
        ){
            //setea el responseEntity como 200 y el turno va en el cuerpo de la respuesta
           response = ResponseEntity.ok(appointmentService.save(appointmentDTO));
        } else {

            //setea el responseEntity como 400
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable Long id) throws ResourceNotFoundException {
       Optional<AppointmentDTO> appointmentLooked = appointmentService.findById(id);

        if(appointmentLooked.isPresent()){
            return ResponseEntity.ok(appointmentLooked.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<AppointmentDTO> update (@RequestBody AppointmentDTO appointmentDTO) throws Exception {
        ResponseEntity<AppointmentDTO> response;

        if(dentistService.findById(appointmentDTO.getDentist_id()).isPresent()
        && patientService.findById(appointmentDTO.getPatient_id()).isPresent()) {

            //seteamos el responseEntity como 200 y le agregamos el turno como dto en el body
            response = ResponseEntity.ok(appointmentService.update(appointmentDTO));

        } else {
            response = ResponseEntity.badRequest().build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        appointmentService.delete(id);
        return ResponseEntity.ok("Se eliminó el turno con id: " + id);
    }


}
