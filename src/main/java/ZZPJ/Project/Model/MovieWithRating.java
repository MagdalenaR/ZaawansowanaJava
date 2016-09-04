package ZZPJ.Project.Model;

import org.jsoup.nodes.Document;

public class MovieWithRating extends MovieDecorator {
    protected double rate;
    protected double ratingCount;

    public MovieWithRating(Movie movie) {
        super(movie);
    }

    public boolean downloadMovieInfo(DataManager dataManager, String urlForMovie) {
        Document document = dataManager.downloadDocument(urlForMovie);
        if(document==null || document.equals("")){
            return false;
        } else {
            movie.downloadMovieInfo(dataManager, urlForMovie);
            this.rate = dataManager.getMovieRate(document);
            this.ratingCount = dataManager.getMovieRatingCount(document);
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

    public double getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(double ratingCount) {
        this.ratingCount = ratingCount;
    }
}
