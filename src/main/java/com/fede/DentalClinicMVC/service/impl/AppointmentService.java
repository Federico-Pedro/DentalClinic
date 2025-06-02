package com.fede.DentalClinicMVC.service.impl;


import com.fede.DentalClinicMVC.dto.AppointmentDTO;
import com.fede.DentalClinicMVC.entity.Appointment;
import com.fede.DentalClinicMVC.entity.Dentist;
import com.fede.DentalClinicMVC.entity.Patient;
import com.fede.DentalClinicMVC.exception.ResourceNotFoundException;
import com.fede.DentalClinicMVC.repository.IAppointmentRepository;
import com.fede.DentalClinicMVC.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class AppointmentService implements IAppointmentService {

    private IAppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(IAppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public AppointmentDTO save(AppointmentDTO appointmentDTO) {
        //mapear nuestras entidades como DTO manualmente
        //instancia una entidad de turno
        Appointment appointmentEntity = new Appointment();

        //instanciar paciente
        Patient patientEntity = new Patient();
        patientEntity.setId(appointmentDTO.getPatient_id());

        //instanciar odontologo
        Dentist dentistEntity = new Dentist();
        dentistEntity.setId(appointmentDTO.getDentist_id());

        //seteamos el paciente y el odonto a nuetsra entidad turno

        appointmentEntity.setDentist(dentistEntity);
        appointmentEntity.setPatient(patientEntity);

        //convertir el string del DTO que es la fecha a localDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(appointmentDTO.getDate(), formatter);

        //setear la fecha
        appointmentEntity.setDate(date);

        //persistirla en la BD
        appointmentRepository.save(appointmentEntity);

        //vamos a trabajar con el DTO que debemos devolver
        //generar una instancia del turno DTO
        AppointmentDTO appointmentDTOToReturn = new AppointmentDTO();

        //le seteamos los datos de la entidad que persisitimos
        appointmentDTOToReturn.setId(appointmentEntity.getId());
        appointmentDTOToReturn.setDate(appointmentEntity.getDate().toString());
        appointmentDTOToReturn.setDentist_id(appointmentEntity.getDentist().getId());
        appointmentDTOToReturn.setPatient_id(appointmentEntity.getPatient().getId());

        return appointmentDTOToReturn;
    }

    @Override
    public Optional<AppointmentDTO> findById(Long id) throws ResourceNotFoundException {
        //busca la entidad por id a la DB
        Optional<Appointment> appointmentToLookFor = appointmentRepository.findById(id);
        Optional<AppointmentDTO> appointmentDTO = null;

        if (appointmentToLookFor.isPresent()) {
            //recuperar la entidad que se encontró y guardarla

            Appointment appointment = appointmentToLookFor.get();
            //trabajar sobre la info que queiro devolver
            //crear instancia de turnoDTO para devolver
            AppointmentDTO appointmentDTOToReturn = new AppointmentDTO();
            appointmentDTOToReturn.setId(appointment.getId());
            appointmentDTOToReturn.setPatient_id(appointment.getId());
            appointmentDTOToReturn.setDentist_id(appointment.getId());
            appointmentDTOToReturn.setDate(appointment.getDate().toString());

            //Convierto el appointmentDTO a un optional
            appointmentDTO =  Optional.of(appointmentDTOToReturn);
            return appointmentDTO;
        }else {

            throw new ResourceNotFoundException("No se encontró el turno con id: " + id);
        }

    }


    @Override
    public AppointmentDTO update(AppointmentDTO appointmentDTO) throws Exception {

        //chequeo que el turno a a ctualizar existe
        if(appointmentRepository.findById(appointmentDTO.getId()).isPresent()) {
            //buscar la entidad en la BD
            Optional <Appointment> appointmentEntity = appointmentRepository.findById(appointmentDTO.getId());

            //instanciar paciente
            Patient patientEntity = new Patient();
            patientEntity.setId(appointmentDTO.getPatient_id());

            //instanciar odontologo
            Dentist dentistEntity = new Dentist();
            dentistEntity.setId(appointmentDTO.getDentist_id());

            //seteamos el paciente y el odonto a nuetsra entidad turno
            appointmentEntity.get().setPatient(patientEntity);
            appointmentEntity.get().setDentist(dentistEntity);

            //convertir el string del DTO que es la fecha a localDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(appointmentDTO.getDate(), formatter);

            //setear la fecha
            appointmentEntity.get().setDate(date);

            //persistirla en la BD
            appointmentRepository.save(appointmentEntity.get());

            //respuesta dto a devolver
            AppointmentDTO appointmentDTOToReturn = new AppointmentDTO();
            appointmentDTOToReturn.setId(appointmentEntity.get().getId());
            appointmentDTOToReturn.setPatient_id(appointmentEntity.get().getId());
            appointmentDTOToReturn.setDentist_id(appointmentEntity.get().getId());
            appointmentDTOToReturn.setDate(appointmentEntity.get().getDate().toString());

            return appointmentDTOToReturn;
        } else {
            throw new Exception("No se pudo actualizar el turno");
        }


    }



    @Override
    public Optional<AppointmentDTO> delete(Long id) throws ResourceNotFoundException {
        //busca la entidad por id en la DB y la guarda en un optional
        Optional<Appointment> appointmentToLookFor = appointmentRepository.findById(id);

        Optional<AppointmentDTO> appointmentDTO;

        if(appointmentToLookFor.isPresent()) {
            //recuperar el turno y guardarlo en una variable de tipo turno
            Appointment appointment = appointmentToLookFor.get();

            //devolvemos un dto
            //trabajamos sobre ese dto a devolver
            //crear una instancia del dto
            AppointmentDTO appointmentDTOToReturn = new AppointmentDTO();
            appointmentDTOToReturn.setId(appointment.getId());
            appointmentDTOToReturn.setDentist_id(appointment.getDentist().getId());
            appointmentDTOToReturn.setPatient_id(appointment.getPatient().getId());
            appointmentDTOToReturn.setDate(appointment.getDate().toString());

            //convertimos el elemento a un optional
            appointmentDTO = Optional.of(appointmentDTOToReturn);
            return appointmentDTO;

        } else {
            throw new ResourceNotFoundException("No se encontró el turno con id: " + id);
        }
    }





    @Override
    public List<AppointmentDTO> findAll() {
        //vamos a traer las entidades de la DB y guardarlas en una lista
        List<Appointment> appointments = appointmentRepository.findAll();

        //crear lista vacía de turnos DTO
        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();

        //recorremos la lista de entidades de turnos para luego guardar algunos parametros en la listaDTO
        for (Appointment appointment : appointments) {
            appointmentDTOS.add(new AppointmentDTO(appointment.getId(),
                    appointment.getPatient().getId(), appointment.getDentist().getId(),
                    appointment.getDate().toString()));
        }


        return appointmentDTOS;
    }
}
