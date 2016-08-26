package ZZPJ.Project.Model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DataManagerTest {

    private String movieTestPath = "src/test/java/ZZPJ/Project/TestFiles/MovieTest.html";
    private String actorTestPath = "src/test/java/ZZPJ/Project/TestFiles/ActorTest.html";

    private Document getDocumenFromFile(String fileName){
        File file = new File(fileName);
        Document document = new Document(fileName);
        try {
            document = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    @Test
    public void getPageIdMockTest(){
        DataManager dataManager = mock(DataManager.class);
        when(dataManager.getPageId(any(Document.class))).thenReturn("tt12345");
        Document document = new Document("");
        assertEquals("tt12345",dataManager.getPageId(document));
    }

    @Test
    public void getPageIdTest(){
        DataManager dataManager = new DataManager();
        Document doc = getDocumenFromFile(movieTestPath);
        assertEquals("tt0482571",dataManager.getPageId(doc));
    }

    @Test
    public void getMovieTitleMockTest(){
        DataManager dataManager = mock(DataManager.class);
        when(dataManager.getMovieTitle(any(Document.class))).thenReturn("Batman");
        Document document = new Document("");
        assertEquals("Batman",dataManager.getMovieTitle(document));
    }

    @Test
    public void getMovieTitleTest(){
        DataManager dataManager = new DataManager();
        Document doc = getDocumenFromFile(movieTestPath);
        assertEquals("Prestiz",dataManager.getMovieTitle(doc));
    }

    @Test
    public void getMovieReleaseDateMockTest(){
        DataManager dataManager = mock(DataManager.class);
        Date date = new Date();
        when(dataManager.getMovieReleaseDate(any(Document.class))).thenReturn(date);
        Document document = new Document("");
        assertEquals(date,dataManager.getMovieReleaseDate(document));
    }

    @Test
    public void getMovieReleaseDateTest(){
        DataManager dataManager = new DataManager();
        Document document = getDocumenFromFile(movieTestPath);
        Format formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        Date date = dataManager.getMovieReleaseDate(document);
        String result = formatter.format(date);
        assertEquals("05 January 2007",result);
    }


    @Test
    public void getMovieRateMockTest(){
        DataManager dataManager = mock(DataManager.class);
        when(dataManager.getMovieRate(any(Document.class))).thenReturn(5.0);
        Document document = new Document("");
        assertEquals(5.0,dataManager.getMovieRate(document), 0.0);
    }

    @Test
    public void getMovieRateTest(){
        DataManager dataManager = new DataManager();
        Document document = getDocumenFromFile(movieTestPath);
        assertEquals(8.5, dataManager.getMovieRate(document), 0.0);
    }

    @Test
    public void getMovieRatingCountMockTest(){
        DataManager dataManager = mock(DataManager.class);
        when(dataManager.getMovieRatingCount(any(Document.class))).thenReturn(5.0);
        Document document = new Document("");
        assertEquals(5.0,dataManager.getMovieRatingCount(document), 0.0);
    }

    @Test
    public void getMovieRatingCountTest(){
        DataManager dataManager = new DataManager();
        Document document = getDocumenFromFile(movieTestPath);
        assertEquals(845705, dataManager.getMovieRatingCount(document), 0.0);
    }

    @Test
    public void getMovieGenresMockTest(){
        DataManager dataManager = mock(DataManager.class);
        List<String> genres = new ArrayList<>();
        genres.add("action");
        genres.add("biography");
        when(dataManager.getMovieGenres(any(Document.class))).thenReturn(genres);
        Document document = new Document("");
        List<String> result = dataManager.getMovieGenres(document);
        assertThat(result.get(0)).isEqualTo("action");
        assertThat(result.get(1)).isEqualTo("biography");
    }

    @Test
    public void getMovieGenresTest(){
        DataManager dataManager = new DataManager();
        Document document = getDocumenFromFile(movieTestPath);
        List<String> genres = new ArrayList<String>();
        genres.add("Drama");
        genres.add("Mystery");
        genres.add("Sci-Fi");
        List<String> result = dataManager.getMovieGenres(document);
        assertThat(result.get(0)).isEqualTo("Drama");
        assertThat(result.get(1)).isEqualTo("Mystery");
        assertThat(result.get(2)).isEqualTo("Sci-Fi");
    }

    @Test
    public void getActorNameMockTest(){
        DataManager dataManager = mock(DataManager.class);
        when(dataManager.getActorName(any(Document.class))).thenReturn("Leonardo DiCaprio");
        Document document = new Document("");
        assertEquals("Leonardo DiCaprio",dataManager.getActorName(document));
    }

    @Test
    public void getActorNameTest(){
        DataManager dataManager = new DataManager();
        Document doc = getDocumenFromFile(actorTestPath);
        assertEquals("Leonardo DiCaprio",dataManager.getActorName(doc));
    }

    @Test
    public void getActorBirthDateMockTest(){
        DataManager dataManager = mock(DataManager.class);
        Date date = new Date();
        when(dataManager.getActorBirthDate(any(Document.class))).thenReturn(date);
        Document document = new Document("");
        assertEquals(date,dataManager.getActorBirthDate(document));
    }

    @Test
    public void getActorBirthDateTest(){
        DataManager dataManager = new DataManager();
        Document document = getDocumenFromFile(actorTestPath);
        Format formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        Date date = dataManager.getActorBirthDate(document);
        String result = formatter.format(date);
        assertEquals("11 November 1974",result);
    }

    @Test
    public void getActorMoviesMockTest(){
        System.out.print("not implemented");
        Assert.fail();
    }

    @Test
    public void getActorMoviesTest(){
        System.out.print("not implemented");
        Assert.fail();
    }
}
