package com.example.ui.AdminViews;

import com.example.AppUI;
import com.example.cerinte.TaskModel;
import com.example.cerinte.TaskService;
import com.example.sprint.SprintModel;
import com.example.sprint.SprintService;
//import com.example.ui.ChartCapcitateEchipaUI;
//import com.example.ui.ChartCerinteUI;
import com.example.ui.ChartCapcitateEchipaUI;
import com.example.ui.ChartCerinteUI;
import com.vaadin.annotations.Widgetset;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Created by Crisan on 12.06.2017.
 */
@Widgetset("AppWidgetset")
public class StatisticiView extends VerticalLayout {
    @Autowired
    SprintService sprintService=getSprintService();
    @Autowired
    TaskService taskService=getTasksService();
    public StatisticiView(){
        Collection<String > sprints=new ArrayList<>();
        sprintService.getAllSprints().stream().forEach(sprintModel -> sprints.add(sprintModel.getNumarSprint()));
        addComponent(addSprintView(sprints));
    }

    public VerticalLayout addSprintView(Collection<String>sprints)
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
                       if(taskModel.getSprint().equals(sprintNumber.getValue()))
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
