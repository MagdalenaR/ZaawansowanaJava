package ZZPJ.Project.Model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TopRatedMoviesOfActor {

    private DataManager dataManager;
    private Comparator<Movie> movieRatingValueComparator = new Comparator<Movie>() {
        @Override
        public int compare(Movie m1, Movie m2) {
            double rate1 = 0;
            double rate2 = 0;
            Movie movie1 = m1;
            Movie movie2 = m2;

            while(!(movie1 instanceof MovieWithRating) && !(movie1 instanceof MovieBasic)){
                movie1 = ((MovieDecorator)movie1).getMovie();
            }
            while(!(movie2 instanceof MovieWithRating) && !(movie2 instanceof MovieBasic)){
                movie2 = ((MovieDecorator)movie2).getMovie();
            }

            if(movie1 instanceof MovieWithRating){
                rate1 = ((MovieWithRating)movie1).getRate();
            }
            if (movie2 instanceof MovieWithRating){
                rate2 = ((MovieWithRating)movie2).getRate();
            }

            return Double.compare(rate2, rate1);
        }
    };

    public TopRatedMoviesOfActor(DataManager dataManager){
        this.dataManager = dataManager;
    }

    public String findActorLink(String actorName) {

        actorName = actorName.replace(" ", "+");

        Document document = dataManager.downloadDocument("http://www.imdb.com/find?q=" + actorName + "&s=nm&exact=true");
        if (document != null) {
            Element element = document.select("table[class=findList] tr td[class=result_text]:contains(Act)").first();
            String linkToActor = element.select("a[href^=/name/]").attr("href");
            return linkToActor;
        }
        return null;
    }

    public List<Movie> sortMoviesByRatingValue(List<Movie> movies) {
        List<Movie> sortedMovies = new ArrayList<Movie>();
        sortedMovies.addAll(movies);
        Collections.sort(sortedMovies, movieRatingValueComparator);
        return sortedMovies;

    }

}

