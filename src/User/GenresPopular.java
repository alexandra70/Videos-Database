package User;

import Actions.DataBaseInput;
import entertainment.Genre;
import entertainment.Video;

import java.util.*;

public class GenresPopular {

    private DataBaseInput dataBaseInput;
    private HashMap<String, Integer> genrePopular;

    public GenresPopular(DataBaseInput dataBaseInput){
        this.dataBaseInput = dataBaseInput;
        this.genrePopular = new HashMap<>();
    }

    //aka videos that have be seen by users
    public ArrayList<Video> getVideoPopularity() {

        ArrayList<User> users = dataBaseInput.getUsers();
        ArrayList<Video> videos = dataBaseInput.getVideos();
        //pentru fiecare user caut sa pun in lista lui de histroy si
        //adaug view la acel video
        //fac asta pentru toate videurile din lista lui de history,
        //dupa care am toate videurile setate la numarul lor efecti de view
        //trec pin toate videurile din lista de videuri in acest moment
        //si pentru fiecare film vad ce genuri are si in map
        //pun asa : la cheia - genul repectiv incrementrez cu vireul resprectiv


        System.out.println("inainte  ??? ? ?  - > viurir si alte alea ");
        for(User user: users) {
            System.out.println(user.getUserName());
            for(Map.Entry<String, Integer> entry : user.getHistory().entrySet()){
                System.out.println(entry.getKey() + entry.getValue());
            }
            System.out.println("\n");
        }
        //totla nr views
        for(User user: users){
            user.SetViewForVideos(videos);
        }
        System.out.println("dupa   ??? ? ?  - > viurir si alte alea ");
        for(Video video: videos){
            System.out.println("\n" + video.getTitle() + video.getTotalViews() + video.getGenres().toString());
        }
        //lets go for genres

        for(Video video: videos){

            for(String genre: video.getGenres()) {
                if (this.genrePopular.containsKey(genre)){
                    //update the value
                    this.genrePopular.put(genre, this.genrePopular.get(genre) + video.getTotalViews());
                }
                else
                    this.genrePopular.put(genre, video.getTotalViews());
            }
        }
        System.out.println("genurile map");
        for(Map.Entry<String, Integer> entry : this.genrePopular.entrySet()){
            System.out.println(entry.getKey() + " :" +  entry.getValue());
        }


        return videos;

    }

    //map for the most appreciated genres
    public HashMap<String, Integer> getPopularGenreMap(ArrayList <Video> videos){
        List<Map.Entry<String, Integer>> valuesOfGenres = new LinkedList<Map.Entry<String, Integer>> (this.genrePopular.entrySet());
        HashMap<String, Integer> sortedGenres = new LinkedHashMap<>();

        //sort the linked list - valuesOfGenres
        Collections.sort(valuesOfGenres, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        for(Map.Entry<String, Integer> entry : valuesOfGenres){
            sortedGenres.put(entry.getKey(), entry.getValue());
        }

        System.out.println("genurile map\n");
        for(Map.Entry<String, Integer> entry : sortedGenres.entrySet()){
            System.out.println(entry.getKey() + " :" +  entry.getValue());
        }

        return sortedGenres;
    }


}
