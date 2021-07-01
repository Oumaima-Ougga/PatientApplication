package com.example.demo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.example.demo.dao.PatientRepository;
import com.example.demo.entities.Patient;


@SpringBootApplication
public class ProjetPatientApplication implements CommandLineRunner{
	
    @Autowired
	private PatientRepository patientRepository;
	
	public static void main(String[] args) {
	SpringApplication.run(ProjetPatientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		patientRepository.save(new Patient(null, "Mohamed ", new Date(),20, false));
		patientRepository.save(new Patient(null, "Oumaima", new Date(),12, false));
		patientRepository.save(new Patient(null, "Fati",new Date(), 12, false));
		
		patientRepository.findAll().forEach(p->{
		     System.out.println(p.getName());
		     });
	}

}
