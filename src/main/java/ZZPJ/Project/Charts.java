package ZZPJ.Project;

import ZZPJ.Project.Model.Movie;
import ZZPJ.Project.Model.MovieBasic;
import ZZPJ.Project.Model.MovieDecorator;
import ZZPJ.Project.Model.MovieWithRating;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.util.List;

public class Charts {

    /**
     * Displays chart in new frame.
     *
     * @param title - chart title
     * @param chart - chart to display
     */
    public void showChartFrame(String title, JFreeChart chart) {
        JFrame frame = new JFrame(title);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Creates bar chart witch given data and display it on new frame.
     *
     * @param title   - chart title
     * @param dataset - dataset for the chart
     * @param xLabel  - the label for the x axis
     * @param yLabel  - the label for the y axis
     */
    public void createBarChart(String title, DefaultCategoryDataset dataset, String xLabel, String yLabel) {
        JFreeChart chart = ChartFactory.createBarChart(title,
                xLabel, yLabel, dataset, PlotOrientation.VERTICAL,
                true, true, false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        showChartFrame(title, chart);
    }

    /**
     * Creates pie chart witch given data and display it on new frame.
     *
     * @param title   - chart title
     * @param dataset - dataset for the chart
     */
    public void createPieChart(String title, DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setToolTipGenerator(new StandardPieToolTipGenerator("{0} = {1} ({2})"));
        showChartFrame(title, chart);
    }

    /**
     * Creates bar chart for movies based on rating value and displays it on new frame.
     *
     * @param title - chart title
     * @param data  - list of movies
     */
    public void createMovieRatingValueBarChart(String title, List<Movie> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Movie movie : data) {
            while (!(movie instanceof MovieWithRating) && !(movie instanceof MovieBasic)) {
                movie = ((MovieDecorator) movie).getMovie();
            }
            Movie movie1 = movie;
            while (!(movie1 instanceof MovieBasic)) {
                movie1 = ((MovieDecorator) movie1).getMovie();
            }
            if (movie instanceof MovieWithRating) {
                dataset.setValue(((MovieWithRating) movie).getRate(), "Rating value", ((MovieBasic) movie1).getTitle());
            }
        }
        createBarChart(title, dataset, "Movie", "Rating");
    }
}
