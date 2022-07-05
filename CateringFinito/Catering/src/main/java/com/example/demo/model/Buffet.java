package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Buffet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	private String descrizione;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public List<Piatto> getListaPiatti() {
		return piatti;
	}
	
	public Piatto getPiatto(Long id) {
		Piatto piatto = null;
		for(Piatto p : piatti) {
			if(p.getId() == id)
				piatto = p;
		}
		return piatto;
	}
	
	@ManyToOne(fetch = FetchType.EAGER) //cascade = {CascadeType.PERSIST})
	private Chef chef;
	
	@ManyToMany(fetch = FetchType.EAGER) //cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Piatto> piatti;
}
