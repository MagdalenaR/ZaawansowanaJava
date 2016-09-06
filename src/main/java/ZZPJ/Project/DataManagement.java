package ZZPJ.Project;

import ZZPJ.Project.Model.*;

import java.text.DecimalFormat;
import java.util.*;

public class DataManagement {

    private Comparator<Movie> movieRatingValueComparator = new MovieRatingValueComparator();

    public double calculateArithmeticMean(List<Integer> number) {
        int amount = 0;
        for (Integer num : number) {
            amount += num;
        }
        return amount / number.size();
    }

    public int getActorAge(Actor actor) {
        if(actor.getBirthDate()!=null){
            Date birthDate = actor.getBirthDate();
            Calendar cal = Calendar.getInstance();
            int currentYear = cal.get(Calendar.YEAR);
            cal.setTime(birthDate);
            int birthYear = cal.get(Calendar.YEAR);
            int age = currentYear - birthYear;
            return age;
        } else {
            return -1;
        }
    }

    public Map<String, Integer> countNumberOfActorsInAge(List<Actor> actorList) {
        Map<String, Integer> numberOfActor = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < 70; i += 10) {
            numberOfActor.put(i + "-" + (i + 10), countNumberOfActorsInAgeRange(i, i + 10, actorList));
        }
        numberOfActor.put(">70", countNumberOfActorsInAgeRange(70, 1000, actorList));
        return numberOfActor;
    }

    public int countNumberOfActorsInAgeRange(int from, int to, List<Actor> actors) {
        int count = 0;
        for (Actor actor : actors) {
            if (getActorAge(actor) < to && getActorAge(actor) >= from) {
                count++;
            }
        }
        return count;
    }

    public Map<String, Integer> countNumberOfOccurrencesOfGenres(List<Movie> movies) {
        Map<String, Integer> occurrencesOfGenres = new HashMap<String, Integer>();
        for (Movie movie : movies) {
            while (!(movie instanceof MovieBasic) && !(movie instanceof MovieWithGenres)) {
                movie = ((MovieDecorator) movie).getMovie();
            }
            if (movie instanceof MovieWithGenres) {
                for (String genre : ((MovieWithGenres) movie).getGenres()) {
                    if (occurrencesOfGenres.containsKey(genre)) {
                        occurrencesOfGenres.replace(genre, occurrencesOfGenres.get(genre) + 1);
                    } else {
                        occurrencesOfGenres.put(genre, 1);
                    }
                }
            }
        }
        return occurrencesOfGenres;
    }

    public int countNumberOfAllGenres(Map<String, Integer> genres) {
        int numberOfGenres = 0;
        for (Map.Entry<String, Integer> entry : genres.entrySet()) {
            int value = entry.getValue();
            numberOfGenres = numberOfGenres + value;
        }
        return numberOfGenres;
    }

    public Map<String, Double> percentageOfGenres(Map<String, Integer> map) {
        int numberOfGenres = countNumberOfAllGenres(map);
        Map<String, Double> percentageOccurrences = new HashMap<String, Double>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            double percentage = (double) ((entry.getValue() * 100) / (double) numberOfGenres);
            percentageOccurrences.put(entry.getKey(), (double) Math.round(percentage * 100d) / 100d);
        }
        return percentageOccurrences;
    }

    public List<Movie> sortMoviesByRatingValue(List<Movie> movies) {
        List<Movie> sortedMovies = new ArrayList<Movie>();
        sortedMovies.addAll(movies);
        Collections.sort(sortedMovies, movieRatingValueComparator);
        return sortedMovies;
    }

    public List<Movie> topNBestRatedMoviesOfActor(int count, List<Movie> movies) {
        movies = sortMoviesByRatingValue(movies);
        if (movies.size() > count) {
            movies = getNFirstElements(count, movies);
        }
        return movies;
    }

    public List getNFirstElements(int n, List list) {
        List list2 = new ArrayList();
        list2.addAll(list);
        for (int i = list.size(); i > n; i--) {
            list2.remove(i - 1);
        }
        return list2;
    }
    
    /**
     * This method is used to check correctness of date format, given by the
     * user.
     * 
     * @param dateOfActorBirth
     * @return true if the date format is correct, otherwise false
     */
    public boolean checkDateFormat(String dateOfActorBirth) {
        if (dateOfActorBirth.matches("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")) return true; 
        else {
          System.out.println( "Bad date format" );
          return false;
        }
    }

}
