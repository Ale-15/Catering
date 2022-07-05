package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.model.Piatto;
import com.example.demo.repository.BuffetRepository;
import com.example.demo.repository.ChefRepository;

@Service
public class ChefService {

	@Autowired
	protected ChefRepository cr;
	
	@Autowired
	protected BuffetRepository br;
	
	@Transactional
	public Chef inserisci(Chef chef){
		return cr.save(chef);
	}

	@Transactional
	public List<Chef> tutti(){
		return (List<Chef>) cr.findAll();
	}

	@Transactional
	public Chef chefPerId(Long id) {
		Optional<Chef> optional = cr.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	public void deleteChef(Long id) {
		Optional<Chef> optional = cr.findById(id);
		if (optional.isPresent())
			cr.deleteById(id);
	}

	@Transactional
    public List<Buffet> findAllByChef(Chef chef) {
        List<Buffet> buffets = new ArrayList<Buffet>();
        for(Buffet b : br.findAllByChef(chef)) {
            buffets.add(b);
        }
        return buffets;
    }
}