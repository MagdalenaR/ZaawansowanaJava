package ZZPJ.Project;

import ZZPJ.Project.Model.*;
import junit.framework.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class DataManagementTest {

    @Test
    public void calculateArithmeticMean() {
        DataManagement dataManagement = new DataManagement();
        List<Integer> values = new ArrayList<Integer>();
        for (int i = 1; i <= 10; i++) {
            values.add(i);
        }
        Assert.assertEquals(5.0, dataManagement.calculateArithmeticMean(values));
    }

    @Test
    public void getActorAgeTest() {
        Actor actor = new Actor();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse("1994-05-23");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        actor.setBirthDate(date);
        DataManagement dataManagement = new DataManagement();
        assertEquals(22, dataManagement.getActorAge(actor));
    }


    @Test
    public void countNumberOfActorsInAgeRangeTest() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Actor actor1 = new Actor();
        actor1.setBirthDate(simpleDateFormat.parse("1994"));
        Actor actor2 = new Actor();
        actor2.setBirthDate(simpleDateFormat.parse("1954"));
        Actor actor3 = new Actor();
        actor3.setBirthDate(simpleDateFormat.parse("1960"));
        Actor actor4 = new Actor();
        actor4.setBirthDate(simpleDateFormat.parse("1980"));
        List<Actor> actors = new ArrayList<Actor>();
        actors.add(actor1);
        actors.add(actor2);
        actors.add(actor3);
        actors.add(actor4);
        DataManagement dataManagement = new DataManagement();
        assertEquals(2, dataManagement.countNumberOfActorsInAgeRange(0, 40, actors));
        assertEquals(1, dataManagement.countNumberOfActorsInAgeRange(56, 57, actors));
        assertEquals(2, dataManagement.countNumberOfActorsInAgeRange(40, 80, actors));

    }

    @Test
    public void countNumberOfActorsInAgeTest() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Actor actor1 = new Actor();
        actor1.setBirthDate(simpleDateFormat.parse("1994"));
        Actor actor2 = new Actor();
        actor2.setBirthDate(simpleDateFormat.parse("1954"));
        Actor actor3 = new Actor();
        actor3.setBirthDate(simpleDateFormat.parse("1960"));
        Actor actor4 = new Actor();
        actor4.setBirthDate(simpleDateFormat.parse("1980"));
        List<Actor> actors = new ArrayList<Actor>();
        actors.add(actor1);
        actors.add(actor2);
        actors.add(actor3);
        actors.add(actor4);
        DataManagement dataManagement = new DataManagement();

        Map<String, Integer> testMap = dataManagement.countNumberOfActorsInAge(actors);
        assertThat(testMap.get("0-10")).isEqualTo(0);
        assertThat(testMap.get("10-20")).isEqualTo(0);
        assertThat(testMap.get("20-30")).isEqualTo(1);
        assertThat(testMap.get("30-40")).isEqualTo(1);
        assertThat(testMap.get("40-50")).isEqualTo(0);
        assertThat(testMap.get("50-60")).isEqualTo(1);
        assertThat(testMap.get("60-70")).isEqualTo(1);
    }

    @Test
    public void countNumberOfOccurrencesOfGenresTest() {
        DataManagement dataManagement = new DataManagement();
        Movie movie1 = new MovieBasic();
        Movie movie2 = new MovieWithGenres(movie1); //("crime", "action")
        ((MovieWithGenres) movie2).setGenres(Arrays.asList("crime", "action"));
        Movie movie3 = new MovieWithGenres(movie1); //("action", "horror")
        ((MovieWithGenres) movie3).setGenres(Arrays.asList("action", "horror"));
        Movie movie4 = new MovieWithGenres(movie1); //("drama", "horror")
        ((MovieWithGenres) movie4).setGenres(Arrays.asList("drama", "horror"));
        Movie movie5 = new MovieWithRating(movie4); //("drama", "horror")
        Movie movie6 = new MovieWithRating(movie1);
        List<Movie> movies = Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6);
        Map<String, Integer> result = dataManagement.countNumberOfOccurrencesOfGenres(movies);
        assertThat(result.size()).isEqualTo(4);
        assertThat(result.get("crime")).isEqualTo(1);
        assertThat(result.get("action")).isEqualTo(2);
        assertThat(result.get("horror")).isEqualTo(3);
        assertThat(result.get("drama")).isEqualTo(2);
    }

    @Test
    public void countNumberOfAllGenresTest() {
        DataManagement dataManagement = new DataManagement();
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("crime", 1);
        map.put("action", 5);
        map.put("horror", 2);
        assertThat(dataManagement.countNumberOfAllGenres(map)).isEqualTo(8);
    }

    @Test
    public void percentageOfGenresTest() {
        DataManagement dataManagement = new DataManagement();
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("crime", 1);
        map.put("action", 2);
        map.put("horror", 3);
        map.put("drama", 2);
        Map<String, Double> percentage = dataManagement.percentageOfGenres(map);
        assertThat(percentage.get("crime")).isEqualTo(12.5);
        assertThat(percentage.get("action")).isEqualTo(25.0);
        assertThat(percentage.get("horror")).isEqualTo(37.5);
        assertThat(percentage.get("drama")).isEqualTo(25.0);
    }


}
