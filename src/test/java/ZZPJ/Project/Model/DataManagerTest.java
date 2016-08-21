package ZZPJ.Project.Model;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DataManagerTest {

    private String movieTestPath = "src/test/java/ZZPJ/Project/TestFiles/MovieTest.html";

    public Document getDocument(String fileName){
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
        Document doc = getDocument(movieTestPath);
        assertEquals("tt0482571",dataManager.getPageId(doc));
    }

    @Test
    public void getTitleMockTest(){
        DataManager dataManager = mock(DataManager.class);
        when(dataManager.getMovieTitle(any(Document.class))).thenReturn("Batman");
        Document document = new Document("");
        assertEquals("Batman",dataManager.getMovieTitle(document));
    }

    @Test
    public void getTitleTest(){
        DataManager dataManager = new DataManager();
        Document doc = getDocument(movieTestPath);
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
        Document document = getDocument(movieTestPath);
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
        Document document = getDocument(movieTestPath);
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
        Document document = getDocument(movieTestPath);
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
        Document document = getDocument(movieTestPath);
        List<String> genres = new ArrayList<String>();
        genres.add("Drama");
        genres.add("Mystery");
        genres.add("Sci-Fi");
        List<String> result = dataManager.getMovieGenres(document);
        assertThat(result.get(0)).isEqualTo("Drama");
        assertThat(result.get(1)).isEqualTo("Mystery");
        assertThat(result.get(2)).isEqualTo("Sci-Fi");
    }
}
