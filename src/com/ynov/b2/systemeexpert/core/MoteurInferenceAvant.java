package com.ynov.b2.systemeexpert.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.ynov.b2.systemeexpert.beans.BaseDeFaits;
import com.ynov.b2.systemeexpert.beans.BaseDeRegles;
import com.ynov.b2.systemeexpert.beans.IFait;
import com.ynov.b2.systemeexpert.beans.Regle;
import com.ynov.b2.systemeexpert.ui.IHM;
import com.ynov.b2.systemeexpert.utils.FaitFactory;

public class MoteurInferenceAvant {
	
	
	private BaseDeFaits baseDeFaits;
	private BaseDeRegles baseDeRegles;
	private IHM ihm;

	
	private int niveauMaxRegle;
	
	
	public MoteurInferenceAvant(IHM ihm) {
		this.ihm = ihm;
		this.baseDeFaits = new BaseDeFaits();
		this.baseDeRegles = new BaseDeRegles();
	}
	
	
	public int DemanderValeurEntiere(String question) {
		return ihm.demanderValeurEntiere(question);
	}
	
	public boolean DemanderValeurBooleenne(String question) {
		return ihm.demanderValeurBooleenne(question);
	}
	
	
	/*
	 * Parcourt les prémisses(faits) d'une règle, 2 cas possibles :
	 * 
	 * - Le fait n'est pas présent dans la base de faits :
	 * 		- soit il possède une question, et dans ce cas il faut demander la valeur de l'utilisateur et l'ajouter dans la base de faits
	 * 		- soit il ne possède pas de question (donc fait inférable) et la règle ne pourra pas s'appliquer
	 * 
	 * - Le fait est présent est présent dans la base de faits :
	 * 		- soit la valeur correspond, et dans ce cas, on passe au fait suivant 
	 * 		- soit la valeur ne correspond pas, et alors la règle ne s'appliquera pas
	 */
	int estApplicable(Regle r) {
		int niveauMax = -1;
		
		// On vérifie la véracité de chaque prémisse 
		for(IFait f : r.getPremisses()) {
			// on cherche le fait dans la base de faits avec son nom
			IFait faitTrouve = baseDeFaits.getFaitByNom(f.getNom());
			if(faitTrouve == null) {
				// Ce fait n'est pas dans la base de faits
				if(f.getQuestion() != null) {
					// On le demande et on l'ajoute
					// on passe le fait de la présisse en paramètre pour récupérer sa classe et savoir 
					// si on doit créer un fait booléen ou un fait entier
					
					faitTrouve = FaitFactory.Fait(f,this);
					this.baseDeFaits.addFait(faitTrouve);
					
				} else {
					// La règle ne peut pas s'appliquer
					return -1;
				}
			}
			
			// Le fait existe dans la base (avant ou créé),
			// mais avec la bonne valeur ?
			if(!faitTrouve.getValeur().equals(f.getValeur())) {
				// valeur ne correspond pas
				return -1;
			} else {
				// ça correspond, on met à jour le niveau
				niveauMax = Math.max(niveauMax, faitTrouve.getNiveau());
			}
		}
		return niveauMax;
		
	}
	
	/**
	 * Parcourt de toutes les règles de la base de règles 
	 * et retourne la première règle applicable trouvé
	 * si aucune n'est applicable : return null
	 * @param baseDeRegles
	 * @return
	 */
	Regle trouverRegle(BaseDeRegles baseDeRegles) {
		for(Regle r : baseDeRegles.getRegles()) {
			int niveau = estApplicable(r);
			if(niveau != -1) {
				this.niveauMaxRegle = niveau;
				return r;
			}
		}
		return null;
	}
	
	
	public List<IFait> Resoudre() {
		// On copie toutes les règles dans une base de règle locale
		BaseDeRegles baseDeRegle_copie = new BaseDeRegles();
		baseDeRegle_copie.setRegles(this.baseDeRegles.getRegles());
		
		// on vide la base faits
		this.baseDeFaits.vider();
		
		// Tant qu'il existe des règles à appliquer
		Regle r = trouverRegle(baseDeRegle_copie);
		while(r != null) {
			// Appliquer la règle 
			// on ajoute la conclusion de la règle applicable en base fait
			IFait nouveauFait = r.conclusion;
			nouveauFait.setNiveau(niveauMaxRegle+1);
			this.baseDeFaits.addFait(nouveauFait);
			// on enlève la règle vérifié de la base de règle locale
			baseDeRegle_copie.effacerRegle(r);
			// on cherche la prochaine règle applicable
			r = trouverRegle(baseDeRegle_copie);	
		}
			
		// Affichage des résultats
		ihm.afficherFaits(this.baseDeFaits.getFaits());

		return this.baseDeFaits.getFaits();
		
	}
	
	
	/**
	 * Prend en paramètre un string, et crée une règle à partir de celle ci :
	 * String de la forme :
	 * "R1 : IF (Ordre=3(Quel est l'ordre ?)) THEN Triangle"
	 * @param str
	 */
	public void AjouterRegle(String str) {
		//Séparation nom:regle
		String[] nomRegle = str.split(":");
		if(nomRegle.length == 2) {
			String nom = nomRegle[0].trim();
			// séparation premisses THEN conclusion
			String regle = nomRegle[1].trim();
			regle = regle.replaceFirst("^" + "IF", "");
			String[] premissesConclusion = regle.split("THEN");
			if(premissesConclusion.length == 2) {
				// Lecture des premisses
				ArrayList<IFait> premisses = new ArrayList<>();
				String[] premissesStr = premissesConclusion[0].split(" AND ");
				for(String chaine : premissesStr) {
					IFait premisse = FaitFactory.Fait(chaine.trim());
					premisses.add(premisse);
				}
				
				// Lecture de la conclusion 
				String conclusionStr = premissesConclusion[1].trim();
				IFait conclusion = FaitFactory.Fait(conclusionStr);
				
				// Création de la règle et ajout à la base
				this.baseDeRegles.ajouterRegle(new Regle(premisses, conclusion, nom));
				
			}
		}
		
	}
	
	
	
	

}
