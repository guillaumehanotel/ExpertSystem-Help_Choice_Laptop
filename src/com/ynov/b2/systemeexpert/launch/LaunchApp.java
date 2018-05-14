package com.ynov.b2.systemeexpert.launch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.ynov.b2.systemeexpert.ui.MainFrame;

public class LaunchApp {

	public static void main(String[] args) {
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		       if ("Nimbus".equals(info.getName())) {
		           UIManager.setLookAndFeel(info.getClassName());
		           break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		MainFrame fenetre = new MainFrame();
		fenetre.afficheInterface();
		
	}

}
