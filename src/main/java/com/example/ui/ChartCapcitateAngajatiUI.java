package com.example.ui;

import com.example.AppUI;
import com.example.cerinte.TaskModel;
import com.example.sprint.SprintModel;
import com.example.user.UserModel;
import com.example.user.UserService;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Crisan on 11.06.2017.
 */
@Theme("valo")
@Widgetset("AppWidgetset")
public class ChartCapcitateAngajatiUI extends VerticalLayout{
    @Autowired
    UserService userService=getfunctiiService();
    String[] AngajatiList=new  String[1000];
    public ChartCapcitateAngajatiUI(Collection<TaskModel>taskModels, SprintModel sprintModel)
    {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.setWidth("400px");  // 100% by default
        chart.setHeight("300px"); // 400px by default
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Capcitatea echipelor pe Sprintului");
        conf.getChart().setType(ChartType.COLUMN);

        Collection<String > angajatiCollection= new ArrayList<>();
        for(TaskModel taskModel:taskModels)
        {
            if(!angajatiCollection.contains(taskModel.getPersoana()))
            {
                angajatiCollection.add(taskModel.getPersoana());
            }
        }
        int i=0;
        for(String angajat:angajatiCollection)
        {
            AngajatiList[i]=angajat;
            i++;
        }
        XAxis x = new XAxis();
        x.setCategories(AngajatiList);
        x.setMin(0);
       conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setMin(0);
        conf.addyAxis(y);
        conf.getxAxis().setTitle("Angajati");
        conf.getyAxis().setTitle("Timp[ore]");


        Legend legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setBackgroundColor(new SolidColor("#EEEEEE"));
        legend.setAlign(HorizontalAlign.LEFT);
        legend.setVerticalAlign(VerticalAlign.TOP);
//        legend.setX(200);
//        legend.setY(0);
        legend.setFloating(true);
        legend.setShadow(true);
        conf.setLegend(legend);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.x +': '+ this.y +' h'");
        conf.setTooltip(tooltip);

        PlotOptionsColumn plot = new PlotOptionsColumn();
        plot.setPointPadding(0.2);
        plot.setBorderWidth(0);
        Collection<Number> timpNecesarSarcinilor=new ArrayList<>();
        Collection<Number> capacitateAngajat=new ArrayList<>();
        for( i=0;i<angajatiCollection.size();i++)
        {
            int timpSarcini=0;
            for(TaskModel taskModel:taskModels)
            {
                if(AngajatiList[i].equals(taskModel.getPersoana()))
                {
                    timpSarcini=timpSarcini+Integer.parseInt(taskModel.getTimp())*6;
                }
            }
         timpNecesarSarcinilor.add(timpSarcini);

            int capacitate=0;
            for(UserModel userModel:userService.getAllUsers())
            {
                if((userModel.getNume()+" "+userModel.getPrenume()).equals(AngajatiList[i]))
                {
                    capacitate=capacitate+userModel.getOrePeZi()*sprintModel.getNrZile();
                }
            }
            capacitateAngajat.add(capacitate);
        }

        conf.addSeries(new ListSeries("Timp necesare sarcinilor",timpNecesarSarcinilor));
        conf.addSeries(new ListSeries("Capacitate angajat",capacitateAngajat));

        chart.drawChart(conf);
        addComponent(chart);
    }

    public UserService getfunctiiService()
    {
        AppUI ui = (AppUI) UI.getCurrent();
        ApplicationContext context1 = ui.getApplicationContext();
        UserService bean = context1.getBean(UserService.class);
        return bean;
    }

}
