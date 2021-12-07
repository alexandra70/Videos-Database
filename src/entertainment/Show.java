package entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Show extends Video{

    //lista de sezoana
    private ArrayList<Season> seasons;// = new ArrayList<>();
    private int nrSeasons;
    private int nrFavorite;
    private int totalViews;
    private double noteShow;
    private double rating;

    public Show(String title, int year, ArrayList<String> genres,ArrayList<String> cast,
                ArrayList<Season> seasons, int nrSeasons) {
        super(title, year, genres, cast);
        this.seasons = seasons;
        this.nrSeasons = nrSeasons;

        this.nrFavorite = 0;
        this.totalViews = 0;
        this.noteShow = 0.0;
    }

    public void setTotalViews(int totalViews){
        this.totalViews += totalViews;
    }

    public int getTotalViews(){
        return this.totalViews;
    }

    public ArrayList<Season> getSeasons(){
        return this.seasons;
    }

   public int getDuration(){
        int sum = 0;
        for(Season season: seasons)
            sum+=season.getDuration();
        return sum;
   }

    public void setRating(double rating){
        this.rating = rating;
    }


    public double getRating(){
        double result = 0.0;
        double totalRatingAllSeason = 0.0;
        int i = 0;
        for(Season season : this.seasons){
            for(Double elem : season.getRatings()){
                if(elem == 0.0 || elem.isNaN())
                    continue;
                i++;
                result+=elem.doubleValue();
            }
            if(result != 0.0) {
                result = result / i;
                totalRatingAllSeason+=result;
            }
            //chiar dc e zeor am inteles ca trebuie pus si asa
            //adica petnru fiecare dintre sezone sa punem
            //nota finala / chiar si la sezonae fara rating
        }
        this.noteShow = totalRatingAllSeason/this.seasons.size();

        return this.noteShow;
   }

   public int getYear(){
        return super.getYear();
   }

    public String addRating(Video video, String videoName, Double rating, int nrSeasons, String userName) {

        List<Double> ad = this.seasons.get(nrSeasons - 1).getRatings();
        ad.add(rating);
        this.seasons.get(nrSeasons - 1).setRatings(ad);
        return "success -> " + videoName + " was rated with " +
                rating + " by " + userName;
    }

    public void setFavorite(){
        this.nrFavorite++;
    }

    public int getNrFavorite(){
        return this.nrFavorite;
    }

}
