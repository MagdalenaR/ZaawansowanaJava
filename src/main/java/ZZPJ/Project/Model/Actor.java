package ZZPJ.Project.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Actor {
    private String id;
    private String name;
    private Date birthDate;
    private List<Movie> movies = new ArrayList<>();
}
