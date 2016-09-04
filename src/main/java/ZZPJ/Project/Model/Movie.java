package ZZPJ.Project.Model;

public interface Movie {
    boolean downloadMovieInfo(DataManager dataManager, String urlForMovie);
    void showMovieInformatation();
}
