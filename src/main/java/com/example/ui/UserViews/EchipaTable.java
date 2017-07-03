package com.example.ui.UserViews;

import com.example.AppUI;
import com.example.ui.SaveProfileDetaisListener;
import com.example.user.UserModel;
import com.example.user.UserService;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.*;


public class EchipaTable extends VerticalLayout {

	@Autowired
	UserService userService;
	private Label echipa = new Label("ECHIPA :");
	private Label noelement = new Label("");
	Grid grid;
	TextField nameFilter;
	TextField prenumeFilter;
	TextField emailFilter;
	TextField functieFilter;


	public EchipaTable(String userID){
		echipa.setId("echipa-field");

		userService= getUserService();
		UserModel user = userService.getUser(userID);
		if(user.getEchipa()==null)
		{
			echipa.setValue("Fara echipa asignata");
		}else {
			echipa.setValue(echipa.getValue()+user.getEchipa());
		}
		addComponent(echipa);
		Collection<UserModel> personss=new ArrayList<>() ;
		List<UserModel> allUsers = userService.getAllUsers();
        for (UserModel userModel:allUsers)
		{
			try {
				if (userModel.getEchipa().equals(user.getEchipa())) {
					personss.add(userModel);
				}
			}catch(NullPointerException e)
			{
				echipa.setValue("Fara echipa asignata");
			}
		}
       // Create a grid bound to the container
		grid = new Grid(UserModel.class);
        grid.setId("tabel-echipa");
		grid.setItems(personss);

		grid.setColumns("nume","prenume","email","functie");
		grid.setSizeFull();
		HorizontalLayout horizontalLayout=new HorizontalLayout();
		nameFilter = new TextField();
		nameFilter.setId("filtru-nume");
		nameFilter.setPlaceholder("Nume...");
		nameFilter.addValueChangeListener(this::onNameFilterTextChange);
		horizontalLayout.addComponent(nameFilter);

		prenumeFilter = new TextField();
		prenumeFilter.setId("filtru-prenume");
		prenumeFilter.setPlaceholder("Prenume...");
		prenumeFilter.addValueChangeListener(this::onPrenumwFilterTextChange);
		horizontalLayout.addComponent(prenumeFilter);

		functieFilter = new TextField();
		functieFilter.setId("filtru-functie");
		functieFilter.setPlaceholder("Functie...");
		functieFilter.addValueChangeListener(this::onFuntieFilterTextChange);
		horizontalLayout.addComponent(functieFilter);

		emailFilter = new TextField();
		emailFilter.setId("filtru-email");
		emailFilter.setPlaceholder("Email...");
		emailFilter.addValueChangeListener(this::onEmailFilterTextChange);
		horizontalLayout.addComponent(emailFilter);

		addComponent(horizontalLayout);
		addComponent(noelement);
		addComponent(grid);

}
	private void onNameFilterTextChange(HasValue.ValueChangeEvent<String> event) {
		ListDataProvider<UserModel> dataProvider = (ListDataProvider<UserModel>) grid.getDataProvider();
		try
		{
			dataProvider.setFilter(UserModel::getNume, s -> caseInsensitiveContains(s, event.getValue()));
		}catch (NoSuchElementException e)
		{
			noelement.setValue("Nu exista in lista");
		}
	}
	private void onFuntieFilterTextChange(HasValue.ValueChangeEvent<String> event) {
		ListDataProvider<UserModel> dataProvider = (ListDataProvider<UserModel>) grid.getDataProvider();
		try
		{
			dataProvider.setFilter(UserModel::getFunctie, s -> caseInsensitiveContains(s, event.getValue()));
		}catch (NoSuchElementException e)
		{
			noelement.setValue("Nu exista in lista");
		}
	}
	private void onEmailFilterTextChange(HasValue.ValueChangeEvent<String> event) {
		ListDataProvider<UserModel> dataProvider = (ListDataProvider<UserModel>) grid.getDataProvider();
		try
		{
			dataProvider.setFilter(UserModel::getEmail, s -> caseInsensitiveContains(s, event.getValue()));
		}catch (NoSuchElementException e)
		{
			noelement.setValue("Nu exista in lista");
		}
	}

	private void onPrenumwFilterTextChange(HasValue.ValueChangeEvent<String> event) {
		ListDataProvider<UserModel> dataProvider = (ListDataProvider<UserModel>) grid.getDataProvider();
		try
		{
			dataProvider.setFilter(UserModel::getPrenume, s -> caseInsensitiveContains(s, event.getValue()));
		}catch (NoSuchElementException e)
		{
			noelement.setValue("Nu exista in lista");
		}
	}
	private Boolean caseInsensitiveContains(String where, String what) {
		return where.toLowerCase().contains(what.toLowerCase());
	}

	public SaveProfileDetaisListener getSaveFormListener(){
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context = ui.getApplicationContext();
		return context.getBean(SaveProfileDetaisListener.class);
	}


	public UserService getUserService()
	{
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context1 = ui.getApplicationContext();
		UserService bean = context1.getBean(UserService.class);
		return bean;
	}
}