package Actions;

import User.User;
import actor.Actor;
import entertainment.Film;
import entertainment.Show;
import entertainment.Video;
import fileio.*;

import java.util.ArrayList;
import java.util.List;

public class DataBaseInput {

    private ArrayList<Actor> actors = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Film> films =  new ArrayList<>();
    private ArrayList<Show> shows = new ArrayList<>();
    private ArrayList<Video> videos = new ArrayList<>();
    private List<ActionInputData> commands;

    public DataBaseInput(Input input){


        for(MovieInputData movieInputData : input.getMovies()){
            this.films.add(new Film(movieInputData.getTitle(), movieInputData.getYear(), movieInputData.getGenres(),
                    movieInputData.getCast(), movieInputData.getDuration()));
        }

        for(SerialInputData serialInputData: input.getSerials()){
            this.shows.add(new Show(serialInputData.getTitle(), serialInputData.getYear(), serialInputData.getGenres(),
                    serialInputData.getCast(), serialInputData.getSeasons(), serialInputData.getNumberSeason()));
        }

        this.videos.addAll(this.films);
        this.videos.addAll(this.shows);

        for(UserInputData user:input.getUsers()){
            this.users.add(new User(user.getUsername(), user.getSubscriptionType(), user.getHistory(), user.getFavoriteMovies(), this.videos));
        }

        for(ActorInputData actorInputData: input.getActors()){
            this.actors.add(new Actor(actorInputData.getName(), actorInputData.getCareerDescription(),
                    actorInputData.getFilmography(), actorInputData.getAwards()));
        }

        //copiez cu totul in obiectulmeu comenzile
        //si ii dau un nume pecare sa il tin si minte
        this.commands = input.getCommands();
    }

    public ArrayList<User> getUsers(){
        return this.users;
    }

    public List<ActionInputData> getCommands(){
        return this.commands;
    }

    public ArrayList<Actor> getActors(){
        return this.actors;
    }

    public ArrayList<Show> getShows(){
        return this.shows;
    }

    public ArrayList<Film> getFilms(){
        return this.films;
    }

    public ArrayList<Video> getVideos(){
        return this.videos;
    }

    public Video getVideo(String videoName){
        //caut mai intai pin filem
        for(Film film: this.films){
            if(film.getTitle().equals(videoName))
                return film;
        }
        for(Show show:this.shows){
            if(show.getTitle().equals(videoName))
                return show;
        }
        return null;
    }

    public User getUser(String userName){
        for(User user : this.users){
            if(user.getUserName().equals(userName))
                return user;
        }
        return null;
    }

    public Actor getActor(String actorName){
        for(Actor actor : this.actors){
            if(actor.getName().equals(actorName))
                return actor;
        }
        return null;
    }



}
