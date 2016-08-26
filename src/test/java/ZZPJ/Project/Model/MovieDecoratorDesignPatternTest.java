package ZZPJ.Project.Model;

import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class MovieDecoratorDesignPatternTest {

    @Mock
    public DataManager mockedDataManager;

    @Spy
    @InjectMocks
    public MovieBasic sutMovieBasic;

    @InjectMocks
    public MovieWithRating sutMovieWithRating;

    @InjectMocks
    public MovieWithGenres sutMovieWithGenres;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void test(){
        String url = "http://www.imdb.com/title/tt0468569/";
        Movie m1 = new MovieBasic();
        DataManager dataManager = new DataManager();
        m1.downloadMovieInfo(dataManager, url);
        m1.showMovieInformatation();
        System.out.println();
        m1 = new MovieWithGenres(m1);
        m1.downloadMovieInfo(dataManager, url);
        m1.showMovieInformatation();
        System.out.println();
        m1 = new MovieWithRating(m1);
        m1.downloadMovieInfo(dataManager, url);
        m1.showMovieInformatation();
    }

    @Test
    public void downloadMovieBasicInfoTest(){

        Date date = new Date();
        when(mockedDataManager.getPageId(any(Document.class))).thenReturn("tt12345");
        when(mockedDataManager.getMovieTitle(any(Document.class))).thenReturn("Batman");
        when(mockedDataManager.getMovieReleaseDate(any(Document.class))).thenReturn(date);

        String url = "";
        sutMovieBasic.downloadMovieInfo(mockedDataManager, url);
        assertEquals("tt12345", sutMovieBasic.id);
        assertEquals("Batman", sutMovieBasic.title);
        assertEquals(date, sutMovieBasic.releaseDate);

        sutMovieBasic.showMovieInformatation();
    }

    @Test
    public void downloadMovieWithRatingInfoTest(){

        Date date = new Date();
        when(mockedDataManager.getPageId(any(Document.class))).thenReturn("tt12345");
        when(mockedDataManager.getMovieTitle(any(Document.class))).thenReturn("Batman");
        when(mockedDataManager.getMovieReleaseDate(any(Document.class))).thenReturn(date);

        when(mockedDataManager.getMovieRate(any(Document.class))).thenReturn(5.0);
        when(mockedDataManager.getMovieRatingCount(any(Document.class))).thenReturn(10.0);

        String url = "";
        sutMovieWithRating.downloadMovieInfo(mockedDataManager, url);

        assertEquals(sutMovieBasic, sutMovieWithRating.movie);
        assertEquals(5.0, sutMovieWithRating.rate,0.0);
        assertEquals(10.0, sutMovieWithRating.ratingCount,0.0);

        sutMovieWithRating.showMovieInformatation();
    }

    @Test
    public void downloadMovieWithGenresInfoTest(){

        Date date = new Date();
        when(mockedDataManager.getPageId(any(Document.class))).thenReturn("tt12345");
        when(mockedDataManager.getMovieTitle(any(Document.class))).thenReturn("Batman");
        when(mockedDataManager.getMovieReleaseDate(any(Document.class))).thenReturn(date);
        when(mockedDataManager.getMovieGenres(any(Document.class))).thenReturn(Arrays.asList("genre1", "genre2"));

        sutMovieWithGenres.downloadMovieInfo(mockedDataManager, "");
        sutMovieWithGenres.showMovieInformatation();

        assertEquals(sutMovieBasic,sutMovieWithGenres.movie);
        assertEquals("genre1",sutMovieWithGenres.genres.get(0));
        assertEquals("genre2",sutMovieWithGenres.genres.get(1));
    }

    @Test
    public void downloadMovieWithRatingAndGenresInfoTest(){
        //DataManager mockedDataManager = mock(DataManager.class);
        Date date = new Date();
        when(mockedDataManager.getPageId(any(Document.class))).thenReturn("tt12345");
        when(mockedDataManager.getMovieTitle(any(Document.class))).thenReturn("Batman");
        when(mockedDataManager.getMovieReleaseDate(any(Document.class))).thenReturn(date);

        when(mockedDataManager.getMovieRate(any(Document.class))).thenReturn(5.0);
        when(mockedDataManager.getMovieRatingCount(any(Document.class))).thenReturn(10.0);
        when(mockedDataManager.getMovieGenres(any(Document.class))).thenReturn(Arrays.asList("genre1", "genre2"));

        Movie m1 = new MovieBasic();
        m1 = new MovieWithGenres(m1);
        m1 = new MovieWithRating(m1);

        m1.downloadMovieInfo(mockedDataManager, "");
        m1.showMovieInformatation();

        assertEquals("tt12345", ((MovieBasic)((MovieWithGenres)((MovieWithRating)m1).movie).movie).id);
        assertEquals("Batman",((MovieBasic)((MovieWithGenres)((MovieWithRating)m1).movie).movie).title);
        assertEquals(date,((MovieBasic)((MovieWithGenres)((MovieWithRating)m1).movie).movie).releaseDate);
        assertEquals("genre1",((MovieWithGenres)((MovieWithRating)m1).movie).genres.get(0));
        assertEquals("genre2",((MovieWithGenres)((MovieWithRating)m1).movie).genres.get(1));
        assertEquals(5.0,((MovieWithRating)m1).rate,0.0);
        assertEquals(10.0,((MovieWithRating)m1).ratingCount,0.0);
    }
}
