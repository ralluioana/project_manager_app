package com.example.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalLayout implements View {
	public LoginView(){
		LoginForm loginForm = new LoginForm();
		addComponent(loginForm);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event){}
}