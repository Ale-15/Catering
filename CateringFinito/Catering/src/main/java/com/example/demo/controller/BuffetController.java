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
import com.example.demo.service.BuffetService;
import com.example.demo.service.ChefService;
import com.example.demo.service.PiattoService;
import com.example.demo.validator.BuffetValidator;

@Controller
public class BuffetController {
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
    private BuffetValidator buffetValidator;

	@Autowired
	private ChefService chefService;

	@Autowired
	private PiattoService piattoService;

	@GetMapping("/listaBuffet")
	public String getBuffet(Model model) {
		model.addAttribute("ListaBuffet", this.buffetService.tutti());
		return "ListaBuffet.html";
	}
	
	@GetMapping("/admin/listaBuffet")
	public String getBuffetAdmin(Model model) {
		model.addAttribute("ListaBuffet", this.buffetService.tutti());
		return "admin/ListaBuffet.html";
	}
	
	@GetMapping("/user/listaBuffet")
	public String getBuffetUser(Model model) {
		model.addAttribute("ListaBuffet", this.buffetService.tutti());
		return "ListaBuffet.html";
	}
	
	@GetMapping("/admin/buffet/{id}")
    public String getBuffetAdmin(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("buffet", this.buffetService.buffetPerId(id));
        model.addAttribute("ListaChef", this.chefService.tutti());
        model.addAttribute("ListaPiatti", this.piattoService.tutti());
        model.addAttribute("piatti", this.buffetService.buffetPerId(id).getListaPiatti());
    	return "admin/Buffet.html";
    }
	
	@GetMapping("/buffet/{id}")
    public String getBuffetUser(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("buffet", this.buffetService.buffetPerId(id));
        model.addAttribute("piatti", this.buffetService.buffetPerId(id).getListaPiatti());
    	return "Buffet.html";
    }
	
	@GetMapping("/admin/buffetForm")
	public String addBuffet(Model model){
		model.addAttribute("buffet", new Buffet());
		return "admin/buffetForm.html";
	}

    @PostMapping("/admin/buffet")
    public String addProdotto(@ModelAttribute("ListaBuffet") Buffet buffet, Model model, BindingResult BindingResult) {
    	this.buffetValidator.validate(buffet, BindingResult);
    	
        if (!BindingResult.hasErrors()) {
        	this.buffetService.inserisci(buffet);
            model.addAttribute("ListaBuffet", this.buffetService.tutti());
            return "admin/ListaBuffet.html";
        }
        return "admin/buffetForm.html";
    }

    @GetMapping("/admin/cancellaBuffet/{id}")
    public String deleteBuffet(@PathVariable("id") Long id, Model model) {
    	this.buffetService.deleteBuffet(id);
    	model.addAttribute("ListaBuffet", this.buffetService.tutti());
        return "admin/ListaBuffet.html";	//Fare un html per dire Eliminazione effettuata, e poi mandarlo al form della lista
    }
    
    @GetMapping("/admin/tornaHome")
    public String getHomeAdmin(Model model) {
    	return "admin/Home.html";
    }
    
    @GetMapping("/tornaHome")
    public String getHome(Model model) {
    	return "Home.html";
    }
}