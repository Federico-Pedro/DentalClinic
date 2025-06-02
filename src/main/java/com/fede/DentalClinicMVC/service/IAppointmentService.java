package com.fede.DentalClinicMVC.service;

import com.fede.DentalClinicMVC.dto.AppointmentDTO;
import com.fede.DentalClinicMVC.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IAppointmentService {

    AppointmentDTO save(AppointmentDTO appointmentDTO);
    Optional<AppointmentDTO> findById(Long id) throws ResourceNotFoundException;
    AppointmentDTO update(AppointmentDTO appointmentDTO) throws Exception;
    Optional<AppointmentDTO> delete(Long id) throws ResourceNotFoundException;
    List<AppointmentDTO> findAll();
}
