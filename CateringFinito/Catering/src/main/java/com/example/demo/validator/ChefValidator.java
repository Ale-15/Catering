package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Chef;

@Component
public class ChefValidator implements Validator{

	final Integer MAX_NAME_LENGTH = 100;
    final Integer MIN_NAME_LENGTH = 2;

	@Override
	public boolean supports(Class<?> pClass) {
		return Chef.class.equals(pClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Chef chef = (Chef) target;
        String nome = chef.getNome().trim();
        String cognome = chef.getCognome().trim();
        String nazionalita = chef.getNazionalita().trim();

        if (nome.isEmpty())
            errors.rejectValue("nome", "required");
        else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nome", "size");

        if (cognome.isEmpty())
            errors.rejectValue("cognome", "required");
        else if (cognome.length() < MIN_NAME_LENGTH || cognome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("cognome", "size");
        
        if (nazionalita.isEmpty())
            errors.rejectValue("nazionalita", "required");
        else if (nazionalita.length() < MIN_NAME_LENGTH || nazionalita.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nazionalita", "size");
	}
}