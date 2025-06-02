package com.fede.DentalClinicMVC.repository;

import com.fede.DentalClinicMVC.entity.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDentistRepository extends JpaRepository<Dentist, Long> {

    //el @query puede no estar ya que el metodo tiene los nombres convencionales de spring data y lo hace automticamente

    @Query("SELECT d FROM Dentist d WHERE d.registration=?1")
    Optional<Dentist> findByRegistration(Integer registration);


}
