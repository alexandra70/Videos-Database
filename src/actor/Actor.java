package actor;

import Actions.DataBaseInput;
import entertainment.Video;

import java.util.ArrayList;
import java.util.Map;

public class Actor {
    private String name;
    private String careerDescription;
    private ArrayList<String> filmography;
    private ArrayList<Video> filmographyAsRef;

    private Map<ActorsAwards, Integer> awards;
    private Double averageRatingActor;

    public Actor(String name, String careerDescription,
                           ArrayList<String> filmography,
                           Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.averageRatingActor = 0.0;
        this.filmographyAsRef = new ArrayList<>();
    }

    public void setFilmographyAsRef(DataBaseInput dataBaseInput){
        for(String str : this.filmography){
            this.filmographyAsRef.add(dataBaseInput.getVideo(str));
        }
    }

    public double getRating(){

        double i = 0.0;
        double total = 0.0;
        for(Video video : this.filmographyAsRef){
            if(video == null)
                continue;
            if(video.getRating() == 0)
                continue;
            i += 1;
            total += video.getRating();
        }

        return total/i;
    }

    public String getName() {
        return name;
    }

    public Double getAverageRatingActor(){
        return this.averageRatingActor;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public int getNumberAwards(){
        int total = 0;
        for(Map.Entry<ActorsAwards, Integer> entry : this.awards.entrySet()){
            total+=entry.getValue();
        }
        return total;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setAverageRatingActor(Double averageRatingActor){
        this.averageRatingActor = averageRatingActor;
    }

    public String toString(){
        return this.getName() + ":";
    }


}
