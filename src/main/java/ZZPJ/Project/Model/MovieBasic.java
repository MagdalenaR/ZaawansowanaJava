package ZZPJ.Project.Model;

import ZZPJ.Project.Crawler;
import org.jsoup.nodes.Document;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieBasic implements Movie {
    protected String id;
    protected String title;
    protected Date releaseDate;

    public boolean downloadMovieInfo(Crawler crawler, String urlForMovie) {
        Document document = crawler.downloadDocument(urlForMovie);
        if (document == null || document.equals("")) {
            return false;
        } else {
            this.id = crawler.getPageId(document);
            this.title = crawler.getMovieTitle(document);
            this.releaseDate = crawler.getMovieReleaseYear(document);
            return true;
        }
    }

    public void showMovieInformatation() {
        System.out.println("Id: " + this.id);
        System.out.println("Title: " + this.title);
        String date = null;
        Format formatter = new SimpleDateFormat("yyyy");
        if (this.releaseDate != null) {
            date = formatter.format(releaseDate);
        }
        System.out.println("Release year: " + date);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
