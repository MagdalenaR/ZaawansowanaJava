package ZZPJ.Project;

import ZZPJ.Project.Model.Actor;
import ZZPJ.Project.Model.Movie;
import ZZPJ.Project.Model.MovieWithRating;

import java.util.ArrayList;
import java.util.List;

public class StatisticsMaker {
    private Crawler crawler;
    private DataManagement dataManagement;

    public StatisticsMaker(){
        this.crawler = new Crawler();
        this.dataManagement = new DataManagement();
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
    }
}
