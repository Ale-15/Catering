package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Utente;

/* CLASSE USATA PER IL CONTROLLO SULL'INSERIMENTO DI DUPLICATI*/

@Component	//Lo annoto con component così lo posso usare con autowired nel controller
public class utenteValidator implements Validator{

//	@Autowired
//	private utenteService utenteService;

	final Integer MAX_NAME_LENGTH = 100;
    final Integer MIN_NAME_LENGTH = 4;

	@Override
	public boolean supports(Class<?> pClass) {
		return Utente.class.equals(pClass);
	}

	//DA CONTROLLARE
	@Override
	public void validate(Object target, Errors errors) {	//Con errors rigettiamo la validazione con un codice di errore
		Utente utente = (Utente) target;
        String nome = utente.getNome().trim();
        String cognome = utente.getCognome().trim();

        if (nome.isEmpty())
            errors.rejectValue("nome", "required");
        else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nome", "size");

        if (cognome.isEmpty())
            errors.rejectValue("cognome", "required");
        else if (cognome.length() < MIN_NAME_LENGTH || cognome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("cognome", "size");
	}
}
