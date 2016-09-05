package ZZPJ.Project;

import javax.swing.*;

public class App extends JFrame {
    private StatisticsMaker statisticsMaker = new StatisticsMaker();

    public App() {
        super("IMDB CRAWLER");
        init();
    }

    private void init() {
        setSize(500, 250);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setBounds();

        add(topRatedMoviesOfActorNumberOfMoviesTextField);
        add(topRatedMoviesOfActorLabel);
        add(topRatedMoviesOfActorActorNameTextField);
        add(topRatedMoviesOfActorButton);

        add(numberOfActorsInAgeRangeLabel);
        add(numberOfActorsInAgeRangeButton);

        add(genresOfMostPopularMoviesLabel);
        add(genresOfMostPopularMoviesButton);

        add(actorsBornOnDateLabel);
        add(actorsBornOnDateTextField);
        add(actorsBornOnDateButton);

        add(averageNumberOfVotesTopRatedMoviesOfGenreLabel);
        add(averageNumberOfVotesTopRatedMoviesOfGenreComboBox);
        add(averageNumberOfVotesTopRatedMoviesOfGenreButton);
    }

    private void setBounds() {
        int labelWidth = topRatedMoviesOfActorLabel.getPreferredSize().width;
        topRatedMoviesOfActorNumberOfMoviesTextField.setBounds(10, 10, 30, 30);
        topRatedMoviesOfActorLabel.setBounds(50, 10, labelWidth, 30);
        topRatedMoviesOfActorActorNameTextField.setBounds(labelWidth + 60, 10, 100, 30);
        topRatedMoviesOfActorButton.setBounds(labelWidth + 170, 10, 50, 30);

        labelWidth = numberOfActorsInAgeRangeLabel.getPreferredSize().width;
        numberOfActorsInAgeRangeLabel.setBounds(10, 50, labelWidth, 30);
        numberOfActorsInAgeRangeButton.setBounds(labelWidth + 20, 50, 50, 30);

        labelWidth = genresOfMostPopularMoviesLabel.getPreferredSize().width;
        genresOfMostPopularMoviesLabel.setBounds(10, 90, labelWidth, 30);
        genresOfMostPopularMoviesButton.setBounds(labelWidth + 20, 90, 50, 30);

        labelWidth = actorsBornOnDateLabel.getPreferredSize().width;
        actorsBornOnDateLabel.setBounds(10, 130, labelWidth, 30);
        actorsBornOnDateTextField.setBounds(labelWidth + 20, 130, 100, 30);
        actorsBornOnDateButton.setBounds(labelWidth + 130, 130, 50, 30);

        labelWidth = averageNumberOfVotesTopRatedMoviesOfGenreLabel.getPreferredSize().width;
        averageNumberOfVotesTopRatedMoviesOfGenreLabel.setBounds(10, 170, labelWidth, 30);
        averageNumberOfVotesTopRatedMoviesOfGenreComboBox.setBounds(labelWidth + 20, 170, 100, 30);
        averageNumberOfVotesTopRatedMoviesOfGenreButton.setBounds(labelWidth + 130, 170, 50, 30);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new App().setVisible(true);
            }
        });
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
