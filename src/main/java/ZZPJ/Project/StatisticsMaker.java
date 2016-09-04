package ZZPJ.Project;

import ZZPJ.Project.Model.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatisticsMaker {
    private Crawler crawler;
    private DataManagement dataManagement;
    private Charts charts;

    public StatisticsMaker(){
        this.crawler = new Crawler();
        this.dataManagement = new DataManagement();
        this.charts = new Charts();
    }

    public void topRatedMoviesOfActor(String actorName, int numberOfMovies){

        Actor actor = new Actor();
        List<Movie> movies = new ArrayList<Movie>();
        String linkToActor = crawler.findActorLink(actorName);
        actor.downloadActorInfo(crawler, linkToActor, MovieWithRating.class);
        movies = dataManagement.topNBestRatedMoviesOfActor(numberOfMovies, actor.getMovies());

        for (Movie movie : movies) {
            movie.showMovieInformatation();
        }

        charts.createMovieRatingValueBarChart("Top Rated Movies of " + actorName, movies);
    }



    public void numberOfActorsInAgeRange(){

        List<Actor> actors = crawler.getMostPopularActors();
        Map<String, Integer> numberOfActorInAge = dataManagement.countNumberOfActorsInAge(actors);
        DefaultPieDataset dataset = new DefaultPieDataset();

        System.out.println("Number of actors in age range");
        for(Map.Entry<String,Integer> entry:numberOfActorInAge.entrySet()){
            dataset.setValue(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        charts.createPieChart("Number of actors in age range", dataset);
    }
}
