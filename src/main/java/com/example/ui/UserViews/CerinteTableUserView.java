package com.example.ui.UserViews;

import com.example.AppUI;
import com.example.cerinte.TaskModel;
import com.example.cerinte.TaskService;
import com.example.echipe.EchipaService;
import com.example.functii.FunctiiModel;
import com.example.functii.FunctiiService;
import com.example.sprint.SprintModel;
import com.example.sprint.SprintService;
import com.example.user.UserModel;
import com.example.user.UserService;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;


public class CerinteTableUserView extends VerticalLayout {

	@Autowired
	UserService userService;
	@Autowired
	TaskService taskService;
	@Autowired
	EchipaService echipaService;
	@Autowired
	FunctiiService functiiService;
	@Autowired
	SprintService sprintService=getSprintService();

	private Label noelement = new Label("");
	Grid grid;
	TextField persoaneFilter;
	TextField status;
	TextField tip;
	TextField cerinteID;
	TextField cerintaNume;
	TextField timp;
	TextField sprint;
	Collection<TaskModel> tasks=new ArrayList<>() ;
	public CerinteTableUserView(String userID){

		userService= getUserService();
		taskService=getTaskService();
		echipaService=getEchipaService();
		functiiService=getfunctiiService();
		String echipa = userService.getUser(userID).getEchipa();

		List<TaskModel> allTasks = taskService.findAllByEchipa(echipa);
        for (TaskModel taskModel:allTasks)
		{
			if(taskModel.getEchipa().equals(echipa))
			tasks.add(taskModel);
		}
		addComponent(filtersLayout());
		addComponent(noelement);
		if(userService.getUser(userID).getScrumMaster()==true)
		{
			Grid gridLayout = createManagerGrid(tasks,echipa);
			addComponent(gridLayout);
			addComponent(addCerintaLayout(echipa));
		}
		else{
        	Grid gridLayout = createGrid(tasks,echipa,userService.getUser(userID).getNume()+" "+userService.getUser(userID).getPrenume());
			addComponent(gridLayout);
		}

	}

