package ZZPJ.Project.Model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import ZZPJ.Project.EnumGenre;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DataManager {

    private static final String MOST_POPULAR_CELEBS_URL = "http://www.imdb.com/search/name?gender=male,female&ref_=nv_cel_m_3";
    private static final String MOST_POPULAR_MOVIES_URL = "http://www.imdb.com/chart/moviemeter?ref_=nv_mv_mpm_8";

  public Document downloadDocument( String url ) {
    Document document = new Document( url );
    try {
      document = Jsoup.connect( url ).userAgent( "Mozilla Chrome Safari Opera" )
        .timeout( 100 * 1000 ).get( );
    } catch ( IOException e ) {
      e.printStackTrace( );
    }
    return document;
  }

    public Document downloadDocument(String url){
        Document document = new Document(url);
        try {
            document = Jsoup.connect(url)
                    .userAgent("Mozilla Chrome Safari Opera")
                    .timeout(100*1000)
                    .get();
        } catch (IOException e) {
            document = null;
            e.printStackTrace();
        }
        return document;
    }

  public String getPageId( Document document ) {
    return document.select( "meta[property=pageId]" ).attr( "content" );
  }

    public String getMovieTitle(Document document){
        Element element = document.select( "h1[itemprop=name]" ).first( );
        String title = null;
        if(element != null){
            title = ((TextNode) element.childNodes().get( 0 )).text();
            title = title.substring(0,title.length()-1);
        }
        return title;
    }

  public Date getMovieReleaseYear( Document document ) {
    SimpleDateFormat format = new SimpleDateFormat( "yyyy" );
    Date date = null;
    Element element = document
      .select( "div[class=txt-block]:contains(Release Date)" ).first( );
    String [ ] dateWithCountry = ( (TextNode) element.childNodes( ).get( 2 ) )
      .text( ).split( " \\(" );
    String dateString = dateWithCountry[ 0 ]
      .substring( dateWithCountry[ 0 ].length( ) - 4 );

    try {
      date = format.parse( dateString );
    } catch ( ParseException e ) {
      e.printStackTrace( );
    }
    return date;
  }

    public double getMovieRate(Document document){
        if (!document.select("span[itemprop=ratingValue]").text( ).isEmpty( )){
            return ( Double.parseDouble( document.select("span[itemprop=ratingValue]").text( ).replace(",",".") ));
        } else {
            return 0.0;
        }
    }

    public double getMovieRatingCount(Document document){
        if (!document.select("span[itemprop=ratingValue]").text( ).isEmpty( )){
            return ( Double.parseDouble( document.select("span[itemprop=ratingCount]").text( )
                    .replace( "," , "" )
                    .replaceAll("\\u00A0","") ) );
        } else {
            return 0.0;
        }
    }

  public List<String> getMovieGenres( Document document ) {
    List<String> genres = new ArrayList<String>( );
    Elements elements = document.select( "span[itemprop=genre]" );
    for ( Element link : elements ) {
      genres.add( link.text( ) );
    }
    return genres;
  }

  public String getActorName( Document document ) {
    return document.select( "meta[property=og:title]" ).attr( "content" );
  }

    public Date getActorBirthDate(Document document){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = null;
        try {
            birth = format.parse( document.select("time[itemprop=birthDate]").attr("datetime") );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birth;
    }

  public List<Movie> getActorMovies( Document document, Class movieType ) {
    List<String> links = getActorMoviesLinks( document );
    List<Movie> movies = getMoviesFromLinks( links, movieType );
    return movies;
  }

  public List<String> getActorMoviesLinks( Document document ) {
    List<String> links = new ArrayList<String>( );
    Elements linksOnPage = document
      .select( "#filmography > .filmo-category-section div[id^=actor]" );
    for ( Element link : linksOnPage ) {
      links.add( link.select( "a[href^=/title/]" ).attr( "href" ) );
    }
    return links;
  }

    public List<Movie> getMoviesFromLinks(List<String> links, final Class movieType){
        final List<Movie> movies = new ArrayList<Movie>();
        final DataManager dataManager = this;
        final CountDownLatch latch= new CountDownLatch(links.size());
        for (final String link : links){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Movie movie = (Movie) new MovieBasic();
                        if(movieType.getName()!=MovieBasic.class.getName()){
                            Class clazz = Class.forName(movieType.getName());
                            Constructor constructor = clazz.getConstructor(Movie.class);
                            movie = (Movie) constructor.newInstance(new MovieBasic());
                        }
                        System.out.println("start " + link);
                        if (movie.downloadMovieInfo(dataManager, ("http://www.imdb.com" + link))){
                            movies.add(movie);
                            System.out.println("finish " + link);
                        } else {
                            System.out.println("failed "+ link);
                        }
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException
                            | ClassNotFoundException | InvocationTargetException  e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                }
            });
            thread.start();
        }

    try {
      latch.await( );
    } catch ( InterruptedException e ) {
      e.printStackTrace( );
    }
    return movies;
  }

  public List<String> getBirthDateActorsLinks( String dateOfActorBirth ) {
    List<String> links = new ArrayList<String>( );
    if ( dateOfActorBirth.matches( "\\d{4}-\\d{2}-\\d{2}" ) ) {
      Document document = downloadDocument(
          "http://www.imdb.com/search/name?birth_date=" + dateOfActorBirth + ","
              + dateOfActorBirth );
      if ( document != null ) {
        Elements linksOnPage = document.select( "#main > .results td[class^=name]" );
        for ( Element element : linksOnPage ) {
          String link = element.select( "a[href^=/name/]" ).attr( "href" );
          links.add( link );
        }
      } else
        return null;
    } else
      System.out.println( "bad date format" );
    return links;
  }

  public List<Actor> getActorsFromLinks( List<String> links ) {
    final List<Actor> actors = new ArrayList<Actor>( );
    final DataManager dataManager = this;
    final CountDownLatch latch = new CountDownLatch( links.size( ) );
    for ( final String link : links ) {
      Thread thread = new Thread( new Runnable( ) {
        @Override
        public void run( ) {
          Actor actor = new Actor( );
          System.out.println( "start " + link );
          if ( actor.downloadActorInfo( dataManager,
              ( "http://www.imdb.com" + link ) ) ) {
            actors.add( actor );
            System.out.println( "finish " + link );
          } else {
            System.out.println( "failed " + link );
          }
          latch.countDown( );
        }
      } );
      thread.start( );
    }
    try {
      latch.await( );
    } catch ( InterruptedException e ) {
      e.printStackTrace( );
    }
    return actors;
  }

  public List<Integer> getVotesOfTheHighestRatedMovies( String genre ) {
    List<Integer> votes = new ArrayList<Integer>( );
    for ( EnumGenre enumGenre : EnumGenre.values( ) ) {
      if ( genre.equals( enumGenre.toString( ) ) ) {
        Document document = downloadDocument(
            "http://www.imdb.com/search/title?genres=" + genre.toLowerCase( )
                + "&sort=user_rating,desc&title_type=feature&num_votes=25000,&pf_rd_m=A2FGELUUNOQJNL&pf_rd_p=2406822102&pf_rd_r="
                + "049TRTYEDSGBYXJAJYBP&pf_rd_s=right-6&pf_rd_t=15506&pf_rd_i=top&ref_=chttp_gnr_"
                + enumGenre.value );

        if ( document != null ) {
          Elements linksOnPage = document.select( "div[class^=rating rating-list" );

          for ( Element element : linksOnPage ) {
            votes.add( Integer.parseInt(
                element.select( "meta[itemprop=ratingCount]" ).attr( "content" ) ) );
          }
        } else return null;
      }
    }
    return votes;
  }

    public String findActorLink(String actorName) {

        String link = createSearchedLink(actorName);
        Document document = downloadDocument(link);
        if (document != null) {
            Element element = document.select("table[class=findList] tr td[class=result_text]:contains(Act)").first();
            String linkToActor = element.select("a[href^=/name/]").attr("href");
            return linkToActor;
        }
        return null;
    }

    public String createSearchedLink(String searchedValue) {
        searchedValue = searchedValue.replace(" ", "+");
        return ("http://www.imdb.com/find?q=" + searchedValue);
    }

    public List<String> getMostPopularCelebsLinks(){
        Document document = downloadDocument(MOST_POPULAR_CELEBS_URL);
        List<String> linksToActors = new ArrayList<String>();
        Elements elements = document.select("table.results td.name");
        for(Element element : elements){
            linksToActors.add(element.select("a[href^=/name/]").attr("href"));
        }
        return linksToActors;
    }

    public List<Movie> getMostPopularMovies(Class movieType){
        Document document = downloadDocument(MOST_POPULAR_MOVIES_URL);
        List<String> links = getMostPopularMoviesLinks(document);
        List<Movie> movies = getMoviesFromLinks(links,movieType);
        return  movies;
    }

    public List<String> getMostPopularMoviesLinks(Document document) {
        List<String> links = new ArrayList<String>();
        Elements elements = document.select("table .titleColumn ");
        for(Element element : elements){
            String link = element.select( "a[href^=/title/]" ).attr( "href" );
            links.add( link );
        }
        return links;
    }
}
