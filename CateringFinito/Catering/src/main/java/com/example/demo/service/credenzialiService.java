package com.example.demo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Credenziali;
import com.example.demo.repository.credenzialiRepository;

@Service
public class credenzialiService {

	@Autowired
	protected credenzialiRepository cr;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Transactional
	public Credenziali saveCredenziali(Credenziali credenziali) {
		credenziali.setRuolo(Credenziali.ruoloAdmin);
		credenziali.setPassword(this.passwordEncoder.encode(credenziali.getPassword()));
		return this.cr.save(credenziali);
	}
	
	@Transactional
    public Credenziali getCredenziali(Long id) {
        Optional<Credenziali> result = this.cr.findById(id);//optional->Contenitore di valori
        return result.orElse(null);
    }

    @Transactional
    public Credenziali getCredenziali(String username) {
        Optional<Credenziali> result = this.cr.findByUsername(username);
        return result.orElse(null);
    }
//
//	/* interrogazione non transazionale*/
//	public List<Utente> findAll(){
//		List<Utente> utenti = new ArrayList<Utente>();
//		for(Utente u: ur.findAll()) {	//findAll mi ritorna un'iteratore, quindi itero sull'iteratore
//			utenti.add(u);
//		}
//		return utenti;
//	}

	//per verificare i duplicati in persona Validator
	//ho bisogno di un metodo in repository
//	public boolean alreadyExist(Credenziali credenziali) {
//		return cr.existsByUsername(credenziali.getUsername());
//	}

	/* Utilizzando questa annotazione ci pensa Sping boot ad aprire e chiudere la transazione*/
//	@Transactional
//	public void deleteById(Long id) {
//		ur.deleteById(id);
//	}
}