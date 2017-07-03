package com.example.ui.AdminViews;

import com.example.AppUI;
import com.example.config.Functii;
import com.example.echipe.EchipaModel;
import com.example.echipe.EchipaService;
import com.example.functii.FunctiiService;
import com.example.user.UserModel;
import com.example.user.UserService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.*;


public class EchipeAdminView extends VerticalLayout {

	@Autowired
	UserService userService=getUserService();
	@Autowired
	EchipaService echipaService=getEchipeService();
	@Autowired
	FunctiiService functiiService=getFunctiiService();


	private Label noelement = new Label("");

	public EchipeAdminView(){

	addFullLayout();
	}
	public void addFullLayout()
	{

		Collection<String> echipe= new ArrayList<>();
		List<UserModel> allUsers = userService.getAllUsers();
		List<EchipaModel> allEchipe=echipaService.getAllEchipe();

		if(allEchipe==null)
		{
			Label nuExistaEchipe=new Label("NU exista echipe in baza de date");
			addComponent(nuExistaEchipe);
		}
		else {

			for (EchipaModel echipaModel : allEchipe) {
				if (!echipe.contains(echipaModel.getNumeEchipa())) {
					echipe.add(echipaModel.getNumeEchipa());
				}
			}
			for (String echipa : echipe) {
				String manager = "Fara manager";
				Collection<String> personss = new ArrayList<>();
				for (UserModel userModel : allUsers) {
					if (userModel.getEchipa().toUpperCase().equals( echipa.toUpperCase())) {
						personss.add(userModel.getNume() + " " + userModel.getPrenume());
						try {
							if (userModel.getScrumMaster() == true && userModel.getFunctie().equals("MANAGER")) {
								manager = userModel.getNume() + " " + userModel.getPrenume();
							}
						} catch (NullPointerException e) {
						}
					}
				}
				HorizontalLayout echipeEditLayout=new HorizontalLayout();
				HorizontalLayout echipeLayout = showEchipeLayout(echipa, manager, personss);
				echipeEditLayout.addComponent(echipeLayout);
				echipeEditLayout.addComponent(showAngajatiList(echipa));
				addComponent(echipeEditLayout);
			}
			addComponent(noelement);
		}
	}

