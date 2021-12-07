package Commands;

import Actions.Action;
import Actions.DataBaseInput;
import actor.Actor;
import actor.ActorsAwards;
import common.Constants;
import entertainment.Film;
import entertainment.Video;

import java.util.*;

import static java.lang.Double.NaN;
import static java.lang.Double.doubleToLongBits;

public class QueryActors {


    private DataBaseInput dataBaseInput;
    public QueryActors(DataBaseInput dataBaseInput){
        this.dataBaseInput = dataBaseInput;
    }

    public String compareActorsFilterDescription(String sortType, int N, List<List<String>> filters) {

        ArrayList<Actor> actors = dataBaseInput.getActors();
        ArrayList<Actor> actorsPassFilters = new ArrayList<Actor>();

        ArrayList<String> words = new ArrayList<String>();
        words.addAll(filters.get(2));

        System.out.println(words);
        System.out.println(words.size());


        for(Actor actor: actors) {
            //caut substring - word in string -description
            int sum = 0;
            for (String word : words) {
                if (actor.getCareerDescription().toLowerCase().contains(word + ' ')) {
                    sum++;
                }
            }
            if (sum == words.size()) {
                //good to go
                actorsPassFilters.add(actor);
            }
        }

        System.out.println(actorsPassFilters);

        if(sortType.equals("asc"))
            Collections.sort(actorsPassFilters, new Comparator<Actor>() {
                @Override
                public int compare(Actor o1, Actor o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });

        if(sortType.equals("desc"))
            Collections.sort(actorsPassFilters, new Comparator<Actor>() {
                @Override
                public int compare(Actor o1, Actor o2) {
                    return o2.getName().compareTo(o1.getName());
                }
            });

        System.out.println("aici N loana " +  N);
        ArrayList<String> finalArray = new ArrayList<>();
        int i = 0;
        for(Actor actor : actorsPassFilters){
            i++;
            if(i > N)
                break;
            finalArray.add(actor.getName());
        }

        return "Query result: " + finalArray;
    }

    public String compareActorsAwards(String sortType, int N, List<List<String>> filters) {

        //sunt toate null pana la 3

        ArrayList<Actor> actors = dataBaseInput.getActors();
        ArrayList<Actor> actorsPassFilters = new ArrayList<Actor>();

        HashMap<String, Integer> mostActive = new HashMap<>();


        if (filters.get(3) == null)
            return "Query result: ";
        ArrayList<String> awards = new ArrayList<>();
        awards.addAll(filters.get(3));

        for (Actor actor : actors) {
            ArrayList<String> actorAwards = new ArrayList<>();
            for (Map.Entry<ActorsAwards, Integer> entry : actor.getAwards().entrySet())
                actorAwards.add(entry.getKey().name());
            if (actorAwards.containsAll(filters.get(3))) {
                //atunci adaug actorul care are toate pemiile la
                mostActive.put(actor.getName(), actor.getNumberAwards());
            }
        }

        List<Map.Entry<String, Integer>> sortRatedVideos = new LinkedList<>(mostActive.entrySet());

        if(sortType.equals("asc"))
            Collections.sort(sortRatedVideos, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    if(o1.getValue() == o2. getValue())
                        return o1.getKey().compareTo(o2.getKey());
                    return o1.getValue() - o2. getValue();
                }
            });
        if(sortType.equals("desc"))
            Collections.sort(sortRatedVideos, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    if(o1.getValue() == o2. getValue())
                        return o2.getKey().compareTo(o1.getKey());
                    return o2.getValue() - o1. getValue();
                }
            });



        ArrayList<String> finalArray = new ArrayList<>();

        int i = 0;
        for(Map.Entry<String, Integer> entry : sortRatedVideos){
            i++;
            if(i > N)
                break;
            finalArray.add(entry.getKey());
        }
        System.out.println(":ce ma -:");
        System.out.println(finalArray);
        return "Query result: " + finalArray;

    }


    //AGERAGE !!!!!!!!!!!!!!
    public String compareActorsRating(String sortType, int N) {
        ArrayList<Actor> actors = this.dataBaseInput.getActors();
        ArrayList<Video> selectVideo = new ArrayList<Video>();
        HashMap<Video, Double> actorsRating = new HashMap<>();
        HashMap<String, Double> actorsSelect = new HashMap<>();

//

        for(Video video : this.dataBaseInput.getVideos()){
            if(video != null && video.getRating()!=0) {
                System.out.println(video.getTitle() + " : : : " + video.getRating());
                System.out.println(video.getRatingList());
                //get its filmography and get actors form it
                for (String str : video.getCast()) {
                    //din cast iau actorii si ii adau in select
                    Actor actor = this.dataBaseInput.getActor(str);
                    if (actor != null)
                        if (actor.getRating() != 0.0)
                            if (!actorsSelect.containsKey(actor.getName())) {
                                actorsSelect.put(actor.getName(), actor.getRating());
                            }
                }
            }
        }

        System.out.println(actorsSelect);
        List<Map.Entry<String , Double>> sort = new LinkedList<>(actorsSelect.entrySet());

        if (sortType.equals("asc")){
            Collections.sort(sort, new Comparator<Map.Entry<String, Double>>() {
                @Override
                public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
//                        if(o1.getValue().doubleValue() == o2.getValue().doubleValue()){
//                            System.out.println(o1.getKey() + " / "+ o2.getKey());
//                            return o1.getKey().compareTo(o2.getKey());
//
//                        }
                    return (int)(o1.getValue().doubleValue() - o2.getValue().doubleValue());
                }
            });
            Collections.sort(sort, new Comparator<Map.Entry<String, Double>>() {
                @Override
                public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                    if(o1.getValue().doubleValue() == o2.getValue().doubleValue()){
                        System.out.println(o1.getKey() + " / "+ o2.getKey());
                        return o1.getKey().compareTo(o2.getKey());

                    }
                 //   return (int)(o1.getValue().doubleValue() - o2.getValue().doubleValue());
                    return 0;
                }
            });
            Collections.sort(sort, new Comparator<Map.Entry<String, Double>>() {
                @Override
                public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                    if(o1.getValue().doubleValue() == o2.getValue().doubleValue()){
                        System.out.println(o1.getKey() + " / "+ o2.getKey());
                        return o1.getKey().compareTo(o2.getKey());

                    }
                    return (int)(o1.getValue().doubleValue() - o2.getValue().doubleValue());
                }
            });
        }
        if (sortType.equals("desc")){
            Collections.sort(sort, new Comparator<Map.Entry<String, Double>>() {
                @Override
                public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                    if(o1.getValue().doubleValue() == o2.getValue().doubleValue()){
                        return o2.getKey().compareTo(o1.getKey());
                    }
                    return (int)(o2.getValue().doubleValue() - o1.getValue().doubleValue());
                }
            });
        }
        for(Map.Entry<String, Double> entry : sort){
            System.out.println("---->>>>>>>>>" + entry.getKey() + " : " + entry.getValue());
        }

        ArrayList<String> arr = new ArrayList<>();
        for(Map.Entry<String, Double> entry : sort) {
            arr.add(entry.getKey());
            System.out.println(arr);
        }

        if (N <= sort.size()) {
            System.out.println(arr.subList(0, N));
            return "Query result: " + arr.subList(0, N);
        }
        return "Query result: " + arr;

    }
}
