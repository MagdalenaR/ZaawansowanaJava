package ZZPJ.Project.Model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataManager {

    public Document downloadDocument(String url){
        Document document = new Document(url);
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public String getPageId(Document document){
        return document.select( "meta[property=pageId]" ).attr( "content" );
    }

    public String getMovieTitle(Document document){
        Element element = document.select( "h1[itemprop=name]" ).first( );
        String title = ((TextNode) element.childNodes().get( 0 )).text();
        return title.substring(0,title.length()-1);
    }

    public Date getMovieReleaseDate(Document document){
        SimpleDateFormat format;
        Date date = null;
        Element element = document.select("div[class=txt-block]:contains(Release Date)" ).first();
        String[] dateWithCountry = ((TextNode) element.childNodes().get( 2 )).text().split(" \\(");
        String dateString = dateWithCountry[0].substring(1);

        if(dateString.length()==4){
            format = new SimpleDateFormat("yyyy");
        } else {
            format = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        }
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
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

    public List<String> getMovieGenres(Document document){
        List<String> genres = new ArrayList<String>();
        Elements elements = document.select("span[itemprop=genre]");
        for(Element link : elements) {
            genres.add( link.text( ) );
        }
        return genres;
    }

    public String getActorName(Document document){
        return document.select( "meta[property=og:title]" ).attr("content");
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

    public List<Movie> getActorMovies(Document document){

        return null;
    }
}
