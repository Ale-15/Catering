package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Buffet;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.service.IngredienteService;
import com.example.demo.service.PiattoService;
import com.example.demo.validator.IngredienteValidator;

@Controller
public class IngredienteController {

	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired
	private IngredienteValidator ingredienteValidator;

	@Autowired
	private PiattoService piattoService;

	@GetMapping("/admin/ingredienteForm")
	public String addIngrediente(Model model){
		model.addAttribute("ingrediente", new Ingrediente());
		return "admin/ingredientiForm.html";
	}

	@PostMapping("/admin/ingrediente")
	public String addIngrediente(@ModelAttribute("ListaIngredienti") Ingrediente ingrediente, Model model, BindingResult BindingResult) {
    	this.ingredienteValidator.validate(ingrediente, BindingResult);
    	
        if (!BindingResult.hasErrors()) {
        	this.ingredienteService.inserisci(ingrediente);
            model.addAttribute("ListaIngredienti", this.ingredienteService.tutti());
            return "admin/Home.html";
        }
        return "admin/ingredientiForm.html";
    }

	@GetMapping("/ingrediente/{id}")
    public String getIngrediente(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("ingrediente", this.ingredienteService.ingredientePerId(id));
    	return "Ingrediente.html";
    }
	
	@GetMapping("/admin/ingrediente/{id}")
    public String getIngredienteAdmin(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("ingrediente", this.ingredienteService.ingredientePerId(id));
    	return "admin/Ingrediente.html";
    }
	
	@GetMapping("/admin/piattoIngrediente/{piattoId}/{ingredienteId}")
	public String inserisciIngrediente(@PathVariable("piattoId") Long piattoId, @PathVariable("ingredienteId") Long ingredienteId, Model model) {
		Piatto piatto = piattoService.piattoPerId(piattoId);
		Ingrediente ingrediente = ingredienteService.ingredientePerId(ingredienteId);

		piatto.getIngredienti().add(ingrediente);
		ingredienteService.inserisci(ingrediente);
		model.addAttribute("piatto", piatto);
		model.addAttribute("ListaIngredienti", this.ingredienteService.tutti());
		model.addAttribute("ingredienti", piatto.getIngredienti());
		return "admin/Piatto.html";
	}
	
	@GetMapping("/admin/cancellaIngrediente/{piattoId}/{ingredienteId}")
    public String deletePiatto(@PathVariable("piattoId") Long piattoId, @PathVariable("ingredienteId") Long ingredienteId, Model model) {
    	Piatto piatto = piattoService.piattoPerId(piattoId);
		Ingrediente ingrediente = ingredienteService.ingredientePerId(ingredienteId);

    	piatto.getIngredienti().remove(ingrediente);
    	model.addAttribute("piatto", piattoService.piattoPerId(piattoId));
		model.addAttribute("ingredienti", piattoService.piattoPerId(piattoId).getIngredienti());
		model.addAttribute("ListaIngredienti", ingredienteService.tutti());
        return "admin/Piatto.html";
    }
}
