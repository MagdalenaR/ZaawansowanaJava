package ZZPJ.Project;

import ZZPJ.Project.Model.Movie;
import ZZPJ.Project.Model.MovieBasic;
import ZZPJ.Project.Model.MovieWithGenres;
import ZZPJ.Project.Model.MovieWithRating;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DataManagementRatingsTest {

    Movie movie1 = new MovieBasic();
    Movie movie2 = new MovieWithRating(movie1);
    Movie movie3 = new MovieWithRating(movie1);
    Movie movie7 = new MovieWithRating(movie1);
    Movie movie4 = new MovieWithGenres(movie7);
    Movie movie8 = new MovieWithGenres(movie1);
    Movie movie5 = new MovieWithRating(movie8);
    Movie movie6 = new MovieWithRating(movie1);
    List<Movie> movies = Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6);

    @Before
    public void setDataToTestMovies() {
        ((MovieBasic) movie1).setTitle("movie");
        ((MovieWithRating) movie2).setRate(3);
        ((MovieWithRating) movie3).setRate(5);
        ((MovieWithRating) movie7).setRate(2);
        ((MovieWithRating) movie5).setRate(1);
        ((MovieWithRating) movie6).setRate(4);
    }

    @Test
    public void sortMoviesByRatingValueTest() {
        DataManagement dataManagement = new DataManagement();
        movies = dataManagement.sortMoviesByRatingValue(movies);
        assertThat(movies).containsSubsequence(movie3, movie6, movie2, movie4, movie5, movie1);
    }

    @Test
    public void topNBestRatedMoviesOfActor() {
        DataManagement dataManagement = new DataManagement();
        List<Movie> result = dataManagement.topNBestRatedMoviesOfActor(3, movies);
        assertThat(result).isSortedAccordingTo(new MovieRatingValueComparator());
        assertThat(result).hasSize(3);
        assertThat(result).containsSubsequence(movie3, movie6, movie2);
    }

    @Test
    public void topNBestRatedMoviesOfActorTest2() {
        DataManagement dataManagement = new DataManagement();
        List<Movie> result = dataManagement.topNBestRatedMoviesOfActor(8, movies);
        assertThat(result).isSortedAccordingTo(new MovieRatingValueComparator());
        assertThat(result).hasSize(6);
        assertThat(result).containsSubsequence(movie3, movie6, movie2, movie4, movie5, movie1);
    }

    @Test
    public void getNFirstElementsTest() {
        DataManagement dataManagement = new DataManagement();
        List<Movie> result = dataManagement.getNFirstElements(4, movies);
        assertThat(result).hasSize(4);
    }
}
