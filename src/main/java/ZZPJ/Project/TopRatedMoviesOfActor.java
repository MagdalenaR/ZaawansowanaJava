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

    public List<Movie> topNBestRatedMoviesOfActor(int count, List<Movie> movies){
        movies = sortMoviesByRatingValue(movies);
        if (movies.size() > count) {
            movies = getNFirstElements(count, movies);
        }
        return movies;
    }

    public List getNFirstElements(int n, List list){
        List list2 = new ArrayList();
        list2.addAll(list);
        for(int i = list.size(); i > n; i--){
            list2.remove(i-1);
        }
        return list2;
    }
}
