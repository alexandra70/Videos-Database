package User;

import Actions.DataBaseInput;
import entertainment.Film;
import entertainment.Season;
import entertainment.Show;
import entertainment.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String userName;
    private String subscriptionType;
    private Map<String, Integer> history;
    private ArrayList<String> favoriteMovies;
    private ArrayList<String> historyRatedMovies;
    private int howManyRatings;
    private HashMap<ArrayList<String> , Integer> mapVideoRated;
    private ArrayList<Video> mostPopular;
    private ArrayList<Video> videos;

    public User(String userName, String subscriptionType,
                Map<String, Integer> history, ArrayList<String> favoriteMovies, ArrayList<Video> videos){
        this.userName = userName;
        this.subscriptionType = subscriptionType;
        this.history = history;
        this.favoriteMovies = favoriteMovies;
        this.historyRatedMovies = new ArrayList<>();
        this.videos = videos;
        this.mostPopular = new ArrayList<Video>();

        this.howManyRatings = 0;
        this.mapVideoRated = new HashMap<>();

    }

    public String getUserName(){
        return this.userName;
    }

    public String getSubscriptionType(){
        return this.subscriptionType;
    }

    public Map<String, Integer> getHistory(){
        return this.history;
    }

    public int getRatingsNumber(){

        return this.howManyRatings;
    }

    public ArrayList<String> getFavoriteMovies(){
        return this.favoriteMovies;
    }

    public ArrayList<String> getHistoryRatedMovies(){
        return this.historyRatedMovies;
    }

    public String setFavorite(String movie){
        //check if it was already added
        for(String findMovie : this.favoriteMovies) {
            if (findMovie.equals(movie)) {
                return "error -> " + movie + " is already in favourite list";
            }
        }

        if(this.history.containsKey(movie)){
            if(!this.favoriteMovies.contains(movie)){
                for(Video video : this.videos){
                    if(video.getTitle().equals(movie)){
                        video.setFavorite();
                    }
                }
                this.favoriteMovies.add(movie);
                return "success -> " + movie + " was added as favourite";
            }
            return "error -> " + movie + " is already in favourite list";
        }
        return "error -> " + movie + " is not seen";
    }

    public String setView(String title){
        for(Map.Entry<String, Integer> entry : this.history.entrySet()){
            if(entry.getKey().equals(title)){
                entry.setValue(entry.getValue() + 1);
                return "success -> " + title +
                        " was viewed with total views of " + entry.getValue();
            }
        }
        //altfel n am gasit si adaug
        this.history.put(title, 1);
        return "success -> " + title + " was viewed with total views of " + 1;
    }

    public void SetViewForVideos(ArrayList<Video> videos){

        //vreau sa vizualtizez filmele din videos si sa actualizez in structurile de
        //film , video , seriar - - numarul de vizualizari
        //trebuie sa vad dc filmul este in history dc da il atases structurii numarul de
        //vizualizari din user


            //get video
            for(Video video : videos){
                if(this.history.containsKey(video.getTitle())){
                    video.setTotalViews( video.getTotalViews()  +  this.history.get(video.getTitle()));
                }
            }




//        for(Map.Entry<String, Integer> entry : this.history.entrySet()) {
//            for(Video video:videos){
//                for(String rate: this.historyRatedMovies){
//                    if(video.getTitle().equals(rate)){
//                        continue;
//                    }
//                }
//                if(video.getTitle().equals(entry.getKey())){
//                    //setez view
//                    video.setTotalViews(entry.getValue());
//                }
//            }
//        }
    }

    public String SetRating(Video videoCurr, String videoName, Double rating, int nrSeasons, String userName) {

        for (Map.Entry<String, Integer> entry : this.history.entrySet()) {


            if (entry.getKey().equals(videoName)) {

                System.out.println("=================>" + entry.getKey() + " | " + videoName);
                ArrayList<String> formString = new ArrayList<>();
                formString.add(videoName);
                if (nrSeasons != 0)
                    formString.add(String.valueOf(nrSeasons - 1));
                else {
                    formString.add(String.valueOf(0)); // for films
                    System.out.println(videoName);
                }
                if (mapVideoRated.containsKey(formString)) {
                    //dc o contine inseama ca a raituit fimlul
                    //dcnu o dauug
                    System.out.println(videoName + " : "  + rating);
                    return "error -> " + videoName + " has been already rated";
                }

                else if (!mapVideoRated.containsKey(formString)) {
                    if (nrSeasons == 0)
                        mapVideoRated.put(formString, 0);
                    else
                        mapVideoRated.put(formString, nrSeasons - 1);

                    this.howManyRatings++;

                    System.out.println("buia" + videoName + howManyRatings);
                    return videoCurr.addRating(videoCurr, videoName, rating, nrSeasons, userName);
                }
            }
        }

        return "error -> " + videoName + " is not seen";
    }

    public String toString(){
        return this.getUserName();
    }

}
