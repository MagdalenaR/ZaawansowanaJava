package ZZPJ.Project;

import ZZPJ.Project.Model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class MostPopularMoviesTest {

    @Mock
    DataManager dataManager;
    @Spy
    @InjectMocks
    MostPopularMoviesCrawler mostPopularMoviesCrawler;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void countNumberOfOccurrencesOfGenresTest(){
        Movie movie1 = new MovieBasic();
        Movie movie2 = new MovieWithGenres(movie1); //("crime", "action")
        ((MovieWithGenres)movie2).setGenres(Arrays.asList("crime", "action"));
        Movie movie3 = new MovieWithGenres(movie1); //("action", "horror")
        ((MovieWithGenres)movie3).setGenres(Arrays.asList("action", "horror"));
        Movie movie4 = new MovieWithGenres(movie1); //("drama", "horror")
        ((MovieWithGenres)movie4).setGenres(Arrays.asList("drama", "horror"));
        Movie movie5 = new MovieWithRating(movie4); //("drama", "horror")
        Movie movie6 = new MovieWithRating(movie1);

        List<Movie> movies = Arrays.asList(movie1,movie2,movie3,movie4,movie5,movie6);

        when(mostPopularMoviesCrawler.countNumberOfOccurrencesOfGenres(anyListOf(Movie.class))).thenCallRealMethod();
        Map<String,Integer> result = mostPopularMoviesCrawler.countNumberOfOccurrencesOfGenres(movies);

        assertThat(result.size()).isEqualTo(4);
        assertThat(result.get("crime")).isEqualTo(1);
        assertThat(result.get("action")).isEqualTo(2);
        assertThat(result.get("horror")).isEqualTo(3);
        assertThat(result.get("drama")).isEqualTo(2);
    }

    @Test
    public void countNumberOfAllGenresTest(){

        when(mostPopularMoviesCrawler.countNumberOfAllGenres(anyMapOf(String.class,Integer.class))).thenCallRealMethod();
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("crime",1);
        map.put("action", 5);
        map.put("horror",2);
        assertThat(mostPopularMoviesCrawler.countNumberOfAllGenres(map)).isEqualTo(8);
    }

    @Test
    public void percentageOfGenresTest(){
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("crime",1);
        map.put("action",2);
        map.put("horror",3);
        map.put("drama",2);
        when(mostPopularMoviesCrawler.percentageOfGenres(anyMapOf(String.class,Integer.class))).thenCallRealMethod();
        Map<String, Double> percentage = mostPopularMoviesCrawler.percentageOfGenres(map);
        assertThat(percentage.get("crime")).isEqualTo(12.5);
        assertThat(percentage.get("action")).isEqualTo(25.0);
        assertThat(percentage.get("horror")).isEqualTo(37.5);
        assertThat(percentage.get("drama")).isEqualTo(25.0);
    }
}
