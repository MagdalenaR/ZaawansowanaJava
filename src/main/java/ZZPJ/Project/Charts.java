package ZZPJ.Project;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;

public class Charts {

    public void showChartFrame(String title, JFreeChart chart){
        JFrame frame = new JFrame(title);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void createBarChart(String title, DefaultCategoryDataset dataset, String xLabel, String yLabel){
        JFreeChart chart = ChartFactory.createBarChart(title,
                xLabel, yLabel, dataset, PlotOrientation.VERTICAL,
                true, true, false);
        showChartFrame(title,chart);
    }

    public void createPieChart(String title, DefaultPieDataset dataset){
        JFreeChart chart = ChartFactory.createPieChart(title,dataset,true,true,false);
        showChartFrame(title,chart);
    }
}
