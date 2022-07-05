package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Buffet;
import com.example.demo.repository.BuffetRepository;

@Service
public class BuffetService {

	@Autowired
	protected BuffetRepository br;
	
	@Transactional
	public Buffet inserisci(Buffet buffet){
		return br.save(buffet);
	}

	@Transactional
	public List<Buffet> tutti(){
		return (List<Buffet>) br.findAll();
	}
	
	@Transactional
	public Buffet buffetPerId(Long id) {
		Optional<Buffet> optional = br.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Buffet buffet) {
		List<Buffet> buffets = this.br.findByNome(buffet.getNome());
		if (buffets.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void deleteBuffet(Long id) {
		Optional<Buffet> optional = br.findById(id);
		if (optional.isPresent())
			br.deleteById(id);
	}
}