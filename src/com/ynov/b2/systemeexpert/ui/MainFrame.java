package com.ynov.b2.systemeexpert.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.ynov.b2.systemeexpert.beans.Computer;
import com.ynov.b2.systemeexpert.beans.IFait;
import com.ynov.b2.systemeexpert.beans.Regle;
import com.ynov.b2.systemeexpert.core.SystemeExpert;



public class MainFrame extends JFrame implements IHM {

	SystemeExpert systemeexpert;
	Browser browser;
	BrowserView view;
	private static CountDownLatch waitForSubmit = new CountDownLatch(1);
	
	private static final String QUESTION_URL = "file:///home/guillaumeh/Documents/Workspaces/Java/JavaSE/Work/EclipseWorkspace-B2/SystemeExpertUI/question.html";
	private static final String INDEX_URL = "file:///home/guillaumeh/Documents/Workspaces/Java/JavaSE/Work/EclipseWorkspace-B2/SystemeExpertUI/index.html";
	private static final String RESULTATS_URL = "file:///home/guillaumeh/Documents/Workspaces/Java/JavaSE/Work/EclipseWorkspace-B2/SystemeExpertUI/resultats.html";

	
	public MainFrame() {

	}
	
	/**
	 * Lance le système expert
	 */
	public void startExpertSystem() {
		systemeexpert = new SystemeExpert(this);
		ArrayList<Computer> computers = systemeexpert.run();	
		
		displayResults(computers);
		
		
	}

	/**
	 * Lance la fenetre avec la page d'accueil, ainsi que les listeners des boutons
	 */
	public void afficheInterface() {

		// Fenetre
		this.setTitle("Help Choice Laptop");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 800);
		this.getContentPane().setBackground(new Color(0x034F84));
		this.setLocationRelativeTo(null);
		
		launchURL(INDEX_URL);
		
		DOMDocument document = browser.getDocument();
		
