package com.example.ui.UserViews;

/**
 * Created by Crisan on 23.06.2017.
 */
        import com.vaadin.addon.charts.Chart;
        import com.vaadin.addon.charts.PointClickEvent;
        import com.vaadin.addon.charts.PointClickListener;
        import com.vaadin.addon.charts.model.ChartType;
        import com.vaadin.addon.charts.model.Configuration;
        import com.vaadin.addon.charts.model.Cursor;
        import com.vaadin.addon.charts.model.DataLabels;
        import com.vaadin.addon.charts.model.DataSeries;
        import com.vaadin.addon.charts.model.DataSeriesItem;
        import com.vaadin.addon.charts.model.PlotOptionsPie;
        import com.vaadin.ui.Component;
        import com.vaadin.ui.Notification;
        import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PieChartTasksStatusUi extends VerticalLayout {



//    @Override
//    protected Component getChart() {
//
//        Component ret = createChart();
//        ret.setWidth("100%");
//        ret.setHeight("450px");
//        return ret;
//    }

    public PieChartTasksStatusUi(int newTask, int lessThenHalf,int moreThenHalf, int doneTask, int total)
    {
       addComponent( createChart( newTask,  lessThenHalf, moreThenHalf,  doneTask,  total));
    }
    public static Chart createChart(int newTask, int lessThenHalf,int moreThenHalf, int doneTask, int total) {
        Chart chart = new Chart(ChartType.PIE);

        Configuration conf = chart.getConfiguration();

        conf.setTitle("Status Cerinte");

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setCursor(Cursor.POINTER);
        DataLabels dataLabels = new DataLabels();
        dataLabels.setEnabled(true);
        dataLabels
                .setFormatter("'<b>'+ this.point.name +'</b>: '+ this.percentage +' %'");
        plotOptions.setDataLabels(dataLabels);
        conf.setPlotOptions(plotOptions);

        final DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("Cerinte noi", newTask*100.0/total));
        series.add(new DataSeriesItem("Cerinte mai putin de jumatate realizate", lessThenHalf*100/total));
        DataSeriesItem chrome = new DataSeriesItem("Cerinte peste jumatate realizate", moreThenHalf*100/total);
        chrome.setSliced(true);
        chrome.setSelected(true);
        series.add(chrome);
        series.add(new DataSeriesItem("Cerinte finalizate", doneTask*100/total));
        conf.setSeries(series);

        chart.addPointClickListener(new PointClickListener() {

            @Override
            public void onClick(PointClickEvent event) {
                Notification.show("Click: "
                        + series.get(event.getPointIndex()).getName());
            }
        });

        chart.drawChart(conf);

        return chart;
    }

}