package com.fede.DentalClinicMVC.repository;

import com.fede.DentalClinicMVC.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {
}
