package com.example.ui.AdminViews;

import com.example.AppUI;
import com.example.config.Functii;
import com.example.echipe.EchipaModel;
import com.example.echipe.EchipaService;
import com.example.functii.FunctiiModel;
import com.example.functii.FunctiiService;
import com.example.user.UserModel;
import com.example.user.UserService;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


public class AddResourcesAdminView extends VerticalLayout {

	@Autowired
	UserService userService=getUserService();
	@Autowired
	EchipaService echipaService=getEchipeService();
	@Autowired
	FunctiiService functiiService=getFunctiiService();


	private Label noelement = new Label("");

	public AddResourcesAdminView(){
		addComponent(addEchipaLayout());
		addComponent(adaugaFunctie());
		addComponent(stergereFunctii());
	}

	private VerticalLayout adaugaFunctie() {

		TextField numeFunctie=new TextField("Denumire functie");
		numeFunctie.setId("nume-functie-text-field");
		VerticalLayout addFunctieLayout=new VerticalLayout();
		addFunctieLayout.addComponent(numeFunctie);
		 Button addFucntie=new Button("Adaugare functie");
		addFucntie.addStyleName(ValoTheme.BUTTON_PRIMARY);
		addFunctieLayout.addComponent(addFucntie);
		addFucntie.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				if(numeFunctie.getValue().isEmpty())
				{
					new Notification("Denumire functie necompletata", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				}
				else
				{
					if(functiiService.getAllFunctii().contains(new FunctiiModel(numeFunctie.getValue())))
					{
						new Notification("Functie deja existenta", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
					}
					else{
						functiiService.addFunctie(new FunctiiModel(numeFunctie.getValue()));
						new Notification("Functie salvata", Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
					}
				}
			}
		});

		return addFunctieLayout;
	}

	private VerticalLayout stergereFunctii() {
       VerticalLayout stergereLAyout =new VerticalLayout();
		Collection <String> functiiList=new ArrayList<>();
		CheckBoxGroup <String> multiSelectFunctiiList=new CheckBoxGroup<>("Functii");
		multiSelectFunctiiList.setId("checkboxesgorup-functii");
		for(FunctiiModel functiiModel : functiiService.getAllFunctii())
		{
			functiiList.add(functiiModel.getNumeFunctie());
		}
		multiSelectFunctiiList.setItems(functiiList);
		stergereLAyout.addComponent(multiSelectFunctiiList);

		Button stergereFunctie=new Button("Stergere functii");
		stergereFunctie.addStyleName(ValoTheme.BUTTON_DANGER);
		stergereFunctie.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				if(multiSelectFunctiiList.getValue().size()>0) {
					Set<String> functiiSelectate = multiSelectFunctiiList.getValue();
					functiiList.remove(multiSelectFunctiiList);
					multiSelectFunctiiList.setItems(functiiList);
					for (String functie : functiiSelectate) {
						functiiService.deleteFunctie(functie);
						new Notification("S-au sters echipele selectate", Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
					}
				}
			}
		});

		stergereLAyout.addComponent(stergereFunctie);
		return stergereLAyout;
	}

	private VerticalLayout addEchipaLayout() {
		List<UserModel> allUsers = userService.getAllUsers();
		Collection<String> angajati=new ArrayList<>();
		for (UserModel userModel:allUsers)
		{
			angajati.add(userModel.getNume()+" "+userModel.getPrenume());
		}

		VerticalLayout addEchipaLayout=new VerticalLayout();


		TextField numeEchipaField=new TextField("Nume Echipa");
		numeEchipaField.setId("nume-echipa-textfield");
		ComboBox managerEchipaField=new ComboBox("Nume manager");
		managerEchipaField.setId("nume-manager-combobox");
		managerEchipaField.setItems(angajati);

		Button addEchipa=new Button("Adauga Echipa");
		addEchipa.setStyleName(ValoTheme.BUTTON_PRIMARY,true);
		addEchipa.setId("add-echipe-button");
		addEchipa.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				try {
					if(echipaService.getAllEchipe().contains(echipaService.getEchipa(numeEchipaField.getValue()))){
						new Notification("Echipa deja existenta", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
					}
				}catch (Exception e)
				{
				if(numeEchipaField.isEmpty()==true||numeEchipaField.getValue().length()<5) {
					new Notification("NU s-a adaugat echipa", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
					new Notification("Nume prea scurt sau necompletat", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				}
				else{
					EchipaModel echipaModel = new EchipaModel(numeEchipaField.getValue());
					if(managerEchipaField.isEmpty()==false) {
						echipaModel.setManager(managerEchipaField.getValue().toString());
						echipaService.addEchipa(echipaModel);
						UserModel userModel = userService.getUserByFullName(managerEchipaField.getValue().toString());
						userModel.setFunctie(Functii.Functie.MANAGER.toString());
						userModel.setEchipa(numeEchipaField.getValue());
						userModel.setScrumMaster(true);
						userService.updateUser(userModel, userModel.getUserName());
					}
					else{
						echipaService.addEchipa(echipaModel);
						UserModel userModel = userService.getUserByFullName(managerEchipaField.getValue().toString());
						userModel.setEchipa(numeEchipaField.getValue());
						userService.updateUser(userModel, userModel.getUserName());
					}
					new Notification("S-a adaugat echipa", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				}
			}}
		});
		addEchipaLayout.addComponent(numeEchipaField);
		addEchipaLayout.addComponent(managerEchipaField);
		addEchipaLayout.addComponent(addEchipa);

		return addEchipaLayout;
	}

	public UserService getUserService()
	{
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context1 = ui.getApplicationContext();
		UserService bean = context1.getBean(UserService.class);
		return bean;
	}

	public EchipaService getEchipeService()
	{
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context1 = ui.getApplicationContext();
		EchipaService bean = context1.getBean(EchipaService.class);
		return bean;
	}

	private FunctiiService getFunctiiService() {
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context1 = ui.getApplicationContext();
		FunctiiService bean = context1.getBean(FunctiiService.class);
		return bean;
	}

}