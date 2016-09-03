package ZZPJ.Project;

import ZZPJ.Project.Model.Actor;

import java.util.Calendar;
import java.util.Date;

public class MostPopularCelebs {

    public int getActorAge(Actor actor){
        Date birthDate = actor.getBirthDate();
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        cal.setTime(birthDate);
        int birthYear = cal.get(Calendar.YEAR);

        int age = currentYear - birthYear;
        return age;
    }
}
