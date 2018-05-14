package com.ynov.b2.systemeexpert.beans;

public class FaitEntier implements IFait {

	protected String nom;
	protected int valeur;
	protected int niveau;
	protected String question;
	
	
	
	
	public FaitEntier(String nom, int valeur, int niveau, String question) {
		this.nom = nom;
		this.valeur = valeur;
		this.niveau = niveau;
		this.question = question;
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public Object getValeur() {
		return this.valeur;
	}

	@Override
	public int getNiveau() {
		return this.niveau;
	}

	@Override
	public String getQuestion() {
		return this.question;
	}

	@Override
	public void setNiveau(int i) {
		this.niveau =i;
	}

	@Override
	public String toString() {
		return nom + "=" + valeur + "(" + niveau + ")";
	}
	
	
	
	

}
