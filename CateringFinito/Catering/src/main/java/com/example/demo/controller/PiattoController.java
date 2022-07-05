package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.model.Piatto;
import com.example.demo.service.BuffetService;
import com.example.demo.service.ChefService;
import com.example.demo.service.IngredienteService;
import com.example.demo.service.PiattoService;
import com.example.demo.validator.PiattoValidator;

@Controller
public class PiattoController {

	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private PiattoValidator piattoValidator;

	@Autowired
	private BuffetService buffetService;

	@Autowired
	private ChefService chefService;

	@Autowired
	private IngredienteService ingredienteService;

	@GetMapping("/admin/piattoForm")
	public String addPiatto(Model model){
		model.addAttribute("piatto", new Piatto());
		return "admin/piattoForm.html";
	}
	
	@GetMapping("/admin/listaPiatti")
	public String getPiattiAdmin(Model model) {
		model.addAttribute("ListaPiatti", this.piattoService.tutti());
		return "admin/ListaPiatti.html";
	}
	
	@PostMapping("/admin/piatto")
	public String addPiatto(@ModelAttribute("ListaPiatti") Piatto piatto, Model model, BindingResult BindingResult) {
    	this.piattoValidator.validate(piatto, BindingResult);
    	
        if (!BindingResult.hasErrors()) {
        	this.piattoService.inserisci(piatto);
            model.addAttribute("ListaPiatti", this.piattoService.tutti());
            return "admin/ListaPiatti.html";
        }
        return "admin/piattoForm.html";
    }
	
	@GetMapping("/piatto/{id}")
    public String getPiatto(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("piatto", this.piattoService.piattoPerId(id));
    	model.addAttribute("ingredienti", this.piattoService.piattoPerId(id).getIngredienti());
    	return "Piatto.html";
    }
	
	@GetMapping("/admin/piatto/{id}")
    public String getPiattoAdmin(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("piatto", this.piattoService.piattoPerId(id));
     	model.addAttribute("ingredienti", this.piattoService.piattoPerId(id).getIngredienti());
    	model.addAttribute("ListaIngredienti", this.ingredienteService.tutti());
    	return "admin/Piatto.html";
    }
	
	@GetMapping("/admin/buffetPiatto/{buffetId}/{piattoId}")
	public String impostaPiatto(@PathVariable("buffetId") Long buffetId, @PathVariable("piattoId") Long piattoId, Model model) {
		Buffet buffet = buffetService.buffetPerId(buffetId);
		Piatto piatto = piattoService.piattoPerId(piattoId);

		buffet.getListaPiatti().add(piatto);
		piattoService.inserisci(piatto);
		model.addAttribute("buffet", buffet);
		model.addAttribute("ListaPiatti", this.piattoService.tutti());
		model.addAttribute("ListaChef", this.chefService.tutti());
		model.addAttribute("piatti", buffet.getListaPiatti());
		return "admin/Buffet.html";
	}
	
	@GetMapping("/admin/cancellaPiatto/{buffetId}/{piattoId}")
    public String deletePiatto(@PathVariable("buffetId") Long buffetId, @PathVariable("piattoId") Long piattoId, Model model) {
    	Buffet buffet = buffetService.buffetPerId(buffetId);
    	Piatto piatto = piattoService.piattoPerId(piattoId);

    	buffet.getListaPiatti().remove(piatto);
    	model.addAttribute("buffet", buffetService.buffetPerId(buffetId));
    	model.addAttribute("ListaPiatti", this.piattoService.tutti());
		model.addAttribute("ListaChef", this.chefService.tutti());
		model.addAttribute("piatti", buffetService.buffetPerId(buffetId).getListaPiatti());
        return "admin/Buffet.html";
    }
	
	@GetMapping("/admin/eliminaPiatto/{piattoId}")
	public String cancellaPiatto(@PathVariable("piattoId") Long piattoId, Model model) {
		Piatto piatto = piattoService.piattoPerId(piattoId);
		
		
		List<Buffet> buffet = piattoService.findAllByPiatti(piatto);
		
		for(Buffet b : buffet){
			b.getListaPiatti().remove(piatto);
		}

		piattoService.deletePiatto(piattoId);
		model.addAttribute("ListaPiatti", this.piattoService.tutti());
		
		return "admin/ListaPiatti.html";
	}
}
