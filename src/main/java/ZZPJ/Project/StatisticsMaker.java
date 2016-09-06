package ZZPJ.Project;

import ZZPJ.Project.Model.Actor;
import ZZPJ.Project.Model.Movie;
import ZZPJ.Project.Model.MovieWithGenres;
import ZZPJ.Project.Model.MovieWithRating;
import org.jfree.data.general.DefaultPieDataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatisticsMaker {
    private Crawler crawler;
    private DataManagement dataManagement;
    private Charts charts;

    public StatisticsMaker() {
        this.crawler = new Crawler();
        this.dataManagement = new DataManagement();
        this.charts = new Charts();
    }

    public void topRatedMoviesOfActor(String actorName, int numberOfMovies) {

        Actor actor = new Actor();
        List<Movie> movies = new ArrayList<Movie>();
        String linkToActor = crawler.findActorLink(actorName);
        actor.downloadActorInfo(crawler, linkToActor, MovieWithRating.class);
        movies = dataManagement.topNBestRatedMoviesOfActor(numberOfMovies, actor.getMovies());
        System.out.println("Top Rated Movies of " + actorName.toUpperCase());
        for (Movie movie : movies) {
            movie.showMovieInformatation();
        }

        charts.createMovieRatingValueBarChart("Top Rated Movies of " + actorName.toUpperCase(), movies);
    }

    public void numberOfActorsInAgeRange() {

        List<Actor> actors = crawler.getMostPopularActors();
        Map<String, Integer> numberOfActorInAge = dataManagement.countNumberOfActorsInAge(actors);
        DefaultPieDataset dataset = new DefaultPieDataset();

        System.out.println("Number of actors in age range");
        for (Map.Entry<String, Integer> entry : numberOfActorInAge.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        charts.createPieChart("Number of actors in age range", dataset);
    }

    public void genresOfMostPopularMovies() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<Movie> movies = crawler.getMostPopularMovies(MovieWithGenres.class);
        Map<String, Integer> genresOccurrences = dataManagement.countNumberOfOccurrencesOfGenres(movies);
        Map<String, Double> genresPercentage = dataManagement.percentageOfGenres(genresOccurrences);
        System.out.println("GENRES OCCURRENCES");
        for (Map.Entry<String, Integer> entry : genresOccurrences.entrySet()) {
            System.out.println(entry.getKey() + ": "
                    + entry.getValue() + " occurences, "
                    + genresPercentage.get(entry.getKey()) + "%");
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        charts.createPieChart("Occurrences of Gnres of Most Popular Movies", dataset);
    }

  public void actorsBornInDate( String date ) {
    List<String> links = crawler.getBirthDateActorsLinks( date );
    List<Actor> actors = crawler.getActorsFromLinks( links, null );
    if ( actors.isEmpty( ) ) {
      System.out.println( "There is no actors born in specified date" );
    } else {
      System.out.println( "People born " + date );
      for ( Actor actor : actors ) {
        actor.showActorInformatation( );
      }
    }
  }

    public void averageNumberOfVotesTopRatedMoviesOfGenre(String genre){
        List<Integer> votes = crawler.getVotesOfTheHighestRatedMovies(genre);
        double average = dataManagement.calculateArithmeticMean(votes);
        System.out.println(average);
    }
}
