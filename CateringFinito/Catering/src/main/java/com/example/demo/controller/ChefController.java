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
import com.example.demo.service.BuffetService;
import com.example.demo.service.ChefService;
import com.example.demo.service.PiattoService;
import com.example.demo.validator.ChefValidator;

@Controller
public class ChefController {

	@Autowired
	private ChefValidator chefValidator;

	@Autowired
	private ChefService chefService;

	@Autowired
	private BuffetService buffetService;

	@Autowired
	private PiattoService piattoService;

	@GetMapping("/admin/chefForm")
	public String addChef(Model model){
		model.addAttribute("chef", new Chef());
		return "admin/chefForm.html";
	}
	
	@GetMapping("/admin/listaChef")
	public String getChefAdmin(Model model) {
		model.addAttribute("ListaChef", this.chefService.tutti());
		return "admin/ListaChef.html";
	}
	
	@GetMapping("/user/listaChef")
	public String getChefUser(Model model) {
		model.addAttribute("ListaChef", this.chefService.tutti());
		return "ListaChef.html";
	}
	
	@PostMapping("/admin/chef")
	public String addChef(@ModelAttribute("ListaChef") Chef chef, Model model, BindingResult BindingResult) {
    	this.chefValidator.validate(chef, BindingResult);
    	
        if (!BindingResult.hasErrors()) {
        	this.chefService.inserisci(chef);
            model.addAttribute("ListaChef", this.chefService.tutti());
            return "admin/ListaChef.html";
        }
        return "admin/chefForm.html";
    }
	
	@GetMapping("/chef/{id}")
    public String getChef(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("chef", this.chefService.chefPerId(id));
    	return "Chef.html";
    }
	
	@GetMapping("/admin/chef/{id}")
    public String getChefAdmin(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("chef", this.chefService.chefPerId(id));
    	return "admin/Chef.html";
    }
	
	@GetMapping("/admin/buffetChef/{buffetId}/{chefId}")
	public String impostaChef(@PathVariable("buffetId") Long buffetId, @PathVariable("chefId") Long chefId, Model model) {
		Buffet buffet = buffetService.buffetPerId(buffetId);
		Chef chef = chefService.chefPerId(chefId);
		
		buffet.setChef(chef);
		chefService.inserisci(chef);
		model.addAttribute("buffet", buffet);
		model.addAttribute("ListaChef", this.chefService.tutti());
		model.addAttribute("piatti", buffet.getListaPiatti());
		model.addAttribute("ListaPiatti", this.piattoService.tutti());
		return "admin/Buffet.html";
	}
	
	@GetMapping("/admin/cancellaChef/{id}")
		public String cancellaChef(@PathVariable("id") Long id, Model model) {
			Chef chef = chefService.chefPerId(id);
			List<Buffet> buffet = chefService.findAllByChef(chef);
			
			for(Buffet b : buffet){
				b.setChef(null);
			}

			chefService.deleteChef(id);
			model.addAttribute("ListaChef", this.chefService.tutti());
			
			return "admin/ListaChef.html";
	}
}