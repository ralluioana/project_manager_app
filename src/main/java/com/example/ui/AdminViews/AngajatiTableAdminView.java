package com.example.ui.AdminViews;

import com.example.AppUI;
import com.example.config.Functii;
import com.example.echipe.EchipaModel;
import com.example.echipe.EchipaService;
import com.example.functii.FunctiiModel;
import com.example.functii.FunctiiService;
import com.example.user.UserModel;
import com.example.user.UserService;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.DetailsGenerator;
import com.vaadin.ui.components.grid.EditorSaveEvent;
import com.vaadin.ui.components.grid.EditorSaveListener;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.*;

import static org.aspectj.weaver.loadtime.definition.Definition.DeclareAnnotationKind.Field;


public class AngajatiTableAdminView extends VerticalLayout {

	@Autowired
	UserService userService;
	@Autowired
	FunctiiService functiiService;
	@Autowired
	EchipaService echipaService;

	private Label echipa = new Label("ANGAJATI");
	private Label noelement = new Label("");
	Grid grid;
	TextField nameFilter;
	TextField prenumeFilter;
	TextField echpaFilter;
	TextField functieFilter;

	public AngajatiTableAdminView(){

		echipa.setId("echipa-field");

		userService= getUserService();
		functiiService=getFunctiiService();
		echipaService=getEchipaService();
		addComponent(echipa);
		Collection<UserModel> personss=new ArrayList<>() ;
		List<UserModel> allUsers = userService.getAllUsers();
        for (UserModel userModel:allUsers)
		{
					personss.add(userModel);
		}
		Grid gridLayout = createGrid(personss);

		addComponent(filtersLayout());
		addComponent(noelement);
		addComponent(gridLayout);
	}


	public Grid createGrid(Collection<UserModel> personss)
	{
		// Create a grid bound to the container
		grid = new Grid(UserModel.class);
		grid.setId("tabel-echipa-adminView");
		grid.setSelectionMode(Grid.SelectionMode.SINGLE);
		grid.setItems(personss);
		grid.setColumns("nume","prenume","email","echipa","functie","orePeZi");
		grid.setSizeFull();
		Binder<UserModel> binder = grid.getEditor().getBinder();
		grid.getEditor().setEnabled(true);
		grid.getEditor().setBinder(binder);

		ComboBox fucntiiCombo=getFunctieComboBox();
		fucntiiCombo.setId("functii-combo-"+grid+getId());
		Grid.Column functiiColumn = grid.getColumn("functie");
		functiiColumn.setEditorComponent(fucntiiCombo);

		ComboBox echipeCombo=getEchipeComboBox();
		echipeCombo.setId("echipe-combo-"+grid+getId());
		Grid.Column echipeColumn = grid.getColumn("echipa");
		echipeColumn.setEditorComponent(echipeCombo);

		grid.addColumn(person -> "Save",
				new ButtonRenderer(clickEvent -> {
					UserModel item = (UserModel) clickEvent.getItem();
					try {
							if(item.getFunctie().toUpperCase().equals("SCRUM MASTER")||item.getFunctie().toUpperCase().equals("SCRUMMASTER")||item.getFunctie().toUpperCase().equals("MANAGER"))
								{
									item.setScrumMaster(true);
								}
									item.setFunctie(fucntiiCombo.getValue().toString().toUpperCase());
					}catch (Exception e)
					{

					}
					try {
						item.setEchipa(echipeCombo.getValue().toString().toUpperCase());
					}catch (Exception e)
					{

					}
					userService.updateUser(item,item.getUserName());
					personss.remove(item);
					grid.setItems(personss);

				},ValoTheme.BUTTON_FRIENDLY));

		grid.addColumn(person -> "Delete",
				new ButtonRenderer(clickEvent -> {
					personss.remove(clickEvent.getItem());
					grid.setItems(personss);
					UserModel item = (UserModel) clickEvent.getItem();
					userService.deleteUser(item.getUserName());

				},ValoTheme.BUTTON_DANGER));

//		grid.getEditor().setSaveCaption("save");
//		grid.getEditor().setCancelCaption("cancel");
		return grid;
	}

		private ComboBox getFunctieComboBox() {
			ComboBox functiiComboBox = new ComboBox();
			functiiComboBox.setEnabled(true);
			Collection <String> functiiCollection=new ArrayList<>();
			for (FunctiiModel functieModel:functiiService.getAllFunctii())
			{
				functiiCollection.add(functieModel.getNumeFunctie());
			}
			try {
				functiiComboBox.setItems(functiiCollection);
			}catch (NullPointerException e)
			{
				functiiCollection.add("Fara Functie");
			}
				return functiiComboBox;
		}

	private ComboBox getEchipeComboBox() {
		ComboBox echipeCombobox = new ComboBox();
		echipeCombobox.setEnabled(true);
		Collection <String> echipeCollection=new ArrayList<>();
		for (EchipaModel echipaModel:echipaService.getAllEchipe())
		{
			echipeCollection.add(echipaModel.getNumeEchipa());
		}
		try {
			echipeCombobox.setItems(echipeCollection);
		}catch (NullPointerException e)
		{
			echipeCollection.add("Fara Echipa");
		}
		return echipeCombobox;
	}


	public HorizontalLayout filtersLayout()
	{
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
		functieFilter.addValueChangeListener(this::onFunctieFilterChange);
		horizontalLayout.addComponent(functieFilter);

		echpaFilter = new TextField();
		echpaFilter.setId("filtru-echipa");
		echpaFilter.setPlaceholder("Echipa...");
		echpaFilter.addValueChangeListener(this::onEchipaFilterTextChange);
		horizontalLayout.addComponent(echpaFilter);
		return horizontalLayout;
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

	private void onFunctieFilterChange(HasValue.ValueChangeEvent<String> event) {
		ListDataProvider<UserModel> dataProvider = (ListDataProvider<UserModel>) grid.getDataProvider();
		try
		{
			dataProvider.setFilter(UserModel::getFunctie, s -> caseInsensitiveContains(s, event.getValue()));
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

	private void onEchipaFilterTextChange(HasValue.ValueChangeEvent<String> event) {
		ListDataProvider<UserModel> dataProvider = (ListDataProvider<UserModel>) grid.getDataProvider();
		try
		{
			dataProvider.setFilter(UserModel::getEchipa, s -> caseInsensitiveContains(s, event.getValue()));
		}catch (NoSuchElementException e)
		{
			noelement.setValue("Nu exista in lista");
		}
	}

	private Boolean caseInsensitiveContains(String where, String what) {
		return where.toLowerCase().contains(what.toLowerCase());
	}

	public UserService getUserService()
	{
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context1 = ui.getApplicationContext();
		UserService bean = context1.getBean(UserService.class);
		return bean;
	}
	public FunctiiService getFunctiiService()
	{
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context1 = ui.getApplicationContext();
		FunctiiService bean = context1.getBean(FunctiiService.class);
		return bean;
	}
	public EchipaService getEchipaService()
	{
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context1 = ui.getApplicationContext();
		EchipaService bean = context1.getBean(EchipaService.class);
		return bean;
	}
}