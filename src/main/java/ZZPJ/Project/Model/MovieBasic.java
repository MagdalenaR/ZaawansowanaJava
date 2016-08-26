package ZZPJ.Project.Model;

import org.jsoup.nodes.Document;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieBasic implements Movie {
    protected String id;
    protected String title;
    protected Date releaseDate;

    public void downloadMovieInfo(DataManager dataManager, String urlForMovie) {
        Document document = dataManager.downloadDocument(urlForMovie);

        this.id = dataManager.getPageId(document);
        this.title = dataManager.getMovieTitle(document);
        this.releaseDate = dataManager.getMovieReleaseYear(document);
    }

    public void showMovieInformatation() {
        System.out.println("Id: " + this.id);
        System.out.println("Title: " + this.title);
        String date = null;
        Format formatter = new SimpleDateFormat("yyyy");
        if (this.releaseDate != null){
            date = formatter.format(releaseDate);
        }
        System.out.println("Release year: " + date);
    }
}
