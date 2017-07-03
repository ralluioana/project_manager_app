package com.example;

import com.example.ui.LoginView;
import com.example.ui.UserViews.UserView;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.WrappedHttpSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

@SpringUI
public class AppUI extends UI {
	
	private static final long serialVersionUID = 1L;
	private ApplicationContext applicationContext;

	@Override
	protected void init(VaadinRequest request){
		WrappedSession session = request.getWrappedSession();
		HttpSession httpSession = ((WrappedHttpSession) session).getHttpSession();
		ServletContext servletContext = httpSession.getServletContext();
		applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		Navigator navigator = new Navigator(this, this);
		navigator.addView("login", LoginView.class);
		navigator.addView("user", UserView.class);
		navigator.navigateTo("login");
		setNavigator(navigator);
	}

	public ApplicationContext getApplicationContext(){
		return applicationContext;
	}
}