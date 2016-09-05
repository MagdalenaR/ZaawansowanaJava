package ZZPJ.Project;

import ZZPJ.Project.Model.Movie;
import ZZPJ.Project.Model.MovieBasic;
import ZZPJ.Project.Model.MovieDecorator;
import ZZPJ.Project.Model.MovieWithRating;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.util.List;

public class Charts {

    public void showChartFrame(String title, JFreeChart chart) {
        JFrame frame = new JFrame(title);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void createBarChart(String title, DefaultCategoryDataset dataset, String xLabel, String yLabel) {
        JFreeChart chart = ChartFactory.createBarChart(title,
                xLabel, yLabel, dataset, PlotOrientation.VERTICAL,
                true, true, false);
        showChartFrame(title, chart);
    }

    public void createPieChart(String title, DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
        showChartFrame(title, chart);
    }

    public void createMovieRatingValueBarChart(String title, List<Movie> data){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for(Movie movie : data){
            while(!(movie instanceof MovieWithRating) && !(movie instanceof MovieBasic)){
                movie = ((MovieDecorator)movie).getMovie();
            }
            Movie movie1 = movie;
            while(!(movie1 instanceof MovieBasic)){
                movie1 = ((MovieDecorator)movie1).getMovie();
            }
            if(movie instanceof MovieWithRating){
                dataset.setValue(((MovieWithRating)movie).getRate(), "Rating value", ((MovieBasic)movie1).getTitle());
            }
        }
        createBarChart(title, dataset, "Movie", "Rating");
    }
}
