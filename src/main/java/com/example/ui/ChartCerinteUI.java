package com.example.ui;

import com.example.cerinte.TaskModel;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.shared.ui.Connect;
import com.vaadin.ui.VerticalLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Crisan on 11.06.2017.
 */
@Theme("valo")
@Widgetset("AppWidgetset")
public class ChartCerinteUI extends VerticalLayout{
    int[] numarSarcini= new int[12];
    public ChartCerinteUI(Collection<TaskModel>taskModels)
    {
        Chart chart = new Chart(ChartType.COLUMN);
        chart.setWidth("400px");  // 100% by default
        chart.setHeight("300px"); // 400px by default
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Sarcinile Sprintului");
        conf.getChart().setType(ChartType.COLUMN);

         XAxis x = new XAxis();
        x.setCategories("0","1", "2", "3", "4", "5", "6", "7", "8",
                "9", "10");
        conf.addxAxis(x);

        YAxis y = new YAxis();
        y.setMin(0);
        conf.addyAxis(y);
        conf.getxAxis().setTitle("Zile pe sarcina");
        conf.getyAxis().setTitle("Numar sarcini");

        Legend legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setBackgroundColor(new SolidColor("#FFFFFF"));
        legend.setAlign(HorizontalAlign.LEFT);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setX(100);
        legend.setY(70);
        legend.setFloating(true);
        legend.setShadow(true);
        conf.setLegend(legend);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.x +': '+ this.y +' pcs'");
        conf.setTooltip(tooltip);

        PlotOptionsColumn plot = new PlotOptionsColumn();
        plot.setPointPadding(0.2);
        plot.setBorderWidth(0);

        Collection<String> echipe=new ArrayList<>();
        for(TaskModel taskModel:taskModels)
        {
            if(!echipe.contains(taskModel.getEchipa()))
            {
                echipe.add(taskModel.getEchipa());
            }
        }

        for(String  echipa:echipe) {

            for (TaskModel taskModel : taskModels) {
                if(taskModel.getEchipa().equals(echipa)) {
                    for(int i=0;i<=10;i++)
                    {
                        if(taskModel.getTimp().equals(Integer.toString(i))){
                            numarSarcini[i]++;
                        }
                  }
                }
            }

            conf.addSeries(new ListSeries(echipa,numarSarcini[0],numarSarcini[1],numarSarcini[2],numarSarcini[3],numarSarcini[4],
                    numarSarcini[5],numarSarcini[6],numarSarcini[7],numarSarcini[8],numarSarcini[9],numarSarcini[10]));
        }
        chart.drawChart(conf);
        addComponent(chart);
    }
}
