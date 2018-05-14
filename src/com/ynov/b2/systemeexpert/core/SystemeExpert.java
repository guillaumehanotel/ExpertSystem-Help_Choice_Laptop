package com.ynov.b2.systemeexpert.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import com.ynov.b2.systemeexpert.beans.Computer;
import com.ynov.b2.systemeexpert.beans.IFait;
import com.ynov.b2.systemeexpert.beans.Regle;
import com.ynov.b2.systemeexpert.ui.MainFrame;
import com.ynov.b2.systemeexpert.ui.IHM;
import com.ynov.b2.systemeexpert.utils.Database;
import com.ynov.b2.systemeexpert.utils.QueryBuilder;

public class SystemeExpert {

	MoteurInferenceAvant moteur;
	IHM ihm;


	public SystemeExpert(IHM ihm) {
		this.ihm = ihm;

		System.out.println("** Création du moteur **");
		moteur = new MoteurInferenceAvant(ihm);

		System.out.println("** Ajout des règles **");
		initRegles();

	}	

	public ArrayList<Computer> run() {

		ArrayList<IFait> faitsTrouves = new ArrayList<IFait>();

		System.out.println("\n** Résolution **");
		faitsTrouves = (ArrayList<IFait>) moteur.Resoudre();

		// Construction de la requête en fonction des résultats
		QueryBuilder queryBuilder = new QueryBuilder(faitsTrouves);
		String request = queryBuilder.getRequest();
		System.out.println(request);

		ArrayList<Computer> computers = getComputersResults(request);
		
		for (Computer computer : computers) {
			System.out.println(computer);
		}

		return computers;

	}


	public ArrayList<Computer> getComputersResults(String request){
		
		ArrayList<Computer> computers = new ArrayList<>();

		Database database = new Database("pc_choosing");
		ResultSet resultats = database.requete(request);

		try {
			while(resultats.next()) {

				int id = resultats.getInt("id");
				double weight = resultats.getDouble("weight");
				String model = resultats.getString("model");
				boolean ssd = resultats.getBoolean("ssd");
				double ram = resultats.getDouble("ram");
				double price = resultats.getDouble("price");
				double storage = resultats.getDouble("storage");
				String cpu_model = resultats.getString("cpu_model");
				double size = resultats.getDouble("size");
				String resolution = resultats.getString("resolution");
				String gpu_model = resultats.getString("gpu_model");
				int rank = resultats.getInt("rank");

				computers.add(new Computer(id, weight, model, ssd, ram, price, storage, cpu_model, size, resolution, gpu_model, rank));		

			}
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			System.exit(1);
		}
		
		return computers;

	}


