package ZZPJ.Project.Model;

import org.jsoup.nodes.Document;

import java.util.Arrays;
import java.util.List;

public class MovieWithGenres extends MovieDecorator {
    protected List<String> genres;

    public MovieWithGenres(Movie movie) {
        super(movie);
    }

    public void downloadMovieInfo(DataManager dataManager, String urlForMovie) {
        movie.downloadMovieInfo(dataManager, urlForMovie);
        Document document = dataManager.downloadDocument(urlForMovie);
        this.genres = dataManager.getMovieGenres(document);
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
