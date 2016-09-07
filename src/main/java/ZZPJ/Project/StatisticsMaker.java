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

    /**
     * Prints informations about n top rated movies of actor and displays bar chart with movie rating value.
     * @param actorName - actor name
     * @param numberOfMovies - number of movies to display
     */
    public void topRatedMoviesOfActor(String actorName, int numberOfMovies) {
        String linkToActor = crawler.findActorLink(actorName);
        if (linkToActor==null){
            return;
        }
        Actor actor = new Actor();
        List<Movie> movies = new ArrayList<Movie>();

        actor.downloadActorInfo(crawler, linkToActor, MovieWithRating.class);
        movies = dataManagement.topNBestRatedMoviesOfActor(numberOfMovies, actor.getMovies());
        if(movies.size() > 0){
            System.out.println("Top " + numberOfMovies + " rated movies of " + actor.getName() + "\n");
            for (Movie movie : movies) {
                movie.showMovieInformatation();
                System.out.println();
            }
        } else {
            System.out.println(actorName.toUpperCase() + " - this actor isn't really actor. He didn't play any role!");
        }

        charts.createMovieRatingValueBarChart("Top rated movies of " + actor.getName(), movies);
    }

    /**
     * Prints number of actors in age groups from the most popular celebs and display pie chart.
     */
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

    /**
     * Prints number of genres occurrence and percentage of occurrences from the most popular movies and display pie chart.
     */
    public void genresOfMostPopularMovies() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<Movie> movies = crawler.getMostPopularMovies(MovieWithGenres.class);
        Map<String, Integer> genresOccurrences = dataManagement.countNumberOfOccurrencesOfGenres(movies);
        Map<String, Double> genresPercentage = dataManagement.percentageOfGenres(genresOccurrences);
        System.out.println("Genres occurrences");
        for (Map.Entry<String, Integer> entry : genresOccurrences.entrySet()) {
            System.out.println(entry.getKey() + ": "
                    + entry.getValue() + " occurences, "
                    + genresPercentage.get(entry.getKey()) + "%");
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        charts.createPieChart("Occurrences of genres of most popular movies", dataset);
    }

    /**
     * Prints info abot actors bon on given date.
     * @param date - date in format yyyy-MM-dd
     */
    public void actorsBornInDate(String date) {
        List<String> links = crawler.getBirthDateActorsLinks(date);
        List<Actor> actors = crawler.getActorsFromLinks(links, null);
        if (actors.size() == 0) {
            System.out.println("There is no actors born in specified date");
        } else {
            System.out.println("People born on " + date + "\n");
            for (Actor actor : actors) {
                actor.showActorInformatation();
                System.out.println();
            }
        }
    }

    /**
     * Prints average of votes in top rated movies of given genre.
     * @param genre
     */
    public void averageNumberOfVotesTopRatedMoviesOfGenre(String genre) {
        List<Integer> votes = crawler.getVotesOfTheHighestRatedMovies(genre);
        double average = dataManagement.calculateArithmeticMean(votes);
        System.out.println("Average number of votes top tated movies of " + genre.toLowerCase() + " = " + average);
    }
}
