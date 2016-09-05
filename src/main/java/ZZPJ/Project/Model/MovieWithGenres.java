package ZZPJ.Project.Model;

import ZZPJ.Project.Crawler;
import org.jsoup.nodes.Document;

import java.util.Arrays;
import java.util.List;

public class MovieWithGenres extends MovieDecorator {
    protected List<String> genres;

    public MovieWithGenres(Movie movie) {
        super(movie);
    }

    public boolean downloadMovieInfo(Crawler crawler, String urlForMovie) {
        Document document = crawler.downloadDocument(urlForMovie);
        if (document == null || document.equals("")) {
            return false;
        } else {
            movie.downloadMovieInfo(crawler, urlForMovie);
            this.genres = crawler.getMovieGenres(document);
            return true;
        }
    }

    public void showMovieInformatation() {
        movie.showMovieInformatation();
        System.out.println("Genres: " + Arrays.toString(this.genres.toArray()));
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
