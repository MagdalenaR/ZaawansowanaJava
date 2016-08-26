package ZZPJ.Project.Model;

import org.jsoup.nodes.Document;

public class MovieWithRating extends MovieDecorator {
    //protected Movie movie;
    protected double rate;
    protected double ratingCount;

    public MovieWithRating(Movie movie) {
        super(movie);
    }

    public void downloadMovieInfo(DataManager dataManager, String urlForMovie) {
        movie.downloadMovieInfo(dataManager, urlForMovie);
        Document document = dataManager.downloadDocument(urlForMovie);
        this.rate = dataManager.getMovieRate(document);
        this.ratingCount = dataManager.getMovieRatingCount(document);
    }

    public void showMovieInformatation() {
        movie.showMovieInformatation();
        System.out.println("Rating value: " + this.rate);
        System.out.println("Rating count: " + this.ratingCount);
    }
}
