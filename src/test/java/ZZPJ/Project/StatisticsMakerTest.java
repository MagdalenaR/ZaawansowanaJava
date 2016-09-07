package ZZPJ.Project;

import ZZPJ.Project.Model.Actor;
import ZZPJ.Project.Model.Movie;
import ZZPJ.Project.Model.MovieWithGenres;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsMakerTest {

    @Mock
    Crawler crawler;
    @Mock
    DataManagement dataManagement;
    @Mock
    Charts charts;
    @Spy
    @InjectMocks
    StatisticsMaker statisticsMaker;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void topRatedMoviesOfActorTest() {
        when(crawler.findActorLink("Brad Pitt")).thenReturn("link");
        statisticsMaker.topRatedMoviesOfActor("Brad Pitt", 4);
        verify(crawler, times(1)).findActorLink("Brad Pitt");
        verify(dataManagement, times(1)).topNBestRatedMoviesOfActor(anyInt(), anyListOf(Movie.class));
    }

    @Test
    public void numberOfActorsInAgeRangeTest() throws InterruptedException {
        statisticsMaker.numberOfActorsInAgeRange();
        verify(dataManagement, times(1)).countNumberOfActorsInAge(anyListOf(Actor.class));
    }

    @Test
    public void genresOfMostPopularMoviesTest() throws InterruptedException {
        statisticsMaker.genresOfMostPopularMovies();
        verify(crawler, times(1)).getMostPopularMovies(MovieWithGenres.class);
        verify(dataManagement, times(1)).countNumberOfOccurrencesOfGenres(anyListOf(Movie.class));
        verify(dataManagement, times(1)).percentageOfGenres(anyMapOf(String.class, Integer.class));
    }

    @Test
    public void actorsBornInDateTest() {
        statisticsMaker.actorsBornInDate("1990-05-23");
        verify(crawler, times(1)).getBirthDateActorsLinks("1990-05-23");
        verify(crawler, times(1)).getActorsFromLinks(anyListOf(String.class), any(Class.class));
    }

    @Test
    public void averageNumberOfVotesTopRatedMoviesOfGenreTest(){
        statisticsMaker.averageNumberOfVotesTopRatedMoviesOfGenre("Action");
        verify(crawler, times(1)).getVotesOfTheHighestRatedMovies("Action");
        verify(dataManagement, times(1)).calculateArithmeticMean(anyListOf(Integer.class));
    }
}
