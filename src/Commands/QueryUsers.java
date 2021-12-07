package Commands;

import Actions.DataBaseInput;
import User.User;
import entertainment.Video;

import java.util.*;

public class QueryUsers {

    private DataBaseInput data;

    public QueryUsers(DataBaseInput dataBaseInput) {
        this.data = dataBaseInput;
    }

    public String numberOfRatings(int N, String sortType) {

        ArrayList<User> users = data.getUsers();
        ArrayList<User> usersSorted = new ArrayList<User>();

        HashMap<String, Integer> mostActive = new HashMap<>();

        for(User user: users) {
            if(user.getRatingsNumber() == 0)
                continue;
            mostActive.put(user.getUserName(), user.getRatingsNumber());
            System.out.println(user.getUserName() + " : " + user.getRatingsNumber());
        }

        List<Map.Entry<String, Integer>> sortRatedVideos = new LinkedList<>(mostActive.entrySet());
        //sortez linked list
        if(sortType.equals("asc"))
            Collections.sort(sortRatedVideos, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    if(o1.getValue() == o2. getValue())
                        return o1.getKey().compareTo(o2.getKey());
                    return o1.getValue() - o2.getValue();
                }
            });

        if(sortType.equals("desc"))
            Collections.sort(sortRatedVideos, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    if(o1.getValue() == o2. getValue())
                        return o2.getKey().compareTo(o1.getKey());
                    return o2.getValue() - o1.getValue();
                }
            });

//        for(User user: users){
//           // System.out.println(user.getUserName());
//           // System.out.println(user.getHistoryRatedMovies().size());
//            if(user.getRatingsNumber() == 0)
//                continue;
//            System.out.println();
//            usersSorted.add(user);
//        }
//
//        if (sortType.equals("asc"))
//            Collections.sort(usersSorted, new Comparator<User>() {
//                @Override
//                public int compare(User o1, User o2) {
//                    return o1.getHistoryRatedMovies().size() - o2.getHistoryRatedMovies().size();
//                }
//            });
//
//        if (sortType.equals("desc"))
//            Collections.sort(usersSorted, new Comparator<User>() {
//                @Override
//                public int compare(User o1, User o2) {
//                    return o2.getHistoryRatedMovies().size() - o1.getHistoryRatedMovies().size();
//                }
//            });
//
//        if(N > usersSorted.size())
//            return "Query result: " + usersSorted.toString();
//
//        return "Query result: " + usersSorted.subList(0, N - 1).toString();

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

}
