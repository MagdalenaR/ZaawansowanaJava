package ZZPJ.Project;

import ZZPJ.Project.Model.*;

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
        if (actor.getBirthDate() != null) {
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

    /**
     * Creates map which contains number of actors in age group.
     * Each age group contains next 10 years, exluding last one (more than 70 years).
     * @param actorList
     * @return
     */
    public Map<String, Integer> countNumberOfActorsInAge(List<Actor> actorList) {
        Map<String, Integer> numberOfActor = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < 70; i += 10) {
            numberOfActor.put(i + "-" + (i + 10), countNumberOfActorsInAgeRange(i, i + 10, actorList));
        }
        numberOfActor.put(">70", countNumberOfActorsInAgeRange(70, 1000, actorList));
        return numberOfActor;
    }

    /**
     * Count number of actors in age of given range
     * @param from - minimum age in group (include)
     * @param to - maximum age in group (exclude)
     * @param actors - list of actors
     * @return - number of actors in given age group
     */
    public int countNumberOfActorsInAgeRange(int from, int to, List<Actor> actors) {
        int count = 0;
        for (Actor actor : actors) {
            int age = getActorAge(actor);
            if (age >= from && age < to) {
                count++;
            }
        }
        return count;
    }

    /**
     * Creates map which contains number of genres from given list of movies.
     * @param movies - list of movies
     * @return - map containing genres and their number of occurrences in list of movies
     */
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

    /**
     * Count number of occurrences of all genres, including repeated
     * @param genres - map containing genres and the number of occurrences of each genre
     * @return - number of all genres, including repeated
     */
    public int countNumberOfAllGenres(Map<String, Integer> genres) {
        int numberOfGenres = 0;
        for (Map.Entry<String, Integer> entry : genres.entrySet()) {
            int value = entry.getValue();
            numberOfGenres = numberOfGenres + value;
        }
        return numberOfGenres;
    }

    /**
     * Calculate percentage of occurrences of genres.
     * @param map - map containing genres and the number of occurrences of each genre
     * @return - map containing percentage of occurrences of genres
     */
    public Map<String, Double> percentageOfGenres(Map<String, Integer> map) {
        int numberOfGenres = countNumberOfAllGenres(map);
        Map<String, Double> percentageOccurrences = new HashMap<String, Double>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            double percentage = (double) ((entry.getValue() * 100) / (double) numberOfGenres);
            percentageOccurrences.put(entry.getKey(), (double) Math.round(percentage * 100d) / 100d);
        }
        return percentageOccurrences;
    }

    /**
     * Sort movies by rating value.
     * @param movies - list of movies to sort.
     * @return - sorted movies.
     */
    public List<Movie> sortMoviesByRatingValue(List<Movie> movies) {
        List<Movie> sortedMovies = new ArrayList<Movie>();
        sortedMovies.addAll(movies);
        Collections.sort(sortedMovies, movieRatingValueComparator);
        return sortedMovies;
    }

    /**
     * Creates list of n best rated movies from given list.
     * @param count - number of the best rated movies to return
     * @param movies - list of movies to take the best rated
     * @return - list containing n the best rated movies
     */
    public List<Movie> topNBestRatedMoviesOfActor(int count, List<Movie> movies) {
        movies = sortMoviesByRatingValue(movies);
        if (movies.size() > count) {
            movies = getNFirstElements(count, movies);
        }
        return movies;
    }

    /**
     * Returns n first elements from list
     * @param n - number of elements to return
     * @param list - list from which will be extracted first n elements
     * @return
     */
    public List getNFirstElements(int n, List list) {
        List list2 = new ArrayList();
        list2.addAll(list);
        for (int i = list.size(); i > n; i--) {
            list2.remove(i - 1);
        }
        return list2;
    }
    
    /**
     * This method is used to check correctness of date format, given by the user.
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
