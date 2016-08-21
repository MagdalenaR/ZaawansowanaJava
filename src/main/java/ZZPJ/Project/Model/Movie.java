package ZZPJ.Project.Model;

import java.util.Date;

public abstract class Movie {
    protected String id;
    protected String title;
    private Date releaseDate;

    public void downloadMovieInfo(String urlForMovie) {

    }

    public abstract void showMovieInformatation();
}
