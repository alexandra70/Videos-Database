package Commands;

import Actions.DataBaseInput;
import User.User;
import User.GenresPopular;
import common.Constants;
import entertainment.Film;
import entertainment.Genre;
import entertainment.Video;

import java.util.*;

public class RStandard {

    private DataBaseInput dataBaseInput;

    public RStandard(DataBaseInput dataBaseInput){
        this.dataBaseInput = dataBaseInput;
    }

    public String RecommendationStandard(String userName){
        if(userName == null)
            return "StandardRecommendation cannot be applied!";
        User user = this.dataBaseInput.getUser(userName);
        ArrayList<Film> movies = dataBaseInput.getFilms();

        for(Film film: movies){
            if(!user.getHistory().containsKey(film.getTitle()))
                return "StandardRecommendation result: " + film.getTitle();
        }
        return "StandardRecommendation cannot be applied!";
    }

    public String RecommendationBestUnseen(String userName){

        User user = this.dataBaseInput.getUser(userName);
        ArrayList<Film> moviesCopy = dataBaseInput.getFilms();

        //copy them

        ArrayList<Film> movies = new ArrayList<Film>();
        for(Film film : moviesCopy) {
            Film newFilm = new Film(film.getTitle(), film.getYear(), film.getGenres(), film.getCast(), film.getDuration());
            newFilm.setRating(film.getRatingList());
            movies.add(newFilm);
        }

        Collections.sort(movies, new Comparator<Film>() {
            @Override
            public int compare(Film o1, Film o2) {

                //if(o1.getRating() != 0 && o2.getRating() != 0)

                    return (int)(o2.getRating() - o1.getRating());
               // return 0;
            }
        });

        System.out.println(user.getHistory());
        for(Film film : movies){
            System.out.println(film.getTitle() + " : " + film.getRating());
        }

        System.out.println("BestRatedUnseenRecommendation" + userName);

        for(Film film: movies){
            System.out.println(film.getTitle() + " : " + film.getRating());
            if(!user.getHistory().containsKey(film.getTitle())) {
                System.out.println(film.getTitle());
                return "BestRatedUnseenRecommendation result: " + film.getTitle();
            }
        }


        return "BestRatedUnseenRecommendation cannot be applied!";
    }

    public String RecommendationPopular(String userName){
        User user = this.dataBaseInput.getUser(userName);
        if(user.getSubscriptionType().equals("BASIC"))
            return "PopularRecommendation cannot be applied!";
        ArrayList<Video> videos = this.dataBaseInput.getVideos();

        ArrayList<Video> videosAfterFilters = new ArrayList<Video>();
        for(Video video: videos) {
            if (user.getHistory().containsKey(video.getTitle())) {
                videosAfterFilters.add(video);

            }
        }

        Collections.sort(videosAfterFilters, new Comparator<Video>() {
            @Override
            public int compare(Video o1, Video o2) {
                if((int)(o1.getRating() - o2.getRating()) == 0){
                    return o1.getTitle().compareTo(o2.getTitle());
                }
                return (int)(o1.getRating() - o2.getRating());
            }
        });

        GenresPopular genresPopular = new GenresPopular(this.dataBaseInput);
        //i have to recommend a movie form that list ->
        ArrayList<Video> seenVideosByUsers = genresPopular.getVideoPopularity();
        HashMap<String, Integer> popularGenres = genresPopular.getPopularGenreMap(seenVideosByUsers);

        for(Video video : seenVideosByUsers) {
            for (Map.Entry<String, Integer> genre : popularGenres.entrySet()) {
                //caut sa nu fi vazut cumva filmul

                if (!user.getHistory().containsKey(video.getTitle())) {
                    //eok numa safie de genul genre
                    if (video.getGenres().contains(genre.getKey())) {
                        return "PopularRecommendation result: " + video.getTitle();
                    }
                }
            }
        }

        System.out.println("dupa recomandare - -- - - -");
        System.out.println(videosAfterFilters.toString());

        return "PopularRecommendation cannot be applied!";

    }




    public String Search(String userName, String genre){
        User user = this.dataBaseInput.getUser(userName);
        if(user.getSubscriptionType().equals("BASIC"))
            return "SearchRecommendation cannot be applied!";

        ArrayList<Video> videos = this.dataBaseInput.getVideos();

        System.out.println("sper - > ");
        for(Video video : videos){
            System.out.println(video.getTitle());
        }

        System.out.println("gata - > ");
        ArrayList<Film> movies = dataBaseInput.getFilms();
        ArrayList<Video> moviesAfterFilters = new ArrayList<Video>();

        for(Video video: videos){
            System.out.println(video.getTitle() + " : " + video.getRating());
            if(!user.getHistory().containsKey(video.getTitle())){
                //vad dc filmul este de acel gen
                System.out.println("[ " + video.getTitle());
                System.out.println(video.getGenres());
                if(video.getGenres().contains(genre)){
                    moviesAfterFilters.add(video);
                }
            }
        }

        Collections.sort(moviesAfterFilters, new Comparator<Video>() {
            @Override
            public int compare(Video o1, Video o2) {
                if((int)(o1.getRating() - o2.getRating()) == 0){
                    return o1.getTitle().compareTo(o2.getTitle());
                }
                return (int)(o1.getRating() - o2.getRating());
            }
        });

        System.out.println("dupa recomandare - -- - - -");
        System.out.println(moviesAfterFilters.toString());
        if(moviesAfterFilters.size() == 0)
            return "SearchRecommendation cannot be applied!";
        return "SearchRecommendation result: " + moviesAfterFilters.toString();
    }

}
