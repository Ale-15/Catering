package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

//import static org.assertj.core.api.Assertions.as;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Credenziali;
import com.example.demo.model.Utente;
import com.example.demo.service.credenzialiService;

@Controller
public class buttonController {

	@Autowired
	private credenzialiService credenzialiService;
	//get operazioni di lettura, post operazioni di scrittura

	//Mi manda al form del button
	@GetMapping("/buttonLogin")
	public String getLogin(Model model) {
		return "Home.html";
	}
	
	@GetMapping("/index")
	public String getIndex(Model model) {
		return "index.html";
	}

	@GetMapping("/default")
	public String defaultAfterLogin(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
		if (credenziali.getRuolo().equals(Credenziali.ruoloAdmin)) {
			return "admin/Home.html";
		}
		return "Home.html";
	}

	@GetMapping("/buttonRegistrazione")
	public String getRegistrazione(Model model) {
		model.addAttribute("Utente", new Utente());
		model.addAttribute("Credenziali", new Credenziali());
		return "RegistrazioneForm.html";
	}
}
