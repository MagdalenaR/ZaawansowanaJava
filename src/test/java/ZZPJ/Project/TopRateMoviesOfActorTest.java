package ZZPJ.Project;

import ZZPJ.Project.Model.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TopRateMoviesOfActorTest {

    @Test
    public void sortMoviesByRatingValueTest(){

        TopRatedMoviesOfActor sutTopRatedMoviesOfActor = new TopRatedMoviesOfActor(new DataManager());

        Movie movie1 = new MovieBasic();
        ((MovieBasic)movie1).setTitle("movie");

        Movie movie2 = new MovieWithRating(movie1);
        ((MovieWithRating)movie2).setRate(3);

        Movie movie3 = new MovieWithRating(movie1);
        ((MovieWithRating)movie3).setRate(5);

        Movie movie7 = new MovieWithRating(movie1);
        ((MovieWithRating)movie7).setRate(2);
        Movie movie4 = new MovieWithGenres(movie7);

        Movie movie8 = new MovieWithGenres(movie1);
        Movie movie5 = new MovieWithRating(movie8);
        ((MovieWithRating)movie5).setRate(1);

        Movie movie6 = new MovieWithRating(movie1);
        ((MovieWithRating)movie6).setRate(4);

        List<Movie> movies = Arrays.asList(movie1,movie2,movie3,movie4,movie5,movie6);
        movies = sutTopRatedMoviesOfActor.sortMoviesByRatingValue(movies);
        assertThat(movies).containsSubsequence(movie3, movie6, movie2, movie4, movie5, movie1);
    }

}
