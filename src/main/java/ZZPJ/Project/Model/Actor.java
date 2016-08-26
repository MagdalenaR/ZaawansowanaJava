package ZZPJ.Project.Model;

import org.jsoup.nodes.Document;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Actor {
    private String id;
    private String name;
    private Date birthDate;
    private List<Movie> movies = new ArrayList<>();

    public void showActorInformatation(){
        String birth = null;
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (birthDate!=null){
            birth = formatter.format(birthDate);
        }

        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Birth date: " + birth);
        System.out.println("Movies:");
        for (Movie movie : movies){
            movie.showMovieInformatation();
        }
    }

    public void downloadActorInfo(DataManager dataManager, String urlForActor, Class<?> movieType){
        Document document = dataManager.downloadDocument(urlForActor);
        this.id = dataManager.getPageId(document);
        this.name = dataManager.getActorName(document);
        this.birthDate = dataManager.getActorBirthDate(document);
        this.movies = dataManager.getActorMovies(document,movieType);
    }


    public void downloadActorInfo(DataManager dataManager, String urlForActor){
        downloadActorInfo(dataManager,urlForActor,MovieBasic.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
