package ZZPJ.Project;

import ZZPJ.Project.Model.Movie;
import ZZPJ.Project.Model.MovieBasic;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.*;

public class CrawlerTest {

    private String movieTestPath = "src/test/java/ZZPJ/Project/TestFiles/MovieTest.html";
    private String actorTestPath = "src/test/java/ZZPJ/Project/TestFiles/ActorTest.html";
    private String topCelebsTestPath = "src/test/java/ZZPJ/Project/TestFiles/TopCelebsTest.html";
    private String searchResultPagePath = "src/test/java/ZZPJ/Project/TestFiles/SearchResultPageTest.html";
    private String mostPopularMoviesTestPath = "src/test/java/ZZPJ/Project/TestFiles/MostPopularMoviesTest.html";

    private Document getDocumenFromFile(String fileName) {
        File file = new File(fileName);
        Document document = new Document(fileName);
        try {
            document = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    @Test(expected = IllegalArgumentException.class)
    public void downloadDocumentExceptionTest() {
        Crawler crawler = new Crawler();
        crawler.downloadDocument("");
    }

    @Test
    public void downloadDocumentTest() {
        String url = "http://www.imdb.com/name/nm0000288/";
        Crawler crawler = new Crawler();
        assertThat(crawler.downloadDocument(url)).isNotNull();
    }

    @Test
    public void getPageIdMockTest() {
        Crawler crawler = mock(Crawler.class);
        when(crawler.getPageId(any(Document.class))).thenReturn("tt12345");
        Document document = new Document("");
        assertEquals("tt12345", crawler.getPageId(document));
    }

    @Test
    public void getPageIdTest() {
        Crawler crawler = new Crawler();
        Document doc = getDocumenFromFile(movieTestPath);
        assertEquals("tt0482571", crawler.getPageId(doc));
    }

    @Test
    public void getMovieTitleMockTest() {
        Crawler crawler = mock(Crawler.class);
        when(crawler.getMovieTitle(any(Document.class))).thenReturn("Batman");
        Document document = new Document("");
        assertEquals("Batman", crawler.getMovieTitle(document));
    }

    @Test
    public void getMovieTitleTest() {
        Crawler crawler = new Crawler();
        Document doc = getDocumenFromFile(movieTestPath);
        assertEquals("Prestiz", crawler.getMovieTitle(doc));
    }

    @Test
    public void getMovieReleaseYearMockTest() {
        Crawler crawler = mock(Crawler.class);
        Date date = new Date();
        when(crawler.getMovieReleaseYear(any(Document.class))).thenReturn(date);
        Document document = new Document("");
        assertEquals(date, crawler.getMovieReleaseYear(document));
    }

    @Test
    public void getMovieReleaseYearTest() {
        Crawler crawler = new Crawler();
        Document document = getDocumenFromFile(movieTestPath);
        Format formatter = new SimpleDateFormat("yyyy");
        Date date = crawler.getMovieReleaseYear(document);
        String result = formatter.format(date);
        assertEquals("2007", result);
    }

    @Test
    public void getMovieRateMockTest() {
        Crawler crawler = mock(Crawler.class);
        when(crawler.getMovieRate(any(Document.class))).thenReturn(5.0);
        Document document = new Document("");
        assertEquals(5.0, crawler.getMovieRate(document), 0.0);
    }

    @Test
    public void getMovieRateTest() {
        Crawler crawler = new Crawler();
        Document document = getDocumenFromFile(movieTestPath);
        assertEquals(8.5, crawler.getMovieRate(document), 0.0);
    }

    @Test
    public void getMovieRatingCountMockTest() {
        Crawler crawler = mock(Crawler.class);
        when(crawler.getMovieRatingCount(any(Document.class))).thenReturn(5.0);
        Document document = new Document("");
        assertEquals(5.0, crawler.getMovieRatingCount(document), 0.0);
    }

    @Test
    public void getMovieRatingCountTest() {
        Crawler crawler = new Crawler();
        Document document = getDocumenFromFile(movieTestPath);
        assertEquals(845705, crawler.getMovieRatingCount(document), 0.0);
    }

    @Test
    public void getMovieGenresMockTest() {
        Crawler crawler = mock(Crawler.class);
        List<String> genres = new ArrayList<String>();
        genres.add("action");
        genres.add("biography");
        when(crawler.getMovieGenres(any(Document.class))).thenReturn(genres);
        Document document = new Document("");
        List<String> result = crawler.getMovieGenres(document);
        assertThat(result.get(0)).isEqualTo("action");
        assertThat(result.get(1)).isEqualTo("biography");
    }

    @Test
    public void getMovieGenresTest() {
        Crawler crawler = new Crawler();
        Document document = getDocumenFromFile(movieTestPath);
        List<String> genres = new ArrayList<String>();
        genres.add("Drama");
        genres.add("Mystery");
        genres.add("Sci-Fi");
        List<String> result = crawler.getMovieGenres(document);
        assertThat(result.get(0)).isEqualTo("Drama");
        assertThat(result.get(1)).isEqualTo("Mystery");
        assertThat(result.get(2)).isEqualTo("Sci-Fi");
    }

    @Test
    public void getActorNameMockTest() {
        Crawler crawler = mock(Crawler.class);
        when(crawler.getActorName(any(Document.class)))
                .thenReturn("Leonardo DiCaprio");
        Document document = new Document("");
        assertEquals("Leonardo DiCaprio", crawler.getActorName(document));
    }

    @Test
    public void getActorNameTest() {
        Crawler crawler = new Crawler();
        Document doc = getDocumenFromFile(actorTestPath);
        assertEquals("Leonardo DiCaprio", crawler.getActorName(doc));
    }

    @Test
    public void getActorBirthDateMockTest() {
        Crawler crawler = mock(Crawler.class);
        Date date = new Date();
        when(crawler.getActorBirthDate(any(Document.class)))
                .thenReturn(date);
        Document document = new Document("");
        assertEquals(date, crawler.getActorBirthDate(document));
    }

    @Test
    public void getActorBirthDateTest() {
        Crawler crawler = new Crawler();
        Document document = getDocumenFromFile(actorTestPath);
        Format formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        Date date = crawler.getActorBirthDate(document);
        String result = formatter.format(date);
        assertEquals("11 November 1974", result);
    }

    @Test
    public void getActorMoviesLinksMockTest() {
        List<String> links = new ArrayList<String>();
        Crawler crawler = mock(Crawler.class);
        when(crawler.getActorMoviesLinks(any(Document.class)))
                .thenReturn(links);
        assertThat(crawler.getActorMoviesLinks(new Document("")))
                .isEqualTo(links);
    }

    @Test
    public void getActorMoviesLinksTest() {
        Crawler crawler = new Crawler();
        Document doc = getDocumenFromFile(actorTestPath);
        assertThat(crawler.getActorMoviesLinks(doc)).hasSize(37);
    }

    @Test
    public void getMoviesFromLinksTest() {
        Crawler crawler = mock(Crawler.class);
        when(crawler.getMoviesFromLinks(anyListOf(String.class),
                any(Class.class))).thenCallRealMethod();
        List<Movie> result = crawler.getMoviesFromLinks(new ArrayList<String>(),
                MovieBasic.class);
        assertThat(result).isEmpty();
    }

    @Test
    public void getActorMoviesTest() {
        Crawler crawler = mock(Crawler.class);
        Document doc = getDocumenFromFile(actorTestPath);
        when(crawler.getActorMovies(doc, MovieBasic.class)).thenCallRealMethod();
        when(crawler.getMoviesFromLinks(anyListOf(String.class), any(Class.class))).thenReturn(new ArrayList<Movie>());

        assertThat(crawler.getActorMovies(doc, MovieBasic.class)).isEmpty();
    }

    @Test
    public void createSearchedLinkCorrect() {
        Crawler crawler = new Crawler();
        String link = crawler.createSearchedLink("Brad pitt");
        assertEquals("http://www.imdb.com/find?q=Brad+pitt", link);
    }

    @Test
    public void findActorLinkTest() {
        Crawler crawler = mock(Crawler.class);
        Document doc = getDocumenFromFile(searchResultPagePath);
        when(crawler.downloadDocument(anyString())).thenReturn(doc);
        when(crawler.findActorLink("Brad Pitt")).thenCallRealMethod();
        String actorLink = crawler.findActorLink("Brad Pitt");
        assertThat(actorLink).startsWith("/name/nm0000093/");
    }

    @Test
    public void getBirthDateActorsLinksTest() {
        Crawler crawler = new Crawler();
        assertThat(crawler.getBirthDateActorsLinks("1994-05-21")).hasSize(2);
    }

    @Test
    public void getBirthDateActorsLinksMockTest() {
        List<String> links = new ArrayList<String>();
        Crawler crawler = mock(Crawler.class);
        when(crawler.getBirthDateActorsLinks(any(String.class)))
                .thenReturn(links);
        assertThat(crawler.getBirthDateActorsLinks(new String("")))
                .isEqualTo(links);
    }

    @Test
    public void getActorsFromLinksTest() {
        Crawler crawler = new Crawler();
        assertThat(crawler
                .getActorsFromLinks(crawler.getBirthDateActorsLinks("1994-05-21"))
                .size()).isEqualTo(2);
    }

    @Test
    public void getVotesOfTheHighestRatedMoviesTest() {
        Crawler crawler = new Crawler();
    /*
     * for ( EnumGenre genre : EnumGenre.values( ) ) { assertThat(
     * crawler.getVotesOfTheHighestRatedMovies( genre.toString( ) ).size( )
     * ) .isEqualTo( 50 ); }
     */
        assertThat(crawler.getVotesOfTheHighestRatedMovies("Action").size())
                .isEqualTo(50);
        assertThat(crawler.getVotesOfTheHighestRatedMovies("Drama").size())
                .isEqualTo(50);
        assertThat(crawler.getVotesOfTheHighestRatedMovies("Horror").size())
                .isEqualTo(50);
    }

    @Test
    public void getMostPopularCelebsLinksTest() {
        Crawler mockedCrawler = mock(Crawler.class);
        Document document = getDocumenFromFile(topCelebsTestPath);
        when(mockedCrawler.downloadDocument(anyString())).thenReturn(document);
        when(mockedCrawler.getMostPopularCelebsLinks()).thenCallRealMethod();
        List<String> list = mockedCrawler.getMostPopularCelebsLinks();
        assertEquals(50, list.size());
    }

    @Test
    public void getMostPopularMoviesLinksTest() {
        Crawler crawler = new Crawler();
        Document doc = getDocumenFromFile(mostPopularMoviesTestPath);
        assertThat(crawler.getMostPopularMoviesLinks(doc)).hasSize(100);
    }
}
