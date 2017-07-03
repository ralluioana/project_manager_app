package com.example.config;

import com.example.echipe.EchipaService;
import com.example.functii.FunctiiService;
import com.example.ui.SaveProfileDetaisListener;
import com.example.ui.UserViews.UserView;
import com.example.user.UserService;
import com.example.auth.AuthManager;
import com.example.ui.LoginFormListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class AppConfig {
	@Bean
	public AuthManager authManager(){
		AuthManager res = new AuthManager();
		return res;
	}

	@Bean
	public UserService userService(){
		UserService res = new UserService();
		return res;
	}

	@Bean
	public EchipaService echipaService(){
		EchipaService res = new EchipaService();
		return res;
	}


	@Bean
	public FunctiiService functiiService(){
		FunctiiService res = new FunctiiService();
		return res;
	}

	@Bean
	public LoginFormListener loginFormListener(){
		return new LoginFormListener();
	}

	@Bean
	public SaveProfileDetaisListener saveProfileDetaisListener(){
		return new SaveProfileDetaisListener();
	}

	@Bean
	public UserView userView(){
		return new UserView();
	}

}