	public void initRegles() {

		/**
		 *  GPU1 : Classement de 1->20 GPU
			GPU2 : Classement de 21->40 GPU
			GPU3 : Classement de 41->X GPU

			GrandEcran :  <= 14 pouces
			MoyenEcran :  > 14 && < 16 pouces
			PetitEcran :  >= 16 pouces

			AvecSSD : Ajout SSD
			SansSSD : Enleve SSD
			DefaultSSD : Avec ou Sans

			minRAM : >= 4Go && < 8Go
			MoyRAM : >= 8Go && < 16Go
			BigRAM : >= 16Go && < 32Go
			ExtraRAM : >= 32Go

			MinHDD : <= 500Go
			MoyHDD : > 500Go && <= 1200 Go
			MaxHDD : > 1200Go

			on utilise le moteur d'inférence pour déduire nos caractéristiques finales à partir des faits booléens trouvés
			à partir des faits entiers (réponses aux questions)

			Exemple :
			On a le fait entier 'Utilisation', si l'utilisateur choisit 1, on en déduit le fait booléen 'Gaming' grâce à une règle
			On demande ensuite le level de gaming, si l'utilisateur choisit 1, on en déduit le fait booléen GPU1 à partir du fait 
			du fait entier LevelGaming = 1 et du fait booléen Gaming = true

			Une fois qu'on connait GPU1, on fait une requete SQL pour récup les PC

		 */

		// Ajout des règles


		String questionUtilisation = "Quelle est l'utilisation principale que vous voulez faire de votre PC ?\n"
				+ "1 -> Gaming\n"
				+ "2 -> Travail\n"
				+ "3 -> Animation";

		String questionGaming = "Quels sont vos besoins pour jouer à vos jeux ?\n"
				+ "1 -> Je veux pouvoir jouer à tout type de jeu en qualité maximale\n"
				+ "2 -> Je veux pouvoir faire tourner mes jeux correctement\n"
				+ "3 -> Je n'ai pas besoin d'une configuration exigeante pour jouer";

		String questionEcran = "Quelle taille d'écran souhaiteriez-vous ?\n"
				+ "1 -> Je souhaite un grand écran\n"
				+ "2 -> Je souhaite un écran de taille moyenne\n"
				+ "3 -> Je souhaite un petit écran";

		String questionSSD = "Voulez-vous un SSD ?\n"
				+ "1 -> Oui\n"
				+ "2 -> Non\n"
				+ "3 -> Peu importe";

		String questionRAM = "Quelle quantité de RAM souhaitez-vous ?\n"
				+ "1 -> 4Go minimum\n"
				+ "2 -> 8Go minimum\n"
				+ "3 -> 16Go minimum\n"
				+ "4 -> 32Go minimum";

		String questionHDD = "Quelle capacité de stockage souhaiteriez-vous ?\n"
				+ "1 -> Je souhaite une petite capacité\n"
				+ "2 -> Je souhaite une capacité moyenne\n"
				+ "3 -> Je souhaite une grande capacité";



		moteur.AjouterRegle("R1 : IF (Utilisation=1("+questionUtilisation+")) THEN Gaming");
		moteur.AjouterRegle("R2 : IF (Utilisation=2("+questionUtilisation+")) THEN Bureautique");
		moteur.AjouterRegle("R3 : IF (Utilisation=3("+questionUtilisation+")) THEN Animation");

		moteur.AjouterRegle("R4 : IF (Gaming AND LevelGaming=1("+questionGaming+")) THEN GPU1");
		moteur.AjouterRegle("R5 : IF (Gaming AND LevelGaming=2("+questionGaming+")) THEN GPU2");
		moteur.AjouterRegle("R6 : IF (Gaming AND LevelGaming=3("+questionGaming+")) THEN GPU3");

		moteur.AjouterRegle("R7 : IF (TailleEcran=1("+questionEcran+")) THEN GrandEcran");
		moteur.AjouterRegle("R8 : IF (TailleEcran=2("+questionEcran+")) THEN MoyenEcran");
		moteur.AjouterRegle("R9 : IF (TailleEcran=3("+questionEcran+")) THEN PetitEcran");

		moteur.AjouterRegle("R10 : IF (SSD=1("+questionSSD+")) THEN AvecSSD");
		moteur.AjouterRegle("R11 : IF (SSD=2("+questionSSD+")) THEN SansSSD");
		moteur.AjouterRegle("R12 : IF (SSD=3("+questionSSD+")) THEN DefaultSSD");

		moteur.AjouterRegle("R13 : IF (RAM=1("+questionRAM+")) THEN MinRAM");
		moteur.AjouterRegle("R14 : IF (RAM=2("+questionRAM+")) THEN MoyRAM");
		moteur.AjouterRegle("R15 : IF (RAM=3("+questionRAM+")) THEN BigRAM");
		moteur.AjouterRegle("R16 : IF (RAM=4("+questionRAM+")) THEN ExtraRAM");

		moteur.AjouterRegle("R17 : IF (HDD=1("+questionHDD+")) THEN MinHDD");
		moteur.AjouterRegle("R18 : IF (HDD=2("+questionHDD+")) THEN MoyHDD");
		moteur.AjouterRegle("R19 : IF (HDD=3("+questionHDD+")) THEN BigHDD");

	}



}
