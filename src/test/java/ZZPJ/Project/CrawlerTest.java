package ZZPJ.Project;

import ZZPJ.Project.Model.Movie;
import ZZPJ.Project.Model.MovieBasic;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.*;

public class CrawlerTest {

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
    public void getPageIdTest() {
        Crawler crawler = new Crawler();
        Document doc = crawler.downloadDocument("http://www.imdb.com/title/tt0482571");
        assertEquals("tt0482571", crawler.getPageId(doc));
    }

    @Test
    public void getMovieTitleTest() {
        Crawler crawler = new Crawler();
        Document doc = crawler.downloadDocument("http://www.imdb.com/title/tt0482571");
        assertEquals("The Prestige", crawler.getMovieTitle(doc));
    }

    @Test
    public void getMovieReleaseYearTest() {
        Crawler crawler = new Crawler();
        Document document = crawler.downloadDocument("http://www.imdb.com/title/tt0482571");
        Format formatter = new SimpleDateFormat("yyyy");
        Date date = crawler.getMovieReleaseYear(document);
        String result = formatter.format(date);
        assertEquals("2006", result);
    }

    @Test
    public void getMovieRateTest() {
        Crawler crawler = new Crawler();
        Document document = crawler.downloadDocument("http://www.imdb.com/title/tt0482571");
        assertThat(crawler.getMovieRate(document)).isBetween(0.0, 10.0);
    }

    @Test
    public void getMovieRatingCountTest() {
        Crawler crawler = new Crawler();
        Document document = crawler.downloadDocument("http://www.imdb.com/title/tt0482571");
        assertThat(crawler.getMovieRatingCount(document)).isGreaterThanOrEqualTo(845705);
    }

    @Test
    public void getMovieGenresTest() {
        Crawler crawler = new Crawler();
        Document document = crawler.downloadDocument("http://www.imdb.com/title/tt0482571");
        List<String> result = crawler.getMovieGenres(document);
        assertThat(result.get(0)).isEqualTo("Drama");
        assertThat(result.get(1)).isEqualTo("Mystery");
        assertThat(result.get(2)).isEqualTo("Sci-Fi");
    }

    @Test
    public void getActorNameTest() {
        Crawler crawler = new Crawler();
        Document doc = crawler.downloadDocument("http://www.imdb.com/name/nm0000138/");
        assertEquals("Leonardo DiCaprio", crawler.getActorName(doc));
    }

    @Test
    public void getActorBirthDateTest() {
        Crawler crawler = new Crawler();
        Document document = crawler.downloadDocument("http://www.imdb.com/name/nm0000138/");
        Format formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        Date date = crawler.getActorBirthDate(document);
        String result = formatter.format(date);
        assertEquals("11 November 1974", result);
    }

    @Test
    public void getActorMoviesLinksTest() {
        Crawler crawler = new Crawler();
        Document doc = crawler.downloadDocument("http://www.imdb.com/name/nm0000138/");
        assertThat(crawler.getActorMoviesLinks(doc).size()).isGreaterThanOrEqualTo(37);
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
        Document doc = mock(Document.class);
        when(crawler.getActorMovies(doc, MovieBasic.class)).thenCallRealMethod();
        when(crawler.getMoviesFromLinks(anyListOf(String.class), any(Class.class)))
                .thenReturn(Arrays.asList(new MovieBasic(), new MovieBasic()));
        assertThat(crawler.getActorMovies(doc, MovieBasic.class)).hasSize(2);
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
        when(crawler.createSearchedLink(anyString())).thenReturn("http://www.imdb.com/find?q=Brad+pitt");
        when(crawler.downloadDocument(anyString())).thenCallRealMethod();
        when(crawler.findActorLink("Brad Pitt")).thenCallRealMethod();
        String actorLink = crawler.findActorLink("Brad Pitt");
        assertThat(actorLink).contains("/name/nm0000093/");
    }

    @Test
    public void getBirthDateActorsLinksTest() {
        Crawler crawler = new Crawler();
        assertThat(crawler.getBirthDateActorsLinks("1994-05-21")).hasSize(2);
    }

    @Test
    public void getActorsFromLinksTest() {
        Crawler crawler = new Crawler();
        assertThat(crawler
                .getActorsFromLinks(crawler.getBirthDateActorsLinks("1994-05-21"), null)
                .size()).isEqualTo(2);
    }

    @Test
    public void getVotesOfTheHighestRatedMoviesTest() {
        Crawler crawler = new Crawler();
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
        when(mockedCrawler.downloadDocument(anyString())).thenCallRealMethod();
        when(mockedCrawler.getMostPopularCelebsLinks()).thenCallRealMethod();
        List<String> list = mockedCrawler.getMostPopularCelebsLinks();
        assertEquals(50, list.size());
    }

    @Test
    public void getMostPopularMoviesLinksTest() {
        Crawler crawler = new Crawler();
        Document doc = crawler.downloadDocument("http://www.imdb.com/chart/moviemeter?ref_=nv_mv_mpm_8");
        assertThat(crawler.getMostPopularMoviesLinks(doc)).hasSize(100);
    }
}
