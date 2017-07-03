package com.example.ui.AdminViews;

import com.example.AppUI;
import com.example.ui.EditProfileForm;
import com.example.ui.SaveProfileDetaisListener;
import com.example.ui.UserViews.ClientMenu;
import com.vaadin.annotations.Widgetset;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.context.ApplicationContext;
@Widgetset("AppWidgetset")
public class AdminMenu extends VerticalLayout {

	final String profil="PROFIL";
	final String angajati ="ANGAJATI";
	final String echipe ="ECHIPE";
	final String edit ="EDITARE";
	final String planificare ="PLANIFICARE";
	final String statistici ="STATISTICI";
	final String help ="HELP";
	private MenuBar clientmenu = new MenuBar();

	public AdminMenu(String userID){
		clientmenu.setId("admin-meniu");
		addComponent(clientmenu);
		MenuBar.Command mycommand = new MenuBar.Command() {
			MenuBar.MenuItem previous = null;

			public void menuSelected(MenuBar.MenuItem selectedItem) {

				switch (selectedItem.getText())
				{
					case profil:{
						removeAllComponents();
						addComponent(clientmenu);
						EditProfileForm editProfileForm =new EditProfileForm(userID);
						addComponent(editProfileForm);

					}
					break;

					case angajati:{
						removeAllComponents();
						addComponent(clientmenu);
						AngajatiTableAdminView angajatiTableAdminView=new AngajatiTableAdminView();
						addComponent(angajatiTableAdminView);
					}
					break;
					case echipe:{
						removeAllComponents();
						addComponent(clientmenu);
						EchipeAdminView echipeAdminView=new EchipeAdminView();
						addComponent(echipeAdminView);

					}
					break;
					case edit:{
						removeAllComponents();
						addComponent(clientmenu);
						EditResourcesAdminView editResourcesAdminView=new EditResourcesAdminView();
						addComponent(editResourcesAdminView);

					}
					break;
					case planificare:{
						removeAllComponents();
						addComponent(clientmenu);
						SprintView sprintView=new SprintView();
						addComponent(sprintView);
					}
					break;
					case statistici:{
						removeAllComponents();
						addComponent(clientmenu);
						StatisticiView statisticiView=new StatisticiView();
						addComponent(statisticiView);
					}
					break;
					case help:{
						removeAllComponents();
						addComponent(clientmenu);
						addComponent(helpViewAdmin());
						addComponent(ClientMenu.helpView());
					}
					break;

				}

				if (previous != null)
					previous.setStyleName(null);
				selectedItem.setStyleName("highlight");
				previous = selectedItem;
			}
		};
		 MenuBar.MenuItem profil = clientmenu.addItem( "PROFIL", VaadinIcons.MALE, mycommand);
		 MenuBar.MenuItem clienti = clientmenu.addItem("ANGAJATI", VaadinIcons.USERS, mycommand);
		 MenuBar.MenuItem transportatori = clientmenu.addItem("ECHIPE",VaadinIcons.GROUP, mycommand);
		MenuBar.MenuItem editare = clientmenu.addItem("EDITARE",VaadinIcons.EDIT, mycommand);
		 MenuBar.MenuItem curse = clientmenu.addItem("PLANIFICARE",VaadinIcons.CALENDAR, mycommand);
		MenuBar.MenuItem statistici = clientmenu.addItem("STATISTICI",VaadinIcons.CHART, mycommand);
		MenuBar.MenuItem help = clientmenu.addItem("HELP",VaadinIcons.HAND, mycommand);

	}

	public static VerticalLayout helpViewAdmin()
	{
		VerticalLayout verticalLayout=new VerticalLayout();
		Label label=new Label("<HTML>\n" +
				"<HEAD>\n" +
				"<H1><font color=\"blue\">DREPTURI ADMINISTRATOR</font></H1>\n" +
				"</HEAD>\n" +
				"</HTML>", ContentMode.HTML );
		label.setStyleName(ValoTheme.LABEL_BOLD);

		Label labelPlanificareText=new Label("<HTML>\n" +	 "<BODY BGCOLOR=\"grey\">\n" +
				"<HR>\n"  +
				"Trimite-ti email administratorului <a href=\"mailto:admin.admin@yourcompany.com\">\n" +
				"admin.admin@yourcompany.com</a>.\n" +
				"<P> Administratorul poate sa vizualizeze toti angajati si toate datele acestora\n" +
				"<P> Administratorul trebuie sa creeze si sa editeze echipele si functiile din sectiunea \"EDITARE\"\n"+
				"<P> Administratorul este singurul care poate sa atribuie funtiile si echipele angajatilor \n"+
				"<P> Cerintele echipelor sunt create si asignate angajatilor si pe sprint de catre angajatii cu functie de conducere\n"+
				"<P> Administratorul asigneaza manageri echipelor si poate doar vizualiza cerintele pe care aceastia le creaza\n"+
				"<P> Administratorul poate sa creeze sprinturi si sa le modifice in sectiunea de planificare\n"+
				"<P> In sectiune de statistici administratorul poate sa vada capacitatea tuturor echipelor pe un sprint selectat\n"+
				"<P> Graficele pe care administratorul le vede sunt calculate pe sprint pentru toti angajatii din toate echipele\n"+
				"<HR>\n" + "</BODY>\n" + "</HTML>", ContentMode.HTML);
		labelPlanificareText.setVisible(false);

		Button extendArrow2=new Button();
		extendArrow2.setIcon(VaadinIcons.ARROW_DOWN);
		extendArrow2.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		extendArrow2.setId("show-admin-help");
		extendArrow2.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				labelPlanificareText.setVisible(true);
			}
		});
		Button hideArrow2=new Button();
		hideArrow2.setIcon(VaadinIcons.ARROW_UP);
		hideArrow2.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		hideArrow2.setId("hide-admin-help");
		hideArrow2.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				labelPlanificareText.setVisible(false);
			}
		});
		HorizontalLayout buttonsLayout2=new HorizontalLayout();
		buttonsLayout2.addComponent(label);
		buttonsLayout2.addComponent(extendArrow2);
		buttonsLayout2.addComponent(hideArrow2);
		verticalLayout.addComponent(buttonsLayout2);
		verticalLayout.addComponent(labelPlanificareText);





		// "<CENTER><IMG SRC=\"img-header.jpg\" ALIGN=\"BOTTOM\"> </CENTER>\n"
		label.setId("help-view-admin");

		return verticalLayout;
	}

	public SaveProfileDetaisListener getSaveFormListener(){
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context = ui.getApplicationContext();
		return context.getBean(SaveProfileDetaisListener.class);
	}

}