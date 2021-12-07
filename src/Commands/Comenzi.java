package Commands;

import Actions.DataBaseInput;
import User.User;
import entertainment.Video;

import java.util.ArrayList;

public class Comenzi {

    //primesco data base cu toate
    private DataBaseInput data;

    public Comenzi(DataBaseInput dataBaseInput){
        this.data = dataBaseInput;
    }

    public String favorite(String title, String name){
        //caut user cu name name
        User user = this.data.getUser(name);
        //adaug in lista lui user in favoriite films

        return user.setFavorite(title);

    }

    //title = name of the movie, name = name of the user
    public String view(String title, String name){
        //find user

        for(User user:this.data.getUsers()){
            if(user.getUserName().equals(name)){
                System.out.println("am gasit user in cadrul caruia" +
                        "trbuie sa agaug un vieu la serial/film" +
                        "primit ca parametru");
                return user.setView(title);
            }
        }
        return "error ->" + title + "is not seen";
    }

    public String rating(Video video, String videoName, Double rating, int nrSeasons, String userName){
        //find user

        User user = data.getUser(userName);
        if(user!=null) {

            //daca am userul in lista de useri
            //daca este caut in isoria lui dc

            if (user.getHistory().containsKey(videoName)) {
                Video videoSetRating = data.getVideo(videoName);
                videoSetRating.addRating(videoSetRating, videoName, rating, nrSeasons, userName);
                return user.SetRating(video, videoName, rating, nrSeasons, userName);
            }
        }
            return "error -> " + videoName + " is not seen";

    }

}
