package ZZPJ.Project;

import ZZPJ.Project.Model.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsMakerTest {

    @Mock
    Crawler crawler;
    @Mock
    DataManagement dataManagement;
    @Spy
    @InjectMocks
    StatisticsMaker statisticsMaker;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void topRatedMoviesOfActorTest(){
        statisticsMaker.topRatedMoviesOfActor("Brad Pitt", 4);
        verify(crawler, times(1)).findActorLink("Brad Pitt");
        verify(dataManagement, times(1)).topNBestRatedMoviesOfActor(anyInt(), anyListOf(Movie.class));
    }
}
