package ZZPJ.Project.Model;

import org.jsoup.nodes.Document;

import java.util.Date;

public interface Movie {
    void downloadMovieInfo(DataManager dataManager, String urlForMovie);
    void showMovieInformatation();
}
