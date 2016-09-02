package ZZPJ.Project;

import ZZPJ.Project.Model.DataManager;
import ZZPJ.Project.Model.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TopRatedMoviesOfActor {

    private DataManager dataManager;
    private Comparator<Movie> movieRatingValueComparator = new MovieRatingValueComparator();

    public TopRatedMoviesOfActor(DataManager dataManager){
        this.dataManager = dataManager;
    }

    public List<Movie> sortMoviesByRatingValue(List<Movie> movies) {
        List<Movie> sortedMovies = new ArrayList<Movie>();
        sortedMovies.addAll(movies);
        Collections.sort(sortedMovies, movieRatingValueComparator);
        return sortedMovies;

    }


}

