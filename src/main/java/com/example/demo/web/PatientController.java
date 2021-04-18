package com.example.demo.web;


import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.PatientRepository;
import com.example.demo.entities.Patient;

@Controller
public class PatientController {
    
	@Autowired
	private PatientRepository patientRepository;
	
	@GetMapping(path ="/patients")
	public String list(Model model, 
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5") int size,
			@RequestParam(name="keyword",defaultValue = "") String Keyword) {
		Page<Patient> pagePatients =  patientRepository.findByNameContains(Keyword, PageRequest.of(page, size));
		model.addAttribute("patients",pagePatients.getContent());
		model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword", Keyword);
		model.addAttribute("size", size);
		return "patients";
	}
	@GetMapping(path ="/deletePatient")
	public String delete(Long id,String keyword,int page,int size) {
		patientRepository.deleteById(id);
		return "redirect:/patients?page="+page+"&size="+size+"&keyword="+keyword;
	}
	@GetMapping(path ="/formPatient")
	public String formPatient(Model model) {
	    model.addAttribute("patient", new Patient());
		return "formPatient";
	}
	
	@PostMapping("/savePatient")
	public String savePatient(Model model,@Valid Patient patient,BindingResult bindinginResult) {
		if(bindinginResult.hasErrors()) return "formPatient";
		patientRepository.save(patient);
		model.addAttribute("patient", patient);
		return "confirmation";
		
	}
	@GetMapping(path ="/editPatient")
	public String editPatient(Model model,Long id) {
		Patient p=patientRepository.findById(id).get();
	    model.addAttribute("patient", p);
		return "formPatient";
	}

	
}
