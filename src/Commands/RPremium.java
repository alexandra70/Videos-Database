package Commands;

import Actions.DataBaseInput;
import User.User;
import entertainment.Video;

import java.util.*;

public class RPremium {

    private DataBaseInput dataBaseInput;

    public RPremium(DataBaseInput dataBaseInput){
        this.dataBaseInput = dataBaseInput;
    }

    public List<Map.Entry<String, Integer>> getFavoriteVideos(String userName, List<List<String>> filters){
        ArrayList<User> users = this.dataBaseInput.getUsers();
        HashMap<String, Integer> mapFavoriteVideos = new HashMap<>();
        for(User user : users){
            //get favorite moie ofusers, but not the user that is called by the name - userName;
            if(user.getUserName().equals(userName))
                continue;
            System.out.println(user.getUserName() + "    are urma fileme preferate  : " + user.getFavoriteMovies());
            for(String videoFavorite : user.getFavoriteMovies()){
                if(!mapFavoriteVideos.containsKey(videoFavorite)){
                    //dc mapulnu contine key atunci o adaug direct
                    Video video = dataBaseInput.getVideo(videoFavorite);

                    //aplic filtre
                    //valoarea 1 deoarece e prima oara cad adaug in rest se va adauna de fiecare data 1 la valoarea curenta
                    mapFavoriteVideos.put(videoFavorite, 1);
                }
                else
                    mapFavoriteVideos.put(videoFavorite, mapFavoriteVideos.get(videoFavorite) + 1);
            }
        }
        List<Map.Entry<String, Integer>> sortFavorite = new LinkedList<>(mapFavoriteVideos.entrySet());
        //sortez linked list

        Collections.sort(sortFavorite, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        return sortFavorite;
    }

    public String RecommendationFavorite(String userName, int N,List<List<String>> filters){

        List<Map.Entry<String, Integer>> favoriteVideos = this.getFavoriteVideos(userName, filters);

        System.out.println("22?\n\n\n");
        System.out.println(favoriteVideos.toString());

        User user = dataBaseInput.getUser(userName);
        if(user.getSubscriptionType().equals("BASIC"))
            return "FavoriteRecommendation cannot be applied!";
        ArrayList<String> arr = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : favoriteVideos) {
            arr.add(entry.getKey());

        }
        System.out.println("----=>][ " + arr);
        System.out.println("caut asta");

        for(String str : arr) {
            if (!user.getHistory().containsKey(str)){
                return "FavoriteRecommendation result: " + str;
            }
        }

                   // return "FavoriteRecommendation result: " + entry.getKey();
        return "FavoriteRecommendation cannot be applied!";
    }
}
