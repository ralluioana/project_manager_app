package com.example.ui;


import com.example.AppUI;
import com.example.user.UserModel;
import com.example.user.UserService;
import com.example.auth.AuthManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class RegisterFormListener implements Button.ClickListener{

	@Autowired
	private AuthManager authManager;

	@Autowired
	private UserService userService;
	@Override
	public void buttonClick(Button.ClickEvent event){
		try {
			Button source = event.getButton();
			LoginForm parent = (LoginForm) source.getParent();
			String username = parent.getTxtLogin().getValue();
			String password = parent.getTxtPassword().getValue();
			try{
				userService.getUser(username);
				Label label = new Label("<font size = \"16\" color=\"red\"> User existent"
						, ContentMode.HTML);
				label.setId("label-username-existent");
				parent.addComponent(label);
				Notification.show("User existent");
			}catch (NoSuchElementException e) {

				if(username.equals("admin"))
				{
					Notification.show("Nu aveti drepturi de inregistrare ca administrator");
				}
				else {
					userService.addUser(new UserModel(username, password));
					UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(username, password);
					Authentication result = authManager.authenticate(request);
					SecurityContextHolder.getContext().setAuthentication(result);
					AppUI current = (AppUI) UI.getCurrent();
					Navigator navigator = current.getNavigator();
					navigator.navigateTo("user");
				}
			}
		} catch (AuthenticationException e) {
			Notification.show("Authentication failed: " + e.getMessage());
		}
	}
}