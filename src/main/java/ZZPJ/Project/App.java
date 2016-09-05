package ZZPJ.Project;

import javax.swing.*;

public class App extends JFrame{
    private StatisticsMaker statisticsMaker = new StatisticsMaker();

    public App() {
        super("IMDB CRAWLER");
    }

    public static void main(String[] args) {

    }

    private JTextField topRatedMoviesOfActorNumberOfMoviesTextField = new JTextField("10");
    private JLabel topRatedMoviesOfActorLabel = new JLabel("top rated movies of");
    private JTextField topRatedMoviesOfActorActorNameTextField = new JTextField("Nicolas Cage");
    private JButton topRatedMoviesOfActorButton = new JButton("Go");

    private JLabel numberOfActorsInAgeRangeLabel = new JLabel("Number of actors in age range");
    private JButton numberOfActorsInAgeRangeButton = new JButton("Go");

    private JLabel genresOfMostPopularMoviesLabel = new JLabel("Genres of most popular movies");
    private JButton genresOfMostPopularMoviesButton = new JButton("Go");

    private JLabel actorsBornOnDateLabel = new JLabel("Actors born on");
    private JTextField actorsBornOnDateTextField = new JTextField("1990-05-23");
    private JButton actorsBornOnDateButton = new JButton("Go");

    private JLabel averageNumberOfVotesTopRatedMoviesOfGenreLabel = new JLabel("Average number of votes top rated movies of");
    private JComboBox averageNumberOfVotesTopRatedMoviesOfGenreComboBox = new JComboBox(EnumGenre.values());
    private JButton averageNumberOfVotesTopRatedMoviesOfGenreButton = new JButton("Go");
}
