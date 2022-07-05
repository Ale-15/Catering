package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Buffet;
import com.example.demo.model.Piatto;
import com.example.demo.repository.BuffetRepository;
import com.example.demo.repository.PiattoRepository;

@Service
public class PiattoService {

	@Autowired
	protected PiattoRepository pr;
	
	@Autowired
	protected BuffetRepository br;

	@Transactional
	public Piatto inserisci(Piatto piatto){
		return pr.save(piatto);
	}

	@Transactional
	public List<Piatto> tutti(){
		return (List<Piatto>) pr.findAll();
	}

	@Transactional
	public Piatto piattoPerId(Long id) {
		Optional<Piatto> optional = pr.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	@Transactional
	public boolean alreadyExists(Piatto piatto) {
		List<Piatto> piatti = this.pr.findByNome(piatto.getNome());
		if (piatti.size() > 0)
			return true;
		else 
			return false;
	}

	public void deletePiatto(Long id) {
		Optional<Piatto> optional = pr.findById(id);
		if (optional.isPresent())
			pr.deleteById(id);
	}

	@Transactional
	public List<Buffet> findAllByPiatti(Piatto piatto) {
		List<Buffet> buffets = new ArrayList<Buffet>();
        for(Buffet b : br.findAllByPiatti(piatto)) {
            buffets.add(b);
        }
        return buffets;
    }
}
