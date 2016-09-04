package ZZPJ.Project;

import ZZPJ.Project.Model.Actor;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class MostPopularCelebsTest {

    @Test
    public void getActorAgeTest(){
        Actor actor = new Actor();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse("1994-05-23");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        actor.setBirthDate(date);
        MostPopularCelebs mostPopularCelebs = new MostPopularCelebs();
        assertEquals(22,mostPopularCelebs.getActorAge(actor));
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
        MostPopularCelebs mostPopularCelebs = new MostPopularCelebs();
        assertEquals(2, mostPopularCelebs.countNumberOfActorsInAgeRange(0, 40, actors));
        assertEquals(1, mostPopularCelebs.countNumberOfActorsInAgeRange(56, 57, actors));
        assertEquals(2, mostPopularCelebs.countNumberOfActorsInAgeRange(40, 80, actors));

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
        MostPopularCelebs mostPopularCelebs = new MostPopularCelebs();

        Map<String, Integer> testMap = mostPopularCelebs.countNumberOfActorsInAge(actors);
        assertThat(testMap.get("0-10")).isEqualTo(0);
        assertThat(testMap.get("10-20")).isEqualTo(0);
        assertThat(testMap.get("20-30")).isEqualTo(1);
        assertThat(testMap.get("30-40")).isEqualTo(1);
        assertThat(testMap.get("40-50")).isEqualTo(0);
        assertThat(testMap.get("50-60")).isEqualTo(1);
        assertThat(testMap.get("60-70")).isEqualTo(1);
    }
}
