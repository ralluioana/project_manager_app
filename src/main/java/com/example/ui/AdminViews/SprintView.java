package com.example.ui.AdminViews;

import com.example.AppUI;
import com.example.cerinte.TaskModel;
import com.example.cerinte.TaskService;
import com.example.functii.FunctiiService;
import com.example.sprint.SprintModel;
import com.example.sprint.SprintService;
import com.example.user.UserModel;
import com.vaadin.annotations.Widgetset;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.GregorianCalendar;


/**
 * Created by Crisan on 12.06.2017.
 */
@Widgetset("AppWidgetset")
public class SprintView extends VerticalLayout {
    @Autowired
    SprintService sprintService=getSprintService();
    @Autowired
    TaskService taskService=getTasksService();
    public  SprintView(){
//        Calendar cal = new Calendar("My Calendar");
//        cal.setWidth("800px");
//        cal.setHeight("600px");
//        cal.setStartDate(new GregorianCalendar(2017, 1, 16, 13, 00, 00).getTime());
//        cal.setEndDate(new GregorianCalendar(2020, 2, 16, 13, 00, 00).getTime());
      addLayout();
    }
    public void addLayout(){
        HorizontalLayout horizontalLayout=new HorizontalLayout();
        horizontalLayout.addComponent(addSprintView());
        horizontalLayout.addComponent(sprintTable());
        addComponentsAndExpand(horizontalLayout);
        addComponentsAndExpand(cerinteTable());
    }
    public VerticalLayout sprintTable() {
        Collection<SprintModel> sprintModels=sprintService.getAllSprints();
        VerticalLayout verticalLayout=new VerticalLayout();
        Grid grid = new Grid(SprintModel.class);
        grid.setId("tabel-sprint-adminView");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(sprintModels);
        grid.setHeight("600px");
        grid.setWidth("600px");
        grid.setColumns("numarSprint","dataStart","dataEnd","nrZile");
        verticalLayout.addComponentsAndExpand(grid);
        return verticalLayout;
    }

    public VerticalLayout cerinteTable() {
        Collection<TaskModel> taskModels=taskService.getAllTasks();
        VerticalLayout verticalLayout=new VerticalLayout();
        Grid grid = new Grid(TaskModel.class);
        grid.setHeight("1000px");
        grid.setWidth("1000px");
        grid.setId("tabel-cerinte-adminView");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setColumns("ID","persoana","descriere","tip","timp","status","sprint","echipa");
        grid.setItems(taskModels);
        verticalLayout.addComponentsAndExpand(grid);
        return verticalLayout;
    }


    public VerticalLayout addSprintView()
    {
        VerticalLayout verticalLayout=new VerticalLayout();
        TextField sprintNumber=new TextField("Numar sprint");
        sprintNumber.setId("sprint-nr-field");
        TextField sprintDays=new TextField("Numar zile lucratoare sprint");
        sprintNumber.setId("sprint-nr-zile-lucratoare-field");
        DateTimeField sprintStartDate=new DateTimeField("Inceput sprint");
        sprintNumber.setId("sprint-date-start-field");
        DateTimeField sprintEndDate=new DateTimeField("Sfarsit sprint");
        sprintNumber.setId("sprint-date-end-field");
        Button addSprint= new Button("Adaugare Sprint");
        addSprint.setId("button-add-sprint");
        addSprint.setIcon(VaadinIcons.PLUS);
        addSprint.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        addSprint.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                SprintModel sprintModel=new SprintModel();
                if(sprintNumber.getValue()!=null)
                {
                    sprintModel.setNumarSprint(sprintNumber.getValue());
                    if(sprintDays.getValue()!=null)
                    {
                        sprintModel.setNrZile(Integer.parseInt(sprintDays.getValue()));
                        if(sprintStartDate.getValue()!=null)
                        {
                            sprintModel.setDataStart(sprintStartDate.getValue());

                                if(sprintEndDate.getValue()!=null)
                                {
                                    sprintModel.setDataEnd(sprintEndDate.getValue());
                                }else{
                                    new Notification("Trebuie sa introduceti ultima zi a sprintului", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
                                    sprintModel=new SprintModel();
                                }
                        }else{
                            new Notification("Trebuie sa introduceti prima zi a sprintului", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
                            sprintModel=new SprintModel();
                        }
                    }else{
                        new Notification("Trebuie sa introduceti numarul de zile lucratoare ale sprintului", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
                        sprintModel=new SprintModel();
                    }
                }
                else{
                    new Notification("Trebuie sa introduceti numarul sprintului", Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
                    sprintModel=new SprintModel();
                }
                if(sprintModel!=null)
                {
                    sprintService.addSprint(sprintModel);
                    new Notification("S-a salvat sprintul", Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
                   removeAllComponents();
                   addLayout();
                }
            }
        });
        verticalLayout.addComponent(sprintNumber);
        verticalLayout.addComponent(sprintDays);
        verticalLayout.addComponent(sprintStartDate);
        verticalLayout.addComponent(sprintEndDate);
        verticalLayout.addComponent(addSprint);
        return verticalLayout;
    }


    private SprintService getSprintService() {
        AppUI ui = (AppUI) UI.getCurrent();
        ApplicationContext context1 = ui.getApplicationContext();
        SprintService bean = context1.getBean(SprintService.class);
        return bean;
    }
    private TaskService getTasksService() {
        AppUI ui = (AppUI) UI.getCurrent();
        ApplicationContext context1 = ui.getApplicationContext();
        TaskService bean = context1.getBean(TaskService.class);
        return bean;
    }
}
