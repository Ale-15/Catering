package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Utente;

public interface utenteRepository extends CrudRepository<Utente,Long>{

//	boolean existsByNomeCognome(String nome, String cognome);
	
}
