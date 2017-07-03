package com.example.ui.UserViews;

import com.example.AppUI;
import com.example.cerinte.TaskModel;
import com.example.cerinte.TaskService;
import com.example.sprint.SprintModel;
import com.example.sprint.SprintService;
import com.vaadin.annotations.Widgetset;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Collection;


/**
 * Created by Crisan on 12.06.2017.
 */
public class SprintsTableUserView extends VerticalLayout {
    @Autowired
    SprintService sprintService=getSprintService();
    @Autowired
    TaskService taskService=getTasksService();
    public SprintsTableUserView(){
       addComponent(sprintTable());

    }
    public VerticalLayout sprintTable() {
        Collection<SprintModel> sprintModels=sprintService.getAllSprints();
        VerticalLayout verticalLayout=new VerticalLayout();
        Grid grid = new Grid(SprintModel.class);
        grid.setId("tabel-sprint-adminView");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(sprintModels);

        grid.setColumns("numarSprint","dataStart","dataEnd","nrZile");
        grid.setSizeFull();
        verticalLayout.addComponentsAndExpand(grid);
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
