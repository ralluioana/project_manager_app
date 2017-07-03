package com.example.ui.UserViews;

import com.example.AppUI;
import com.example.ui.EditProfileForm;
import com.example.ui.SaveProfileDetaisListener;
import com.vaadin.annotations.Widgetset;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.context.ApplicationContext;
@Widgetset("AppWidgetset")
public class ClientMenu extends VerticalLayout {

	final String profil="PROFIL";
	final String echipa ="ECHIPA";
	final String tasks="CERINTE";
	final String planificare ="PLANIFICARE";
	final String help ="HELP";
	private MenuBar clientmenu = new MenuBar();

	public ClientMenu(String userID){
		clientmenu.setId("client-meniu");
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

					case echipa:{
						removeAllComponents();
						addComponent(clientmenu);
						EchipaTable echipaTable=new EchipaTable(userID);
						addComponent(echipaTable);
					}
					break;
					case tasks:{
						removeAllComponents();
						addComponent(clientmenu);
						CerinteTableUserView cerinteTableUserView=new CerinteTableUserView(userID);
						addComponent(cerinteTableUserView);
					}
					break;

					case planificare:{
						removeAllComponents();
						addComponent(clientmenu);
						PlanificareTableView sprintsTableUserView=new PlanificareTableView(userID);
						addComponentsAndExpand(sprintsTableUserView);
					}
					break;
					case help:{
						removeAllComponents();
						addComponent(clientmenu);
						addComponent(helpView());
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
		 MenuBar.MenuItem clienti = clientmenu.addItem("ECHIPA", VaadinIcons.USERS, mycommand);
		 MenuBar.MenuItem transportatori = clientmenu.addItem("CERINTE",VaadinIcons.TASKS, mycommand);
		 MenuBar.MenuItem curse = clientmenu.addItem("PLANIFICARE",VaadinIcons.CALENDAR, mycommand);
		MenuBar.MenuItem felp = clientmenu.addItem("HELP",VaadinIcons.HAND, mycommand);

	}
 public static VerticalLayout helpView()
 {
	 VerticalLayout verticalLayout=new VerticalLayout();
	 Label label=new Label("<HTML>\n" +
			 "<HEAD>\n" +
			 "<H1><font color=\"blue\">DESPRE APLICATIE</font></H1>\n" +
			 "</HEAD>\n" +			 "<HR>\n" +
			 "<BODY BGCOLOR=\"FFFFFF\">\n" +
			 "<a href=\"http://somegreatsite.com\">Link </a>\n" +
			 "spre un site cu documentatia aplicatiei.\n"+
			 "</BODY>\n" +			 "<HR>\n" +
			 "</HTML>",ContentMode.HTML );
	 label.setStyleName(ValoTheme.LABEL_BOLD);
	 VerticalLayout verticalLayout1=new VerticalLayout();
	 Label labelEditareaProfilului=new Label("<HTML>\n" +	 "<BODY BGCOLOR=\"FFFFEE\">\n" +
			 "<H2>Editarea profilului</H2>\n" +
			  "</BODY>\n" + "</HTML>", ContentMode.HTML);
	 Label labelEditareaProfiluluiText=new Label("<HTML>\n" +	 "<BODY BGCOLOR=\"FFFFFF\">\n" +
			 "<HR>\n" +
			 "Trimite-ti email administratorului <a href=\"mailto:admin.admin@yourcompany.com\">\n" +
			 "admin.admin@yourcompany.com</a>.\n" +
			 "<P> Angajatul poate sa si modifice profilul prin deschiderea optiunii \"PROFIL\" din meniu\n" +
			 "<P> Angajatul nu poate sa isi modifice echipa si functia, lucruri care pot fi setate doar de administrator\n"+
			 "<HR>\n" + "</BODY>\n" + "</HTML>", ContentMode.HTML);
	 labelEditareaProfiluluiText.setVisible(false);

	 Button extendArrow=new Button();
	 extendArrow.setIcon(VaadinIcons.ARROW_DOWN);
	 extendArrow.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
	 extendArrow.setId("show-editare-prfil-help");
	 extendArrow.addClickListener(new Button.ClickListener() {
		 @Override
		 public void buttonClick(Button.ClickEvent clickEvent) {
		   labelEditareaProfiluluiText.setVisible(true);
		 }
	 });
	 Button hideArrow=new Button();
	 hideArrow.setIcon(VaadinIcons.ARROW_UP);
	 hideArrow.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
	 hideArrow.setId("hide-editare-prfil-help");
	 hideArrow.addClickListener(new Button.ClickListener() {
		 @Override
		 public void buttonClick(Button.ClickEvent clickEvent) {
			 labelEditareaProfiluluiText.setVisible(false);
		 }
	 });
	 HorizontalLayout buttonsLayout=new HorizontalLayout();
	 buttonsLayout.addComponent(labelEditareaProfilului);
	 buttonsLayout.addComponent(extendArrow);
	 buttonsLayout.addComponent(hideArrow);
	 verticalLayout1.addComponent(buttonsLayout);
	 verticalLayout1.addComponent(labelEditareaProfiluluiText);



	 VerticalLayout verticalLayout2=new VerticalLayout();
	 Label labelVizualizareEchipa=new Label("<HTML>\n" +	 "<BODY BGCOLOR=\"FFFFFF\">\n" +
			"<H2>Vizualizare Echipa</H2>\n" +
			 "</BODY>\n" + "</HTML>", ContentMode.HTML);
	 Label labelVizualizareEchipaText=new Label("<HTML>\n" +	 "<BODY BGCOLOR=\"FFFFFF\">\n" +
			 "<HR>\n" +
			 "Trimite-ti email administratorului <a href=\"mailto:admin.admin@yourcompany.com\">\n" +
			 "admin.admin@yourcompany.com</a>.\n" +
			 "<P> Angajatul poate sa vada datele membrilor echipei din care face parte prin deschiderea optiunii \"ECHIPA\" din meniu\n" +
			 "<P> Angajatul nu poate sa isi modifice datele, lucruri care pot fi setate doar de administrator\n"+
			 "<P> Angajatul nu poate sa vada vre-un alt angajat atata timp cat nu are o echipa asignata\n"+
			 "<HR>\n" +  "</BODY>\n" +"</HTML>", ContentMode.HTML);

	 labelVizualizareEchipaText.setVisible(false);

	 Button extendArrow1=new Button();
	 extendArrow1.setIcon(VaadinIcons.ARROW_DOWN);
	 extendArrow1.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
	 extendArrow1.setId("show-echipa-help");
	 extendArrow1.addClickListener(new Button.ClickListener() {
		 @Override
		 public void buttonClick(Button.ClickEvent clickEvent) {
			 labelVizualizareEchipaText.setVisible(true);
		 }
	 });
	 Button hideArrow1=new Button();
	 hideArrow1.setIcon(VaadinIcons.ARROW_UP);
	 hideArrow1.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
	 hideArrow1.setId("hide-echipa-help");
	 hideArrow1.addClickListener(new Button.ClickListener() {
		 @Override
		 public void buttonClick(Button.ClickEvent clickEvent) {
			 labelVizualizareEchipaText.setVisible(false);
		 }
	 });
	 HorizontalLayout buttonsLayout1=new HorizontalLayout();
	 buttonsLayout1.addComponent(labelVizualizareEchipa);
	 buttonsLayout1.addComponent(extendArrow1);
	 buttonsLayout1.addComponent(hideArrow1);
	 verticalLayout2.addComponent(buttonsLayout1);
	 verticalLayout2.addComponent(labelVizualizareEchipaText);


VerticalLayout verticalLayout3=new VerticalLayout();
	 Label labelPlanificare=new Label("<HTML>\n" +	 "<BODY BGCOLOR=\"FFFFFF\">\n" +
			  "<H2>Vizualizare Planificare</H2>\n" +
			 "</BODY>\n" + "</HTML>", ContentMode.HTML);

	 Label labelPlanificareText=new Label("<HTML>\n" +	 "<BODY BGCOLOR=\"grey\">\n" +
			 "<HR>\n"  +
			 "Trimite-ti email administratorului <a href=\"mailto:admin.admin@yourcompany.com\">\n" +
			 "admin.admin@yourcompany.com</a>.\n" +
			 "<P> Angajatul poate sa vada planificarea pe sprinturi prin deschiderea optiunii \"PLANIFICARE\" din meniu\n" +
			 "<P> Angajatul poate sa vizualizeze tot in aceasta sectiune graficele echipei\n"+
			 "<P> Graficele sunt calculate in functie de sarcinile echipei si capacitatea de munca a acesteia \n"+
			 "<P> Graficele sunt afisate prin selectarea sprintului pe care aceste se vor baza\n"+
			 "<HR>\n" + "</BODY>\n" + "</HTML>", ContentMode.HTML);
	 labelPlanificareText.setVisible(false);

	 Button extendArrow2=new Button();
	 extendArrow2.setIcon(VaadinIcons.ARROW_DOWN);
	 extendArrow2.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
	 extendArrow2.setId("show-planificare-help");
	 extendArrow2.addClickListener(new Button.ClickListener() {
		 @Override
		 public void buttonClick(Button.ClickEvent clickEvent) {
			 labelPlanificareText.setVisible(true);
		 }
	 });
	 Button hideArrow2=new Button();
	 hideArrow2.setIcon(VaadinIcons.ARROW_UP);
	 hideArrow2.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
	 hideArrow2.setId("hide-planificare-help");
	 hideArrow2.addClickListener(new Button.ClickListener() {
		 @Override
		 public void buttonClick(Button.ClickEvent clickEvent) {
			 labelPlanificareText.setVisible(false);
		 }
	 });
	 HorizontalLayout buttonsLayout2=new HorizontalLayout();
	 buttonsLayout2.addComponent(labelPlanificare);
	 buttonsLayout2.addComponent(extendArrow2);
	 buttonsLayout2.addComponent(hideArrow2);
	 verticalLayout3.addComponent(buttonsLayout2);
	 verticalLayout3.addComponent(labelPlanificareText);


	 VerticalLayout verticalLayout4=new VerticalLayout();
	 Label labelCerinte=new Label("<HTML>\n" +	 "<BODY BGCOLOR=\"EEEEEE\">\n" +
			 "<H2>Vizualizare Cerinte</H2>\n" +
			 "</BODY>\n" + "</HTML>", ContentMode.HTML);

	 Label labelCerinteText=new Label("<HTML>\n" +	 "<BODY BGCOLOR=\"FFFFFF\">\n" +
			 "<HR>\n"  +
			 "Trimite-ti email administratorului <a href=\"mailto:admin.admin@yourcompany.com\">\n" +
			 "admin.admin@yourcompany.com</a>.\n" +
			 "<P> Angajatul poate sa vada cerintele echipei sale prin deschiderea optiunii \"CERINTE\" din meniu\n" +
			 "<P> Angajatul poate sa modifice doar statusul propriilor cerinte\n"+
			 "<P> Angajatii cu functie de conducere precum MANAGER sau SCRUM MASTER pot sa aduge si sa modifice cerintele echipei lor \n"+
			 "<P> Functiile pot fi asignate doar de administrator\n"+
			 "<HR>\n" + "</BODY>\n" + "</HTML>", ContentMode.HTML);
	 labelCerinteText.setVisible(false);

	 Button extendArrow3=new Button();
	 extendArrow3.setIcon(VaadinIcons.ARROW_DOWN);
	 extendArrow3.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
	 extendArrow3.setId("show-cerinte-help");
	 extendArrow3.addClickListener(new Button.ClickListener() {
		 @Override
		 public void buttonClick(Button.ClickEvent clickEvent) {
			 labelCerinteText.setVisible(true);
		 }
	 });
	 Button hideArrow3=new Button();
	 hideArrow3.setIcon(VaadinIcons.ARROW_UP);
	 hideArrow3.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
	 hideArrow3.setId("hide-cerinte-help");
	 hideArrow3.addClickListener(new Button.ClickListener() {
		 @Override
		 public void buttonClick(Button.ClickEvent clickEvent) {
			 labelCerinteText.setVisible(false);
		 }
	 });
	 HorizontalLayout buttonsLayout3=new HorizontalLayout();
	 buttonsLayout3.addComponent(labelCerinte);
	 buttonsLayout3.addComponent(extendArrow3);
	 buttonsLayout3.addComponent(hideArrow3);
	 verticalLayout3.addComponent(buttonsLayout3);
	 verticalLayout3.addComponent(labelCerinteText);



	 // "<CENTER><IMG SRC=\"img-header.jpg\" ALIGN=\"BOTTOM\"> </CENTER>\n"
	 label.setId("help-view");
	 verticalLayout.addComponent(label);
	 verticalLayout.addComponent(verticalLayout1);
	 verticalLayout.addComponent(verticalLayout2);
	 verticalLayout.addComponent(verticalLayout3);
	 verticalLayout.addComponent(verticalLayout4);

	 return verticalLayout;
 }

	public SaveProfileDetaisListener getSaveFormListener(){
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context = ui.getApplicationContext();
		return context.getBean(SaveProfileDetaisListener.class);
	}


}