	public HorizontalLayout showEchipeLayout(String echipa, String managerEchipa, Collection<String> angajatiCollection){
		HorizontalLayout horizontalLayout =new HorizontalLayout();
		horizontalLayout.setMargin(true);
		horizontalLayout.setStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		Label numechipa=new Label("ECHIPA: "+echipa);
		numechipa.setStyleName(ValoTheme.LABEL_COLORED);
		numechipa.setStyleName(ValoTheme.LABEL_BOLD,true);
		numechipa.setId("echipa-"+echipa);
		horizontalLayout.addComponent(numechipa);
		Button deleteEchipa=new Button("STERGERE");
		deleteEchipa.setId("delete-echipe-button-"+echipa);
		deleteEchipa.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		deleteEchipa.addStyleName(ValoTheme.BUTTON_DANGER);
		deleteEchipa.setIcon(VaadinIcons.CLIPBOARD_CROSS);
		deleteEchipa.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				echipaService.deleteEchipa(echipa);
				for (String angajat:angajatiCollection)
				{
					UserModel userByFullName = userService.getUserByFullName(angajat);
					userByFullName.setEchipa("");
					userService.updateUser(userByFullName,userByFullName.getUserName());
					new Notification("S-a sters echipa", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				}
			}
		});
		horizontalLayout.addComponent(deleteEchipa);
		Label numeManager=new Label("MANAGER: "+managerEchipa);
		numeManager.setId("manager-"+managerEchipa);
		horizontalLayout.addComponent(numeManager);
		ComboBox angajati = new ComboBox();
		angajati.setId("combobox-angajati-echipa-" + echipa);
		if(angajatiCollection.size()>0) {
			angajati.setItems(angajatiCollection);
			horizontalLayout.addComponent(angajati);
			Button setManager = new Button("Seteaza Manager");
			setManager.setId("set-manager-echipa-" + echipa);
			setManager.addStyleName(ValoTheme.BUTTON_FRIENDLY);
			setManager.addClickListener(new Button.ClickListener() {
				@Override
				public void buttonClick(Button.ClickEvent clickEvent) {
					Object managerOption = angajati.getValue();
					for (UserModel userModel : userService.getAllUsers()) {
						if ((userModel.getNume() + " " + userModel.getPrenume()).equals(managerOption) && managerEchipa != managerOption) {
							if (managerEchipa != null&&managerEchipa!="Fara manager") {
									UserModel oldManager = userService.getUserByFullName(managerEchipa);
									oldManager.setScrumMaster(false);
									oldManager.setFunctie("");
									userService.updateUser(oldManager, oldManager.getUserName());
							}
							EchipaModel echipa1 = echipaService.getEchipa(echipa);
							echipa1.setManager(managerOption.toString());
							echipaService.updateEchipa(echipa1,echipa1.getNumeEchipa());
							userModel.setScrumMaster(true);
							userModel.setFunctie(Functii.Functie.MANAGER.toString());
							userService.updateUser(userModel, userModel.getUserName());
							numeManager.setValue("MANAGER: " + managerOption.toString());
							new Notification("S-a setat managerul echipei", Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
							break;
						}
					}
				}
			});
			horizontalLayout.addComponent(setManager);
		}
		else
		{
			horizontalLayout.addComponent(new Label("NU exista angajati in aceasta echipa"));
		}
		return horizontalLayout;
	}

	public VerticalLayout showAngajatiList(String echipa)
	{
		VerticalLayout showAngajatiLayout=new VerticalLayout();
		Collection <String> angajatiList=new ArrayList<>();
		CheckBoxGroup <String> multiSelectAngajatiList=new CheckBoxGroup<>("Angajati");
		multiSelectAngajatiList.setId("chekcbox-angajati-list-"+echipa);
		for(UserModel userModel : userService.getAllUsers())
		{
			angajatiList.add(userModel.getNume()+" "+userModel.getPrenume());//+" echipa: "+userModel.getEchipa() +" functie: "+userModel.getFunctie());
		}
		multiSelectAngajatiList.setItems(angajatiList);
		HorizontalLayout buttonsLayout=new HorizontalLayout();
		Button showList =new Button();
		showList.setIcon(VaadinIcons.ARROW_DOWN);
		showList.addStyleName(ValoTheme.BUTTON_PRIMARY);
		showList.setId("show-angajati-list-"+echipa);
		showList.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
               if(showAngajatiLayout.getComponentCount()<2)
			   {
				   showAngajatiLayout.addComponent(multiSelectAngajatiList);
			   }
			}
		});
		buttonsLayout.addComponent(showList);
		Button hideList =new Button();
		hideList.setId("hide-angajati-list-"+echipa);
		hideList.setIcon(VaadinIcons.ARROW_UP);
		hideList.addStyleName(ValoTheme.BUTTON_PRIMARY);
		hideList.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				if(showAngajatiLayout.getComponentCount()>=2)
				{
					showAngajatiLayout.removeComponent(multiSelectAngajatiList);
				}
			}
		});
		buttonsLayout.addComponent(hideList);
		Button assignCheckedUsersButton=new Button("Asigneaza angajati");
		assignCheckedUsersButton.setId("asigneaza-angajati-"+echipa);
		assignCheckedUsersButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		assignCheckedUsersButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				if(showAngajatiLayout.getComponentCount()>=2) {
					Set<String> angajatiSelectati = multiSelectAngajatiList.getValue();
					for(String angajat:angajatiSelectati)
					{
						UserModel userByFullName = userService.getUserByFullName(angajat);
						userByFullName.setEchipa(echipa);
						userService.updateUser(userByFullName,userByFullName.getUserName());
						EchipaModel echipaModel=echipaService.getEchipa(echipa);
						if(echipaModel.getCoechipieri().equals(""))
						{
							echipaModel.setCoechipieri(angajat);
						}
						else {
							echipaModel.setCoechipieri(echipaModel.getCoechipieri()+", "+angajat);
						}
						echipaService.updateEchipa(echipaModel,echipaModel.getNumeEchipa());
						 new Notification("S-au asignat angajatii", Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
					}
				}
			}
		});

		buttonsLayout.addComponent(assignCheckedUsersButton);

		showAngajatiLayout.addComponent(buttonsLayout);

		return showAngajatiLayout;
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