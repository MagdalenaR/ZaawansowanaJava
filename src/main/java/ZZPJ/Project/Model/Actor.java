package ZZPJ.Project.Model;

import ZZPJ.Project.Crawler;
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
    private List<Movie> movies = new ArrayList<Movie>();

    public void showActorInformatation() {
        String birth = null;
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (birthDate != null) {
            birth = formatter.format(birthDate);
        }

        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Birth date: " + birth);
        if(movies.size()!=0){
            System.out.println("Movies:");
            for (Movie movie : movies) {
                movie.showMovieInformatation();
            }
        }
    }

    public boolean downloadActorInfo(Crawler crawler, String urlForActor, Class<?> movieType) {
        Document document = crawler.downloadDocument(urlForActor);
        if (document == null || document.equals("")) {
            return false;
        } else {
            this.id = crawler.getPageId(document);
            this.name = crawler.getActorName(document);
            this.birthDate = crawler.getActorBirthDate(document);
            if(movieType!=null){
                this.movies = crawler.getActorMovies(document, movieType);
            }
            return true;
        }
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
