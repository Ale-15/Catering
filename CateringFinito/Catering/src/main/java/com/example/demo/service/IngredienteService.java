package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Ingrediente;
import com.example.demo.repository.IngredienteRepository;

@Service
public class IngredienteService {

	@Autowired
	protected IngredienteRepository ir;
	
	@Transactional
	public Ingrediente inserisci(Ingrediente ingrediente){
		return ir.save(ingrediente);
	}

	@Transactional
	public List<Ingrediente> tutti(){
		return (List<Ingrediente>) ir.findAll();
	}

	@Transactional
	public Ingrediente ingredientePerId(Long id) {
		Optional<Ingrediente> optional = ir.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
}
