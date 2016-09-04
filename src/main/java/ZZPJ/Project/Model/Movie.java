package ZZPJ.Project.Model;

import ZZPJ.Project.Crawler;

public interface Movie {
    boolean downloadMovieInfo(Crawler crawler, String urlForMovie);

    void showMovieInformatation();
}
