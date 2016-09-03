package ZZPJ.Project;

import ZZPJ.Project.Model.Actor;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adrian on 2016-09-04.
 */
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
}
