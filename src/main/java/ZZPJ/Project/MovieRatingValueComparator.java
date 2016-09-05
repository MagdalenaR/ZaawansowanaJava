package ZZPJ.Project;

import ZZPJ.Project.Model.Movie;
import ZZPJ.Project.Model.MovieBasic;
import ZZPJ.Project.Model.MovieDecorator;
import ZZPJ.Project.Model.MovieWithRating;

import java.util.Comparator;

public class MovieRatingValueComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie m1, Movie m2) {
        double rate1 = 0;
        double rate2 = 0;
        Movie movie1 = m1;
        Movie movie2 = m2;

        while (!(movie1 instanceof MovieWithRating) && !(movie1 instanceof MovieBasic)) {
            movie1 = ((MovieDecorator) movie1).getMovie();
        }
        while (!(movie2 instanceof MovieWithRating) && !(movie2 instanceof MovieBasic)) {
            movie2 = ((MovieDecorator) movie2).getMovie();
        }

        if (movie1 instanceof MovieWithRating) {
            rate1 = ((MovieWithRating) movie1).getRate();
        }
        if (movie2 instanceof MovieWithRating) {
            rate2 = ((MovieWithRating) movie2).getRate();
        }

        return Double.compare(rate2, rate1);
    }

}