		// Listener Btn Commencer
		DOMElement buttonStart = document.findElement(By.id("start"));
		buttonStart.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
			@Override
			public void handleEvent(DOMEvent event) {
				
				startExpertSystem();
					
			}
		}, false);
		
		// Listener Btn Quitter
		DOMElement buttonQuit = document.findElement(By.id("quit"));
		buttonQuit.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
			@Override
			public void handleEvent(DOMEvent event) {
				browser.dispose();
				dispatchEvent(new WindowEvent(MainFrame.this, WindowEvent.WINDOW_CLOSING));
			}
		}, false);
	}

	
	/**
	 * Stop un navigateur si il en existe un
	 * Puis lance un nouveau browser avec URL passé en paramètre
	 */
	public void launchURL(String URL) {
		
		if(browser != null) {
			browser.dispose();
		}
		
		browser = new Browser();
        view = new BrowserView(browser);
        BrowserPreferences.setChromiumSwitches("--remote-debugging-port=9222");
        
        
        this.getContentPane().add(view, BorderLayout.CENTER);
        this.setVisible(true);
        
		Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
		    @Override
		    public void invoke(Browser browser) {
		    	browser.loadURL(URL);
		    }
		});
	}
	
	/**
	 * Ajoute un paragraphe avec le text passé en paramètre comme enfant de l'élément root
	 */
	public void addParagraph(DOMDocument document, DOMNode root, String text) {
		// text
		DOMNode textNode = document.createTextNode(text);
		// HTML p Element
		DOMElement paragraph = document.createElement("p");
		// ajout du text au p
		paragraph.appendChild(textNode);
		// ajout du p en dessous du root
		root.appendChild(paragraph);
	}
	
	public void addRow(DOMDocument document, DOMNode root, Computer computer) {
		
		// on crée la ligne
		DOMElement row = document.createElement("tr");
		
		// on récupère les textes de chaque colonne
		DOMNode modelTextNode = document.createTextNode(computer.getModel().toUpperCase());
		DOMNode priceTextNode = document.createTextNode(String.valueOf(computer.getPrice()) + "€");
		
		// on crée chaque cellule de la ligne
		DOMElement tdModel = document.createElement("td");
		DOMElement tdPrice = document.createElement("td");
		
		// on associe le texte à leurs cellules
		tdModel.appendChild(modelTextNode);
		tdPrice.appendChild(priceTextNode);
		
		// on ajoute les cellules à la ligne
		row.appendChild(tdModel);
		row.appendChild(tdPrice);
		
		// puis la ligne à son parent
		root.appendChild(row);
		
	}
	
	
	
	public void displayResults(ArrayList<Computer> computers) {
		
		int nbComputers = computers.size();
		
		launchURL(RESULTATS_URL);
		
		DOMDocument document = browser.getDocument();
		
		
		// Listener Btn Reommencer
		DOMElement buttonStart = document.findElement(By.id("restart"));
		buttonStart.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
			@Override
			public void handleEvent(DOMEvent event) {
				
				startExpertSystem();
					
			}
		}, false);
		
		// Listener Btn Quitter
		DOMElement buttonQuit = document.findElement(By.id("quit"));
		buttonQuit.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
			@Override
			public void handleEvent(DOMEvent event) {
				browser.dispose();
				dispatchEvent(new WindowEvent(MainFrame.this, WindowEvent.WINDOW_CLOSING));
			}
		}, false);
		
		
		
		DOMNode root = document.findElement(By.id("tbody"));
		DOMNode divNbPc = document.findElement(By.id("nbResultats"));
		
		String textNbPc;
		
		if(nbComputers == 0) {
			
			textNbPc = "Aucun résultat ne correpond à vos critères, réessayer avec d'autres choix";
			
			// supprimer le tableau
			browser.executeJavaScript("document.body.firstChild.nextSibling.children[1].children[2].children[0].remove()");
					
		} else {
			
			textNbPc = nbComputers + " ordinateur(s) portable(s) correspond(ent) à vos critères";
			
			for (Computer computer : computers) {
				addRow(document, root, computer);
			}
			
		}
		
		// Phrase de résultats
		DOMNode NbPcTextNode = document.createTextNode(textNbPc);
		DOMElement paragraph = document.createElement("p");
		paragraph.appendChild(NbPcTextNode);
		divNbPc.appendChild(paragraph);
		
	}
	
	
	
	@Override
	public int demanderValeurEntiere(String question) {
		int res;

		launchURL(QUESTION_URL);
		
		
		 String remoteDebuggingURL = browser.getRemoteDebuggingURL();
		 System.out.println(remoteDebuggingURL);
		 
		
		DOMDocument document = browser.getDocument();

		// Ajout de la question à l'interface
		DOMNode root = document.findElement(By.id("question"));
		String[] questionElements = question.split("\n");
		
		for (String elem : questionElements) {
			addParagraph(document, root, elem);
		}

		// association de la classe Result, au résultat de la fct js qui récupère le contenu du formulaire
		JSValue value = browser.executeJavaScriptAndReturnValue("window");
        value.asObject().setProperty("Result", new Result());
        
        
        // on attend le retour du callback pour retourner le résultat
        try {
			waitForSubmit.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        res = Integer.parseInt(Result.result);  

        // on réinstancie le compteur pour la prochaine question
        waitForSubmit = new CountDownLatch(1);
        
		return res;
	}

	
	@Override
	public boolean demanderValeurBooleenne(String question) {
		try {

			int answer = JOptionPane.showConfirmDialog(null, question);
			if (answer == JOptionPane.YES_OPTION) {
				return true;
			} else if (answer == JOptionPane.NO_OPTION) {
				return false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	
	@Override
	public void afficherFaits(ArrayList<IFait> faits) {
		String res = "Solution(s) trouvée(s) : \n";
		//faits.sort(null);
		Collections.sort(faits, (IFait f1, IFait f2) -> {
			if(f1.getNiveau() == f2.getNiveau()) {
				return 0;
			} else if(f1.getNiveau() > f2.getNiveau()){
				return -1;
			} else {
				return 1;
			}
		});

		for(IFait f : faits) {
			if(f.getNiveau() != 0) {
				res += f.toString() + "\n";
			}
		}
		System.out.println(res);
	}

	@Override
	public void afficherRegles(ArrayList<Regle> regles) {
		String res = "";
		for(Regle r : regles) 
			res += r.toString()+"\n"; 
		System.out.println(res);

	}

	
	/**
	 * Classe servant à récupérer les valeurs provenant du JS
	 */
    public static class Result {
    	public static String result;
    	/**
    	 * Fonction de callback appelé par le JS une fois que je formulaire a été rempli
    	 */
        public void save(String res) {
            System.out.println("result = " + res);
            result = res;
            // on décrémente le compteur qui sert à attendre la fin du callback 
            // -> permet de déclencher la suite du programme après le await()
            waitForSubmit.countDown();
        }
    }


}
