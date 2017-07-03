package com.example.ui;

import com.example.AppUI;
import com.example.ui.SaveProfileDetaisListener;
import com.example.user.UserModel;
import com.example.user.UserService;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Component;


public class EditProfileForm extends VerticalLayout {


	private TextField txtLogin = new TextField("User: ");
	private PasswordField txtPassword = new PasswordField("Password: ");
	private TextField txtNume = new TextField("Nume: ");
	private TextField txtPrenume = new TextField("Prenume: ");
	private TextField email = new TextField("Email: ");
	private TextField functieField = new TextField("Functie: ");
	private TextField orePeZiField = new TextField("Ore pe zi: ");
	private Button buttonSave = new Button("Salveaza");
	private Button changePass = new Button("Schimba parola");

	public EditProfileForm(String userID){
		txtNume.setId("nume-field");
		functieField.setId("functi-field");
		txtPrenume.setId("prenume-field");
		txtLogin.setId("user-field");
		email.setId("email-field");
		buttonSave.setId("button-save");
		changePass.setId("change-password");
		orePeZiField.setId("ore-pe-zi-field");
		UserService userService= getUserService();
		UserModel user = userService.getUser(userID);
		txtLogin.setValue(userID);
		if(user.getNume()==null)
		{
			txtNume.setValue("");
		}else {
			txtNume.setValue(user.getNume());
		}
		if(user.getFunctie()==null)
		{
			functieField.setValue("");
		}else {
			functieField.setValue(user.getFunctie());
		}
		if(user.getPrenume()==null)
		{
			txtPrenume.setValue("");
		}else {
			txtPrenume.setValue(user.getPrenume());
		}
		if(user.getEmail()==null)
		{
			email.setValue("");
		}else {
			email.setValue(user.getEmail());
		}
		if(user.getOrePeZi()==0)
		{
			orePeZiField.setValue("0");
		}else {
			orePeZiField.setValue(Integer.toString(user.getOrePeZi()));
		}
		addComponent(txtLogin);
		addComponent(functieField);
		addComponent(txtNume);
		addComponent(txtPrenume);
		addComponent(email);
		addComponent(orePeZiField);
		addComponent(buttonSave);
		addComponent(changePass);

		SaveProfileDetaisListener saveProfileDetaisListener =getSaveFormListener();
		buttonSave.addClickListener(saveProfileDetaisListener);
	}

	public SaveProfileDetaisListener getSaveFormListener(){
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context = ui.getApplicationContext();
		return context.getBean(SaveProfileDetaisListener.class);
	}

	public String getTxtNume() {
		return txtNume.getValue();
	}
	public String getOrePezi() {
		return orePeZiField.getValue();
	}
	public String getFunctieTxt() {
		return functieField.getValue();
	}

	public String getTxtPrenume() {
		return txtPrenume.getValue();
	}

	public String getEmail() {
		return email.getValue();
	}

	public String getTxtLogin(){
		return txtLogin.getValue();
	}

	public PasswordField getTxtPassword(){
		return txtPassword;
	}

	public UserService getUserService()
	{
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context1 = ui.getApplicationContext();
		UserService bean = context1.getBean(UserService.class);
		return bean;
	}
}