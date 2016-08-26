package ZZPJ.Project.Model;

public abstract class MovieDecorator implements Movie {
    protected Movie movie;

    public MovieDecorator(Movie movie){
        this.movie = movie;
    }
}
