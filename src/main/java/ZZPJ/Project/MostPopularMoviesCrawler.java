package ZZPJ.Project;

import ZZPJ.Project.Model.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostPopularMoviesCrawler {
    private DataManager dataManager;

    public MostPopularMoviesCrawler(DataManager dataManager){
        this.dataManager = dataManager;
    }

    public List<Movie> downloadMovies(Class movieType){
        return dataManager.getMostPopularMovies(movieType);
    }

    public Map<String,Integer> countNumberOfOccurrencesOfGenres(List<Movie> movies){
        Map<String, Integer> occurrencesOfGenres = new HashMap<String, Integer>();
        for (Movie movie : movies){
            while (!(movie instanceof MovieBasic) && !(movie instanceof MovieWithGenres)){
                 movie = ((MovieDecorator)movie).getMovie();
            }

            if(movie instanceof MovieWithGenres){
                for(String genre : ((MovieWithGenres)movie).getGenres()){
                    if (occurrencesOfGenres.containsKey(genre)){
                        occurrencesOfGenres.replace( genre, occurrencesOfGenres.get( genre )+1 );
                    } else {
                        occurrencesOfGenres.put( genre, 1 );
                    }
                }
            }
        }
        return occurrencesOfGenres;
    }

    public int countNumberOfAllGenres(Map<String, Integer> genres){
        int numberOfGenres = 0;
        for(Map.Entry<String, Integer> entry : genres.entrySet()) {
            int value = entry.getValue();
            numberOfGenres = numberOfGenres + value;
        }
        return numberOfGenres;
    }

    public Map<String, Double> percentageOfGenres(Map<String, Integer> map){
        int numberOfGenres = countNumberOfAllGenres(map);
        Map<String, Double> percentageOccurrences = new HashMap<String, Double>();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            double percentage = (double) ((entry.getValue()*100)/(double)numberOfGenres);

            percentageOccurrences.put( entry.getKey(), (double)Math.round(percentage*100d)/100d);
        }
        return percentageOccurrences;
    }
}
