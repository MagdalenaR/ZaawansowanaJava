package ZZPJ.Project;

import ZZPJ.Project.Model.Actor;

import java.util.*;

public class MostPopularCelebs {

    public int getActorAge(Actor actor){
        Date birthDate = actor.getBirthDate();
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        cal.setTime(birthDate);
        int birthYear = cal.get(Calendar.YEAR);

        //int age = (int) (System.currentTimeMillis() - birthDate.getTime())/(24*60*60*1000);

        int age = currentYear - birthYear;
        return age;
    }

    public Map<String, Integer> countNumberOfActorsInAge(List<Actor> actorList){
        Map<String, Integer> numberOfActor = new HashMap<String,Integer>();

        for(int i=0; i <80; i += 10){
            numberOfActor.put(i+"-"+(i+10), countNumberOfActorsInAgeRange(i, i + 10, actorList));
        }

        numberOfActor.put(">70", countNumberOfActorsInAgeRange(70, 1000, actorList));
        return numberOfActor;
    }

    public int countNumberOfActorsInAgeRange(int from, int to, List<Actor> actors){
        int count = 0;
        for (Actor actor : actors) {
            if (getActorAge(actor) < to && getActorAge(actor) >= from) {
                count++;
            }
        }
        return count;
    }
}
