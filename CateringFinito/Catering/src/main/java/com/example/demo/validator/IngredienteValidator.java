package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Ingrediente;

@Component
public class IngredienteValidator implements Validator{

	final Integer MAX_NAME_LENGTH = 100;
    final Integer MIN_NAME_LENGTH = 2;

	@Override
	public boolean supports(Class<?> pClass) {
		return Ingrediente.class.equals(pClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Ingrediente ingrediente = (Ingrediente) target;
        String nome = ingrediente.getNome().trim();
        String origine = ingrediente.getOrigine().trim();
        String descrizione = ingrediente.getDescrizione().trim();

        if (nome.isEmpty())
            errors.rejectValue("nome", "required");
        else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nome", "size");

        if (origine.isEmpty())
            errors.rejectValue("origine", "required");
        else if (origine.length() < MIN_NAME_LENGTH || origine.length() > MAX_NAME_LENGTH)
            errors.rejectValue("origine", "size");
        
        if (descrizione.isEmpty())
            errors.rejectValue("descrizione", "required");
        else if (descrizione.length() < MIN_NAME_LENGTH || descrizione.length() > MAX_NAME_LENGTH)
            errors.rejectValue("descrizione", "size");
	}
}
