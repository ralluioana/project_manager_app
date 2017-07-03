package com.example.ui.UserViews;

import com.example.AppUI;
import com.example.cerinte.TaskModel;
import com.example.cerinte.TaskService;
import com.example.sprint.SprintModel;
import com.example.sprint.SprintService;
import com.example.ui.ChartCapcitateAngajatiUI;
import com.example.ui.ChartCapcitateEchipaUI;
import com.example.ui.ChartCerinteUI;
import com.example.ui.SaveProfileDetaisListener;
import com.example.user.UserModel;
import com.example.user.UserService;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Widgetset("AppWidgetset")
public class PlanificareTableView extends VerticalLayout {

	@Autowired
	UserService userService;
	@Autowired
	SprintService sprintService=getSprintService();
	@Autowired
	TaskService taskService=getTaskServiceService();
	private Label echipa = new Label("ECHIPA :");
	private Label noelement = new Label("");
	Grid grid;
	TextField nameFilter;



	public PlanificareTableView(String userID){
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
		Collection<SprintModel> sprintModels=new ArrayList<>() ;
		sprintModels=sprintService.getAllSprints();
       // Create a grid bound to the container
		grid = new Grid(SprintModel.class);
        grid.setId("tabel-sprints");

		grid.setSelectionMode(Grid.SelectionMode.SINGLE);
		grid.setItems(sprintModels);
		grid.setHeight("400");
		grid.setWidth("600px");
		grid.setColumns("numarSprint","dataStart","dataEnd","nrZile");

		HorizontalLayout horizontalLayout=new HorizontalLayout();
		nameFilter = new TextField();
		nameFilter.setId("filtru-sprint");
		nameFilter.setPlaceholder("Numar Sprint...");
		nameFilter.addValueChangeListener(this::onNameFilterTextChange);
		horizontalLayout.addComponent(nameFilter);

		String numeEchipa=userService.getUser(userID).getEchipa();

		Collection<String > sprints=new ArrayList<>();
		addComponent(horizontalLayout);
		addComponent(noelement);
		addComponent(grid);
		sprintService.getAllSprints().stream().forEach(sprintModel -> sprints.add(sprintModel.getNumarSprint()));
	    addComponent(addSprintView(sprints,numeEchipa));
}

	public VerticalLayout addSprintView(Collection<String>sprints, String echipa)
	{
		VerticalLayout verticalLayout=new VerticalLayout();
		ComboBox sprintNumber=new ComboBox("Numar sprint");
		sprintNumber.setId("sprint-nr-combobox");
		sprintNumber.setItems(sprints);

		Button setSprint= new Button("Afisare statistici Sprint");
		setSprint.setId("button-Afisare-statistici-sprint");
		setSprint.setIcon(VaadinIcons.PLUS);
		setSprint.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		setSprint.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				String sprintNr="";
				Collection<TaskModel> tasks=new ArrayList<>();
				if(sprintNumber.getValue()!=null)
				{
					for(TaskModel taskModel:taskService.getAllTasks())
					{
						if(taskModel.getSprint().equals(sprintNumber.getValue())&& taskModel.getEchipa().equals(echipa))
						{
							tasks.add(taskModel);
						}
						sprintNr=sprintNumber.getValue().toString();
					}
				}
				if(!tasks.isEmpty())
				{
					HorizontalLayout horizontalLayout=new HorizontalLayout();
                   ChartCerinteUI chartTasks= new ChartCerinteUI(tasks);
                   horizontalLayout.addComponent(chartTasks);
					for(SprintModel sprintModel:sprintService.getAllSprints()) {
						if(sprintModel.getNumarSprint().equals(sprintNr)) {
                           ChartCapcitateEchipaUI chartCapcitateEchipaUI = new ChartCapcitateEchipaUI(tasks,sprintModel);
                           horizontalLayout.addComponent(chartCapcitateEchipaUI);
							addComponent(tasksCharts(tasks,sprintModel));
						}
					}
					addComponent(horizontalLayout);
				}
				else {
					new Notification("Nu sunt sarcini asignate asignate acestui sprint", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
				}
			}
		});

		verticalLayout.addComponent(sprintNumber);
		verticalLayout.addComponent(setSprint);
		return verticalLayout;
	}

	public HorizontalLayout tasksCharts(Collection<TaskModel> tasks,SprintModel sprintModel)
	{
		HorizontalLayout horizontalLayout= new HorizontalLayout();
	 ChartCapcitateAngajatiUI chartCapacitateAngajatiUI= new  	ChartCapcitateAngajatiUI(tasks, sprintModel);
	 horizontalLayout.addComponent(chartCapacitateAngajatiUI);
	 int newTask=0, doneTask=0, lessThanHalf=0, moreThanHalf=0,total=0;
	 for(TaskModel taskModel:tasks)
	 {
	 	if(taskModel.getStatus()<=10)
	 		newTask++;
	 	if(taskModel.getStatus()>10&&taskModel.getStatus()<50)
	 		lessThanHalf++;
	 	if(taskModel.getStatus()>=50&&taskModel.getStatus()<90)
	 		moreThanHalf++;
		 if(taskModel.getStatus()>=90)
		 	doneTask++;
		 total++;
	 }
PieChartTasksStatusUi pieChartTasksStatusUi=new PieChartTasksStatusUi(newTask,lessThanHalf,moreThanHalf,doneTask,total);

horizontalLayout.addComponent(pieChartTasksStatusUi);
return horizontalLayout;
	}

	private void onNameFilterTextChange(HasValue.ValueChangeEvent<String> event) {
		ListDataProvider<SprintModel> dataProvider = (ListDataProvider<SprintModel>) grid.getDataProvider();
		try
		{
			dataProvider.setFilter(SprintModel::getNumarSprint, s -> caseInsensitiveContains(s, event.getValue()));
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
	public SprintService getSprintService()
	{
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context1 = ui.getApplicationContext();
		SprintService bean = context1.getBean(SprintService.class);
		return bean;
	}
	public TaskService getTaskServiceService()
	{
		AppUI ui = (AppUI) UI.getCurrent();
		ApplicationContext context1 = ui.getApplicationContext();
		TaskService bean = context1.getBean(TaskService.class);
		return bean;
	}
}