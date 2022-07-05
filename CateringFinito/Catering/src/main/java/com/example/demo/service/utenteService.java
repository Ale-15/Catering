package com.example.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Utente;
import com.example.demo.repository.utenteRepository;

public class utenteService {
	
	@Autowired
	private utenteRepository ur;

	@Transactional
	public void saveUtente(Utente utente) {
		ur.save(utente);
	}
	
//	public boolean alreadyExist(Utente utente) {
//		return ur.existsByNomeCognome(utente.getNome(), utente.getCognome());
//	}
}
