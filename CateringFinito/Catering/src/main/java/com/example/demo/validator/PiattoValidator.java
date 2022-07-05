package com.example.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.Piatto;
import com.example.demo.service.PiattoService;

@Component
public class PiattoValidator implements Validator{

	@Autowired
	private PiattoService piattoService;

    private static final Logger logger = LoggerFactory.getLogger(PiattoValidator.class);

	@Override
	public boolean supports(Class<?> pClass) {
		return Piatto.class.equals(pClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descrizione", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.piattoService.alreadyExists((Piatto)target)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

}
