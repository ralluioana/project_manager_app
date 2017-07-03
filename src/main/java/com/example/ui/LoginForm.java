package com.example.ui;

import com.example.AppUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.context.ApplicationContext;

public class LoginForm extends VerticalLayout {
	private TextField txtLogin = new TextField("Login: ");
	private PasswordField txtPassword = new PasswordField("Password: ");
	private Button btnLogin = new Button("Login");
	private Button btnsingup = new Button("Register");

	public LoginForm(){
		Label labelLogin = new Label("Agile Manager Software");
		labelLogin.setId("label-welcome-login");
		labelLogin.setStyleName(ValoTheme.LABEL_H1,true);
		labelLogin.setStyleName(ValoTheme.LABEL_COLORED,true);
		labelLogin.setStyleName(ValoTheme.LABEL_BOLD,true);

		addComponent(labelLogin);
		txtLogin.setId("login-field");
		addComponent(txtLogin);
		txtPassword.setId("password-field");
		addComponent(txtPassword);
		btnLogin.setId("login-button");
		addComponent(btnLogin);
		btnsingup.setId("register-button");
		addComponent(btnsingup);
		LoginFormListener loginFormListener = getLoginFormListener();
		btnLogin.addClickListener(loginFormListener);

		RegisterFormListener registerFormListener =getSingupFormListener();
		btnsingup.addClickListener(registerFormListener);
	}

	public LoginFormListener getLoginFormListener(){
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context = ui.getApplicationContext();
		return context.getBean(LoginFormListener.class);
	}
	public RegisterFormListener getSingupFormListener(){
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context = ui.getApplicationContext();
		return context.getBean(RegisterFormListener.class);
	}
	public TextField getTxtLogin(){
		return txtLogin;
	}

	public PasswordField getTxtPassword(){
		return txtPassword;
	}
}