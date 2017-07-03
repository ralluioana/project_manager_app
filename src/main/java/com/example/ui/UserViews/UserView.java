package com.example.ui.UserViews;

import com.example.ui.AdminViews.AdminMenu;
import com.example.ui.LogoutListener;
import com.example.ui.UserViews.ClientMenu;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;


public class UserView extends VerticalLayout implements View {

	String userID;
	public void enter(ViewChangeListener.ViewChangeEvent event){

		removeAllComponents();
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if(authentication != null && authentication.isAuthenticated()){
			userID = authentication.getName();
			HorizontalLayout headerLayout= new HorizontalLayout();
			headerLayout.setSizeFull();
			Label labelLogin = new Label("Welcome: " + userID);
			labelLogin.setId("labal-username");
			labelLogin.setStyleName(ValoTheme.LABEL_H1,true);
			labelLogin.setStyleName(ValoTheme.LABEL_COLORED,true);
			labelLogin.setStyleName(ValoTheme.LABEL_BOLD,true);
			headerLayout.addComponent(labelLogin);
			Button logout = new Button("Logout");
			logout.setStyleName(ValoTheme.BUTTON_ICON_ALIGN_RIGHT,true);
			logout.setStyleName(ValoTheme.BUTTON_QUIET,true);
			logout.setStyleName(ValoTheme.BUTTON_LARGE,true);
			logout.setIcon(VaadinIcons.SIGN_OUT);
			logout.setId("button-logout");
			LogoutListener logoutListener = new LogoutListener();
			logout.addClickListener(logoutListener);
			headerLayout.addComponent(logout);
			addComponent(headerLayout);

			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (GrantedAuthority ga : authorities) {
				String authority = ga.getAuthority();
				if ("ADMIN".equals(authority)) {
					Label lblAuthority = new Label("Sunteti administrator. ");
					lblAuthority.setId("labal-authority");
					addComponent(lblAuthority);
					AdminMenu menu= new AdminMenu(userID);
					addComponent(menu);
				} else {
					Label lblAuthority = new Label("Autoritatea: " + authority);
					lblAuthority.setId("labal-authority");
					addComponent(lblAuthority);
					ClientMenu menu= new ClientMenu(userID);
					addComponent(menu);
				}
			}
		}else{
			Navigator navigator = UI.getCurrent().getNavigator();
			navigator.navigateTo("login");
		}
	}
}
