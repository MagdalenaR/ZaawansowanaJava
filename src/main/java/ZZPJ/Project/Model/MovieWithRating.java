package ZZPJ.Project.Model;

import ZZPJ.Project.Crawler;
import org.jsoup.nodes.Document;

public class MovieWithRating extends MovieDecorator {
    protected double rate;
    protected int ratingCount;

    public MovieWithRating(Movie movie) {
        super(movie);
    }

    public boolean downloadMovieInfo(Crawler crawler, String urlForMovie) {
        Document document = crawler.downloadDocument(urlForMovie);
        if (document == null || document.equals("")) {
            return false;
        } else {
            movie.downloadMovieInfo(crawler, urlForMovie);
            this.rate = crawler.getMovieRate(document);
            this.ratingCount = crawler.getMovieRatingCount(document);
            return true;
        }
    }

    public void showMovieInformatation() {
        movie.showMovieInformatation();
        System.out.println("Rating value: " + this.rate);
        System.out.println("Rating count: " + this.ratingCount);
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
}