	public HorizontalLayout addCerintaLayout(String echipa)
	{
		HorizontalLayout addLayout=new HorizontalLayout();
		TextField idCerinte= new TextField("ID Cerinta");
		idCerinte.setId("id-cerinta-field-echipa-"+echipa);
		addLayout.addComponent(idCerinte);

		TextField descriereCerinte= new TextField("Descriere ");
		descriereCerinte.setId("descriere-cerinta-field-echipa-"+echipa);
		addLayout.addComponent(descriereCerinte);

		TextField timpCreinta= new TextField("Timp ");
		timpCreinta.setId("timp-cerinta-field-echipa-"+echipa);
		addLayout.addComponent(timpCreinta);

		VerticalLayout buttonLAyout=new VerticalLayout();
		buttonLAyout.addComponent(new Label("Adaugare cerinta"));
		Button add=new Button("Adaugare Cerinta");
		add.setId("button-addcerinate-"+echipa);
		add.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		add.setIcon(VaadinIcons.PLUS);
		buttonLAyout.addComponent(add);
		add.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				TaskModel newTask=new TaskModel();

					try{
						taskService.getTask(idCerinte.getValue());
						new Notification("ID cerinta deja existent", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
					}catch (NoSuchElementException e)
					{
						try{
							newTask.setID(idCerinte.getValue());
						}catch (Exception e1)
						{
							new Notification("ID cerinta nu este setat", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
						}
					}
				try{
					newTask.setDescriere(descriereCerinte.getValue());
				}catch (Exception e)
				{
					new Notification("Descriere cerinta nu este setata", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				}
				try{
					newTask.setTimp(timpCreinta.getValue());
				}catch (Exception e)
				{
					new Notification("Timpul acordat cerintei nu este setat", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				}
				if(newTask.getID()!=null&&newTask.getDescriere()!=null&&newTask.getTimp()!="0")
				{
					newTask.setEchipa(echipa);
					taskService.addTask(newTask);
					new Notification("Cerinta salvata", Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
					tasks.add(newTask);
					removeAllComponents();
					addComponent(createManagerGrid(tasks,echipa));
					addComponent(addCerintaLayout(echipa));
				}
				else {
					new Notification("Cerinta nu a fost salvata", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				}
			}
		});
		addLayout.addComponent(buttonLAyout);
		return addLayout;
	}
	public Grid createManagerGrid(Collection<TaskModel> tasks,String echipa )
	{
		// Create a grid bound to the container
		grid = new Grid(TaskModel.class);
		grid.setId("tabel-tasks-View");
		grid.setSelectionMode(Grid.SelectionMode.SINGLE);
		grid.setItems(tasks);
		grid.setColumns("ID","descriere","persoana","tip","timp","status","sprint");
		grid.setSizeFull();
		Binder<TaskModel> binder = grid.getEditor().getBinder();
		grid.getEditor().setEnabled(true);
		grid.getEditor().setBinder(binder);


		Slider vertslider = new Slider(0, 100);
		vertslider.setOrientation(SliderOrientation.HORIZONTAL);
		Grid.Column statusColumn = grid.getColumn("status");
		statusColumn.setEditorComponent(vertslider);

		ComboBox tipCerintaCombo=getFunctieComboBox();
		tipCerintaCombo.setId("tipCerinta-combo-"+grid.getId());
		Grid.Column functiiColumn = grid.getColumn("tip");
		functiiColumn.setEditorComponent(tipCerintaCombo);

		ComboBox sprintCerintaCombo=getSprintComboBox();
		sprintCerintaCombo.setId("sprintCerinta-combo-"+grid.getId());
		Grid.Column sprintColumn = grid.getColumn("sprint");
		sprintColumn.setEditorComponent(sprintCerintaCombo);

		ComboBox persoane=getPersoaneComboBox(echipa);
		persoane.setId("persoane-combo-"+grid+getId());
		Grid.Column echipeColumn = grid.getColumn("persoana");
		echipeColumn.setEditorComponent(persoane);

		TextField editTimp=new TextField();
		editTimp.setId("edit-timp-"+grid.getId());
		Grid.Column timpColumn = grid.getColumn("timp");
		timpColumn.setEditorComponent(editTimp);


		TextField editDescriere=new TextField();
		editTimp.setId("edit-descriere-"+grid.getId());
		Grid.Column descriereColumn = grid.getColumn("descriere");
		descriereColumn.setEditorComponent(editDescriere);

		grid.addColumn(person -> "Save",
				new ButtonRenderer(clickEvent -> {
					TaskModel item = (TaskModel) clickEvent.getItem();
					try {
						if (!item.getPersoana().equals(persoane.getValue().toString())) {
							item.setPersoana(persoane.getValue().toString());
						}
					}catch (Exception e)
					{

					}
					try {
						if (!item.getTip().toUpperCase().equals(tipCerintaCombo.getValue().toString().toUpperCase())) {
							item.setTip(tipCerintaCombo.getValue().toString().toUpperCase());
						}
					}catch (Exception e)
					{

					}
					try {
						if (!(item.getTimp().equals(editTimp.getValue()))) {
							item.setTimp(editTimp.getValue());
						}
					}catch (Exception e)
					{

					}
					try {
						if (!item.getDescriere().equals(editDescriere.getValue().toString())) {
							item.setDescriere(editDescriere.getValue().toString());
						}
					}catch (Exception e)
					{

					}
					try {
						if(sprintCerintaCombo.getValue().equals(item.getSprint()))
							item.setSprint(sprintCerintaCombo.getValue().toString());
					}catch (Exception e1)
					{

					}
					try {
							if (!(item.getStatus() == (vertslider.getValue()))) {
								item.setStatus(vertslider.getValue());
							}
						} catch (Exception e) {
						}
						taskService.updateTask(item, item.getID());

					taskService.updateTask(item,item.getID());

				},ValoTheme.BUTTON_FRIENDLY));

		grid.addColumn(person -> "Delete",
				new ButtonRenderer(clickEvent -> {
					tasks.remove(clickEvent.getItem());
					grid.setItems(tasks);
					TaskModel item = (TaskModel) clickEvent.getItem();
					taskService.deleteTask(item.getID());

				},ValoTheme.BUTTON_DANGER));

		grid.getEditor().setSaveCaption("save");
		grid.getEditor().setCancelCaption("cancel");
		return grid;
	}
	public Grid createGrid(Collection<TaskModel> tasks,String echipa,String userName)
	{
		// Create a grid bound to the container
		grid = new Grid(TaskModel.class);
		grid.setId("tabel-tasks-View");
		grid.setSelectionMode(Grid.SelectionMode.SINGLE);
		grid.setItems(tasks);
		grid.setColumns("ID","descriere","persoana","tip","timp","status","sprint");
		grid.setSizeFull();
		Binder<TaskModel> binder = grid.getEditor().getBinder();
		grid.getEditor().setEnabled(true);
		grid.getEditor().setBinder(binder);

		Slider vertslider = new Slider(0, 100);
		vertslider.setOrientation(SliderOrientation.HORIZONTAL);
		Grid.Column statusColumn = grid.getColumn("status");
		statusColumn.setEditorComponent(vertslider);


		grid.addColumn(person -> "Save",
				new ButtonRenderer(clickEvent -> {
					TaskModel item = (TaskModel) clickEvent.getItem();
                  if(item.getPersoana().equals(userName)) {
					  try {
						  if (!(item.getStatus() == (vertslider.getValue()))) {
							  item.setStatus(vertslider.getValue());
						  }
					  } catch (Exception e) {
					  }
					  taskService.updateTask(item, item.getID());
				  }
				  else
				  {
				  	new Notification("Nu aveti dreptul de a modifica decat statusul cerintelor dumneavoastra", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				  }
				},ValoTheme.BUTTON_FRIENDLY));

		grid.getEditor().setSaveCaption("save");
		grid.getEditor().setCancelCaption("cancel");
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

	private ComboBox getSprintComboBox() {
		ComboBox sprintComboBox = new ComboBox();
		sprintComboBox.setEnabled(true);
		Collection <String> sprintCollection=new ArrayList<>();
		for (SprintModel sprintModel:sprintService.getAllSprints())
		{
			sprintCollection.add(sprintModel.getNumarSprint());
		}
		try {
			sprintComboBox.setItems(sprintCollection);
		}catch (NullPointerException e)
		{
			sprintCollection.add("Fara Sprint");
		}
		return sprintComboBox;
	}
	private ComboBox getPersoaneComboBox(String echipa) {
		ComboBox persoaneComboBox = new ComboBox();
		persoaneComboBox.setEnabled(true);
		Collection <String> persoaneCollection=new ArrayList<>();
		for (UserModel userModel:userService.getAllUsers())
		{
			if(userModel.getEchipa().equals(echipa))
		    	persoaneCollection.add(userModel.getNume()+" "+userModel.getPrenume());
		}
		try {
			persoaneComboBox.setItems(persoaneCollection);
		}catch (NullPointerException e)
		{
			persoaneCollection.add("Fara persoane in echipa");
		}
		return persoaneComboBox;
	}

	public HorizontalLayout filtersLayout()
	{
		HorizontalLayout horizontalLayout=new HorizontalLayout();
		persoaneFilter = new TextField();
		persoaneFilter.setId("filtru-persoana");
		persoaneFilter.setPlaceholder("Persoana...");
		persoaneFilter.addValueChangeListener(this::onNameFilterTextChange);
		horizontalLayout.addComponent(persoaneFilter);

		cerinteID = new TextField();
		cerinteID.setId("filtru-cerinteID");
		cerinteID.setPlaceholder("ID Cerinta...");
		cerinteID.addValueChangeListener(this::onIDFilterTextChange);
		horizontalLayout.addComponent(cerinteID);

		cerintaNume = new TextField();
		cerintaNume.setId("filtru-descriere cerinta");
		cerintaNume.setPlaceholder("Descriere cerinta...");
		cerintaNume.addValueChangeListener(this::onCerintaFilterChange);
		horizontalLayout.addComponent(cerintaNume);

		timp = new TextField();
		timp.setId("filtru-timp");
		timp.setPlaceholder("Timp...");
		timp.addValueChangeListener(this::onTimpFilterChange);
		horizontalLayout.addComponent(timp);

		sprint = new TextField();
		sprint.setId("sprint-timp");
		sprint.setPlaceholder("Sprint...");
		sprint.addValueChangeListener(this::onSprintFilterChange);
		horizontalLayout.addComponent(sprint);

//		tip = new TextField();
//		tip.setId("filtru-tip");
//		tip.setPlaceholder("Tip...");
//		tip.addValueChangeListener(this::onTipFilterChange);
//		horizontalLayout.addComponent(tip);

		status = new TextField();
		status.setId("filtru-status");
		status.setPlaceholder("status...");
		status.addValueChangeListener(this::onStatusFilterChange);
		horizontalLayout.addComponent(status);

		return horizontalLayout;
	}
	private void onNameFilterTextChange(HasValue.ValueChangeEvent<String> event) {
		ListDataProvider<TaskModel> dataProvider = (ListDataProvider<TaskModel>) grid.getDataProvider();
		try
		{
			dataProvider.setFilter(TaskModel::getPersoana, s -> caseInsensitiveContains(s, event.getValue()));
		}catch (NoSuchElementException e)
		{
		 noelement.setValue("Nu exista in lista");
		}
	}

	private void onStatusFilterChange(HasValue.ValueChangeEvent<String> event) {
		ListDataProvider<TaskModel> dataProvider = (ListDataProvider<TaskModel>) grid.getDataProvider();
		try
		{
			dataProvider.setFilter(TaskModel::getStatus, s ->  Double.parseDouble(event.getValue())==s);
		}catch (NoSuchElementException e)
		{
			noelement.setValue("Nu exista in lista");
		}
	}

	private void onTipFilterChange(HasValue.ValueChangeEvent<String> event) {
		ListDataProvider<TaskModel> dataProvider = (ListDataProvider<TaskModel>) grid.getDataProvider();
		try
		{
			dataProvider.setFilter(TaskModel::getTip, s -> caseInsensitiveContains(s, event.getValue()));
		}catch (NoSuchElementException e)
		{
			noelement.setValue("Nu exista in lista");
		}
	}

	private void onTimpFilterChange(HasValue.ValueChangeEvent<String> event) {
		ListDataProvider<TaskModel> dataProvider = (ListDataProvider<TaskModel>) grid.getDataProvider();
		try
		{
			dataProvider.setFilter(TaskModel::getTimp, s -> s.equals(event.getValue()));
		}catch (NoSuchElementException e)
		{
			noelement.setValue("Nu exista in lista");
		}
	}

	private void onSprintFilterChange(HasValue.ValueChangeEvent<String> event) {
		ListDataProvider<TaskModel> dataProvider = (ListDataProvider<TaskModel>) grid.getDataProvider();
		try
		{
			dataProvider.setFilter(TaskModel::getSprint, s -> s.equals( event.getValue()));
		}catch (NoSuchElementException e)
		{
			noelement.setValue("Nu exista in lista");
		}
	}

	private void onCerintaFilterChange(HasValue.ValueChangeEvent<String> event) {
		ListDataProvider<TaskModel> dataProvider = (ListDataProvider<TaskModel>) grid.getDataProvider();
		try
		{
			dataProvider.setFilter(TaskModel::getDescriere, s -> caseInsensitiveContains(s, event.getValue()));
		}catch (NoSuchElementException e)
		{
			noelement.setValue("Nu exista in lista");
		}
	}

	private void onIDFilterTextChange(HasValue.ValueChangeEvent<String> event) {
		ListDataProvider<TaskModel> dataProvider = (ListDataProvider<TaskModel>) grid.getDataProvider();
		try
		{
			dataProvider.setFilter(TaskModel::getID, s -> caseInsensitiveContains(s, event.getValue()));
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
	public TaskService getTaskService()
	{
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context1 = ui.getApplicationContext();
		TaskService bean = context1.getBean(TaskService.class);
		return bean;
	}
	public EchipaService getEchipaService()
	{
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context1 = ui.getApplicationContext();
		EchipaService bean = context1.getBean(EchipaService.class);
		return bean;
	}
	public FunctiiService getfunctiiService()
	{
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context1 = ui.getApplicationContext();
		FunctiiService bean = context1.getBean(FunctiiService.class);
		return bean;
	}
	public SprintService getSprintService()
	{
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context1 = ui.getApplicationContext();
		SprintService bean = context1.getBean(SprintService.class);
		return bean;
	}
}