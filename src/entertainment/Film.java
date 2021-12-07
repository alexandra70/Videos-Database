package entertainment;

import User.User;

import java.util.ArrayList;
import java.util.List;

public class Film extends Video{

    private int duration;
    private ArrayList<Double> rating = new ArrayList<Double>();
    private ArrayList<String> usersNames;
    private int nrFavorite;
    private int totalViews;
    private double ratingValue;

    public Film(String title, int year, ArrayList<String> genres,
                ArrayList<String> cast, int duration) {
        super(title, year, genres, cast);
        this.duration = duration;
        this.usersNames = new ArrayList<>();
        this.nrFavorite = 0;
        this.totalViews = 0;
        this.ratingValue = 0.0;
    }

    public int getDuration(){
        return this.duration;
    }

    public void setRating(ArrayList<Double> rating){
        this.rating = rating;
    }

    public ArrayList<Double> getRatingList(){
        return this.rating;
    }

    public void setRatings(final ArrayList<Double> ratings) {
        this.rating = ratings;
    }

    public void setTotalViews(int totalViews){
        this.totalViews = totalViews;
    }

    public int getTotalViews(){
        return this.totalViews;
    }

    public double getRating(){

        double sum = 0.0;
        double i = 0.0;
        for(Double elem : this.rating) {
            if(elem.doubleValue() == 0.0 || elem.isNaN() )
                continue;
            sum += elem.doubleValue();
            i++;
        }

        if(sum == 0.0)
            return sum;
        return (sum/i);
    }

    public String addRating(Video video, String videoName, Double rating, int nrSeasons, String userName){

        //if(!this.usersNames.contains(userName)) {
            this.rating.add(rating);
            this.usersNames.add(userName);
       // }

        return "success -> " + videoName + " was rated with " + rating + " by " + userName;
    }

    public void setFavorite(){
        this.nrFavorite++;
    }

    public int getNrFavorite(){
        return this.nrFavorite;
    }

    public int getYear(){
        return super.getYear();
    }

    public String toString() {
        return this.getTitle();
    }
}
