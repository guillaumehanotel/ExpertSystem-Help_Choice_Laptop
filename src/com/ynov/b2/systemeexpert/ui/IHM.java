package com.ynov.b2.systemeexpert.ui;

import java.io.IOException;
import java.util.ArrayList;

import com.ynov.b2.systemeexpert.beans.IFait;
import com.ynov.b2.systemeexpert.beans.Regle;

public interface IHM {

	int demanderValeurEntiere(String question);
	boolean demanderValeurBooleenne(String question);
	void afficherFaits(ArrayList<IFait> faits);
	void afficherRegles(ArrayList<Regle> regles);
	
}
