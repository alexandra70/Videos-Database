package entertainment;

import User.User;

import java.util.ArrayList;

public class Video {

    private String title;
    private int year;
    private ArrayList<String> genres;
    private final ArrayList<String> cast;
    private ArrayList<Double> ratings = new ArrayList<>();
    private double rating;
    private int totalViews;

    public Video(String title, int year, ArrayList<String> genres, ArrayList<String> cast){
        this.title = title;
        this.year = year;
        this.genres = genres;
        this.cast = cast;
        this.rating = 0.0;
        this.totalViews = 0;
    }

    public String getTitle(){
        return this.title;
    }

    public int getYear(){
        return this.year;
    }

    public ArrayList<String> getGenres(){
        return this.genres;
    }

    public ArrayList<String> getCast(){
        return this.cast;
    }

    public double getRating(){
        return 0.0;
    }

    public void setTotalViews(int totalViews){
        //this.totalViews +=totalViews;
    }

    public int getTotalViews(){
        return this.totalViews;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public void setFavorite(){
        return;
    }


    public ArrayList<Double> getRatingList(){
        return this.ratings;
    }

    public int getNrFavorite(){
        return 0;
    }

    public String addRating(Video video, String videoName, Double rating, int nrSeasons, String userName){
        return "null";
    }

    public int getDuration(){
        return 0;
    }

    public String toString(){
        return this.getTitle();
    }

}
