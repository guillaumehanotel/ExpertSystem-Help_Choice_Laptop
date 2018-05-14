package com.ynov.b2.systemeexpert.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.ynov.b2.systemeexpert.beans.FaitBoolean;
import com.ynov.b2.systemeexpert.beans.FaitEntier;
import com.ynov.b2.systemeexpert.beans.IFait;
import com.ynov.b2.systemeexpert.core.MoteurInferenceAvant;


/**
 * classe chargé de construire des nouveaux faits :
 * Sert dans le cas où l'on parcourt les prémisses(faits) d'une règle, et que :
 * le fait possède une question, et dans ce cas il faut demander la valeur de l'utilisateur et l'ajouter dans la base de faits
 * 
 *
 */
public class FaitFactory {

	
	public static IFait Fait(IFait f, MoteurInferenceAvant moteur) {
		try {
			IFait nouveauFait;
			Class classe = f.getClass();
			
			if(classe.equals(Class.forName("com.ynov.b2.systemeexpert.beans.FaitEntier"))) {
				// Création d'un fait entier
				// on pose la question
				int valeur = moteur.DemanderValeurEntiere(f.getQuestion());
				
				nouveauFait = new FaitEntier(f.getNom(), valeur, 0, null);
			} else {
				// C'est un fait booléen
				boolean valeurB = moteur.DemanderValeurBooleenne(f.getQuestion());
				nouveauFait = new FaitBoolean(f.getNom(), valeurB, 0, null);
			}
			return nouveauFait;
			
		} catch(ClassNotFoundException ex) {
			Logger.getLogger(FaitFactory.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public static IFait Fait(String faitStr) {
		faitStr = faitStr.trim();
		if(faitStr.contains("=")) {
			// Il y a le symbole =, c'est donc un fait entier
			faitStr = faitStr.replaceFirst("^" + "\\(", "");
			String[] nomValeurQuestion = faitStr.split("[=()]");
			
			if(nomValeurQuestion.length >= 2) {
				// On a un fait correct Nom=Valeur[(question)]
				String question = null;
				if(nomValeurQuestion.length == 3) {
					// Le fait contient une question
					question = nomValeurQuestion[2].trim();
				}
				return new FaitEntier(nomValeurQuestion[0].trim(), Integer.parseInt(nomValeurQuestion[1].trim()), 0, question);
			}
			
			
		} else {
			// C'est un fait booléen nom[(question)] ou !nom[(question)]
			boolean valeur = true;
			if(faitStr.startsWith("!")) {
				// négatif
				valeur = false;
				// on enlève le "!"
				faitStr = faitStr.substring(1).trim();
			}
			// Split, après avoir enlevé le premier délimiteru si besoin : "("
			faitStr = faitStr.replaceFirst("^" + "\\(", "");
			String[] nomQuestion = faitStr.split("[()]");
			String question = null;
			if(nomQuestion.length == 2) {
				// il y a une question
				question = nomQuestion[1].trim();
			}
			return new FaitBoolean(nomQuestion[0].trim(), valeur, 0, question);
		}
		// si on arrive ici, la syntaxe est incorrect
		return null;
	}
	
	
	
	
	
	
	
}
