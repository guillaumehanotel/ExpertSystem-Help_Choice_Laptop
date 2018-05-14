package com.ynov.b2.systemeexpert.beans;

import java.util.ArrayList;

public class BaseDeFaits {
	
	protected ArrayList<IFait> faits;

	public BaseDeFaits() {
		this.faits = new ArrayList<IFait>();
	}

	public ArrayList<IFait> getFaits() {
		return faits;
	}
	

	/**
	 * Vider la base de faits
	 */
	public void vider() {
		this.faits.clear();
	}
	
	/**
	 * Ajoute un fait à la base de faits
	 * @param fait
	 */
	public void addFait(IFait fait) {
		this.faits.add(fait);
	}
	
	/**
	 * Ajoute une liste de faits à la base de faits
	 * @param faits
	 */
	public void addFaits(ArrayList<IFait> faits) {
		this.faits.addAll(faits);
	}

	/**
	 * Retourne un fait en fonction du nom passé en paramètre
	 * @param name
	 * @return
	 */
	public IFait getFaitByNom(String name) {
		for(IFait fait : this.faits)
			if(fait.getNom().equals(name))
				return fait;
		return null;
	}
	
	
	/**
	 * Retourne la valeur d'un fait en fonction du nom passé en paramètre
	 * @param name
	 * @return
	 */
	public Object getValeurFaitByNom(String name) {
		IFait fait = this.getFaitByNom(name);
		return (fait != null) ? fait.getValeur() : null;
	}
	
	/**
	 * Retourne la taille de la base de faits
	 * @return
	 */
	public int getSize() {
		return this.faits.size();
	}
	
	/**
	 * Retourne vrai ou faux selon si le fait en paramètre est présent dans la base
	 * @param fait
	 * @return
	 */
	public boolean isBelongToBase(IFait fait) {
		return (this.getFaitByNom(fait.getNom()) != null) ? true : false;
	}
	
	/**
	 * Supprime le fait passé en paramètre de la base de faits
	 * @param fait
	 */
	public void removeFait(IFait fait) {
		this.faits.remove(fait);
	}
	
	
	
	

}
