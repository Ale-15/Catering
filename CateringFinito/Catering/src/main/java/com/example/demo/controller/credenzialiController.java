package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Credenziali;
import com.example.demo.model.Utente;
import com.example.demo.service.credenzialiService;
import com.example.demo.validator.credenzialiValidator;
import com.example.demo.validator.utenteValidator;

@Controller
public class credenzialiController {
	
	@Autowired
	private credenzialiService credenzialiService;
	
	@Autowired
	private utenteValidator utenteValidator;
	
	@Autowired
	private credenzialiValidator credenzialiValidator;

	//get operazioni di lettura, post operazioni di scrittura
	
	//Inserisco i dati di un utente nel sistema, operazione post
	@PostMapping("/register")
	public String addUtente(@ModelAttribute("Utente")Utente utente, @ModelAttribute("Credenziali")Credenziali credenziali ,BindingResult userBindingResult, BindingResult credenzialiBindingResult, Model model) {
		utenteValidator.validate(utente, userBindingResult);// per il controllo dei duplicati---bindingResult ha tutti gli errori(è il parametro error)
		credenzialiValidator.validate(credenziali, credenzialiBindingResult);
		
		if(!userBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors()) {	//Se la validazione non ha avuto errori
			credenziali.setUtente(utente);
			credenzialiService.saveCredenziali(credenziali); 	//Invoco sul service il metodo save per salvare la persona
			
			//model.addAttribute("Credenziali", credenziali);	//Lo aggiungo al modello
			return "RegistrazioneEffettuata.html";	//Va sulla home del sito una volta registrato
		}
		return "RegistrazioneForm.html";
	}
	
	@GetMapping("/logout")
	public String logout(Model model) {
		return "index.html";
	}

	@GetMapping("/failure")
	public String failure(Model model) {
		return "failure.html";
	}
	//Richiede tutti gli amministratori perché non specifico l'id
//	@GetMapping("/Utente")
//	public String getUtenti(Model model) {
//		List<Utente> utenti = us.findAll();
//		model.addAttribute("utenti", utenti);
//		return "utenti.html";
//	}
	
	//Lo metto tra parentesi per digli che non è la string id ma è un parametro
//	@GetMapping("/Utente/{id}")	//Per dirgli che questo id viene dal pattern
//	public String getUtente(@PathVariable("id")Long id, Model model) {	//Questo metodo per dialogare con le liste gli serve sempre il parametro model
//		Utente utente = utenteService.findById(id);
//		model.addAttribute("utente", utente);			//Nelle liste quando vogliamo recuperare questo oggetto lo chiameremo persona
//		return "Utente.html";	//Pagina successiva che mi mostra i dati dell'amministratore
//	}
}