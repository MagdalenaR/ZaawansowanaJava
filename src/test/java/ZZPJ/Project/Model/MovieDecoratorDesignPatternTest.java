package ZZPJ.Project.Model;

import ZZPJ.Project.Crawler;
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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class MovieDecoratorDesignPatternTest {

    @Mock
    public Crawler mockedCrawler;

    @Mock
    public Document mockedDocument;

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
    public void downloadMovieBasicInfoTest() {

        Date date = new Date();
        when(mockedCrawler.downloadDocument(anyString())).thenReturn(mockedDocument);
        when(mockedCrawler.getPageId(any(Document.class))).thenReturn("tt12345");
        when(mockedCrawler.getMovieTitle(any(Document.class))).thenReturn("Batman");
        when(mockedCrawler.getMovieReleaseYear(any(Document.class))).thenReturn(date);

        String url = "";
        assertEquals(sutMovieBasic.downloadMovieInfo(mockedCrawler, url), true);
        assertEquals("tt12345", sutMovieBasic.getId());
        assertEquals("Batman", sutMovieBasic.getTitle());
        assertEquals(date, sutMovieBasic.getReleaseDate());

        sutMovieBasic.showMovieInformatation();
    }

    @Test
    public void downloadMovieWithRatingInfoTest() {

        Date date = new Date();
        when(mockedCrawler.downloadDocument(anyString())).thenReturn(mockedDocument);
        when(mockedCrawler.getPageId(any(Document.class))).thenReturn("tt12345");
        when(mockedCrawler.getMovieTitle(any(Document.class))).thenReturn("Batman");
        when(mockedCrawler.getMovieReleaseYear(any(Document.class))).thenReturn(date);

        when(mockedCrawler.getMovieRate(any(Document.class))).thenReturn(5.0);
        when(mockedCrawler.getMovieRatingCount(any(Document.class))).thenReturn(10.0);

        String url = "";
        sutMovieWithRating.downloadMovieInfo(mockedCrawler, url);

        assertEquals(sutMovieBasic, sutMovieWithRating.movie);
        assertEquals(5.0, sutMovieWithRating.rate, 0.0);
        assertEquals(10.0, sutMovieWithRating.ratingCount, 0.0);

        sutMovieWithRating.showMovieInformatation();
    }

    @Test
    public void downloadMovieWithGenresInfoTest() {

        Date date = new Date();
        when(mockedCrawler.downloadDocument(anyString())).thenReturn(mockedDocument);
        when(mockedCrawler.getPageId(any(Document.class))).thenReturn("tt12345");
        when(mockedCrawler.getMovieTitle(any(Document.class))).thenReturn("Batman");
        when(mockedCrawler.getMovieReleaseYear(any(Document.class))).thenReturn(date);
        when(mockedCrawler.getMovieGenres(any(Document.class))).thenReturn(Arrays.asList("genre1", "genre2"));

        sutMovieWithGenres.downloadMovieInfo(mockedCrawler, "");
        sutMovieWithGenres.showMovieInformatation();

        assertEquals(sutMovieBasic, sutMovieWithGenres.movie);
        assertEquals("genre1", sutMovieWithGenres.genres.get(0));
        assertEquals("genre2", sutMovieWithGenres.genres.get(1));
    }

    @Test
    public void downloadMovieWithRatingAndGenresInfoTest() {

        Date date = new Date();
        when(mockedCrawler.downloadDocument(anyString())).thenReturn(mockedDocument);
        when(mockedCrawler.getPageId(any(Document.class))).thenReturn("tt12345");
        when(mockedCrawler.getMovieTitle(any(Document.class))).thenReturn("Batman");
        when(mockedCrawler.getMovieReleaseYear(any(Document.class))).thenReturn(date);

        when(mockedCrawler.getMovieRate(any(Document.class))).thenReturn(5.0);
        when(mockedCrawler.getMovieRatingCount(any(Document.class))).thenReturn(10.0);
        when(mockedCrawler.getMovieGenres(any(Document.class))).thenReturn(Arrays.asList("genre1", "genre2"));

        Movie m1 = new MovieBasic();
        m1 = new MovieWithGenres(m1);
        m1 = new MovieWithRating(m1);

        m1.downloadMovieInfo(mockedCrawler, "");
        m1.showMovieInformatation();

        assertEquals("tt12345", ((MovieBasic) ((MovieWithGenres) ((MovieWithRating) m1).movie).movie).id);
        assertEquals("Batman", ((MovieBasic) ((MovieWithGenres) ((MovieWithRating) m1).movie).movie).title);
        assertEquals(date, ((MovieBasic) ((MovieWithGenres) ((MovieWithRating) m1).movie).movie).releaseDate);
        assertEquals("genre1", ((MovieWithGenres) ((MovieWithRating) m1).movie).genres.get(0));
        assertEquals("genre2", ((MovieWithGenres) ((MovieWithRating) m1).movie).genres.get(1));
        assertEquals(5.0, ((MovieWithRating) m1).rate, 0.0);
        assertEquals(10.0, ((MovieWithRating) m1).ratingCount, 0.0);
    }
}
