package com.example.ui;


import com.example.user.UserModel;
import com.example.user.UserService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class SaveProfileDetaisListener implements Button.ClickListener{

	@Autowired
	private UserService userService;
	@Override
	public void buttonClick(Button.ClickEvent event){
			Button source = event.getButton();
			EditProfileForm parent = (EditProfileForm) source.getParent();
			String username = parent.getTxtLogin();
	     	String nume = parent.getTxtNume();
		    String prenume = parent.getTxtPrenume();
		    String email = parent.getEmail();
	    	String orepezi = parent.getOrePezi();
			String functie = parent.getFunctieTxt();

		    Label label =new Label("<font size = \"10\" color=\"blue\">"
					, ContentMode.HTML);
		    label.setId("label-profil-acutualizat");
			parent.addComponent(label);
			try{
				UserModel user = userService.getUser(username);
				user.setEmail(email);
				user.setNume(nume);
				user.setPrenume(prenume);
				user.setFunctie(functie);
				user.setOrePeZi(Integer.parseInt(orepezi));
				userService.updateUser(user,username);
				user = userService.getUser(username);
				if(user.getEmail()==email&&user.getNume()==nume&&user.getPrenume()==prenume&&user.getFunctie()==functie)
				{
					label.setValue(" Profilul a fost actualizat cu succes");
					Notification.show("Salvat");
				}
				else
				{
					label.setValue(" Profilul NU a fost actualizat cu succes");
					Notification.show("NU a fost salvat");
				}

			}catch (NoSuchElementException e) {
				label.setValue(" Profilul NU a fost actualizat cu succes");
				Notification.show("NU a fost salvat");
			}

	}
}