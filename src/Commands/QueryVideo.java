package Commands;

import Actions.DataBaseInput;
import User.User;
import common.Constants;
import entertainment.Film;
import entertainment.Show;
import entertainment.Video;
import fileio.MovieInputData;

import java.nio.file.FileVisitResult;
import java.util.*;

public class QueryVideo {

    private DataBaseInput dataBaseInput;

    public QueryVideo(DataBaseInput dataBaseInput) {
        this.dataBaseInput = dataBaseInput;
    }

    public String MovieRating(int N, List<List<String>> filters, String sort_type) {

        ArrayList<Film> movies = dataBaseInput.getFilms();
        ArrayList<Film> selected = new ArrayList<>();

        for (Film movie : movies) {
            int add = 1;
            for (int i = 0; i < 2; i++) {
                if (filters.get(i) == null)
                    continue;

                for (String keyword : filters.get(i)) {
                    if (i == 0) {
                        if (keyword != null && movie.getYear() != Integer.parseInt(keyword))
                            add = 0;
                    } else if (i == 1) {
                        if (keyword != null && (!movie.getGenres().equals(keyword)))
                            add = 0;
                    }
                }
            }
            if (add == 1) {
                selected.add(movie);
            }
        }
        if (sort_type.equals("asc")) {
            Collections.sort(selected, new Comparator<Film>() {
                @Override
                public int compare(Film o1, Film o2) {
                    return (int) (o2.getRating() - o1.getRating());
                    // < 0 le schimba , altefel nu dc o2 e mai mare
                }
            });
        }

        if (sort_type.equals("desc")) {
            Collections.sort(selected, new Comparator<Film>() {
                @Override
                public int compare(Film o1, Film o2) {
                    return (int) (o1.getRating() - o2.getRating());
                }
            });
        }

        return "Query result: " + selected.toString();
    }

    public String ShowRating(int N, List<List<String>> filters, String sort_type) {

        ArrayList<Show> shows = dataBaseInput.getShows();
        ArrayList<Show> selected = new ArrayList<Show>();

        System.out.println(filters);

        for(Show show : shows) {


            int add = 1;
            int genreNr = 0;

            for (int i = 0; i < 2; i++) {

                if (filters.get(i) == null)
                    continue;

                for (String keyword : filters.get(i)) {
                    if (i == 0 && keyword != null && show.getYear() != Integer.parseInt(keyword))
                        add = 0;

                    if (i == 1) {
                        for (String genres : show.getGenres()) {
                            if (keyword != null && (genres.equals(keyword)))
                                genreNr = 1;
                        }
                    }
                }
                if (add == 1 && genreNr == 1) {
                    if (!selected.contains(show)) {
                        if(show.getRating() != 0.0)
                            selected.add(show);
                    }
                }
            }
        }

        System.out.println(selected);

        if (sort_type.equals("asc")) {
            Collections.sort(selected, new Comparator<Show>() {
                @Override
                public int compare(Show o1, Show o2) {
                    return (int) (o2.getRating() - o1.getRating());
                    // < 0 le schimba , altefel nu dc o2 e mai mare
                }
            });
        }

        if (sort_type.equals("desc")) {
            Collections.sort(selected, new Comparator<Show>() {
                @Override
                public int compare(Show o1, Show o2) {
                    return (int) (o1.getRating() - o2.getRating());
                }
            });
        }


        return "Query result: " + selected.toString();
    }

    //primele N video-uri sortate după numărul de apariții
    // în listele de video-uri favorite ale utilizatorilor
// întoarce videoclipul care e cel mai des intalnit in lista de favorite
// (care să nu fie văzut de către utilizatorul pentru care se aplică recomandarea)
// a tuturor utilizatorilor, al doilea criteriu fiind ordinea de aparitie din baza
// de date. Atentie! Videoclipul trebuie sa existe in cel putin o lista de videoclipuri
// favorite ale userilor.

    public String FavoriteMovie(int N, List<List<String>> filters, String sort_type) {

        System.out.println("macar aici\n");
        ArrayList<User> users = dataBaseInput.getUsers();
        ArrayList<String> favoriteMovies = new ArrayList<>();
        HashMap<String, Integer> videos = new HashMap<String, Integer>();

        for (User user : users) {
                favoriteMovies.addAll(user.getFavoriteMovies());
        }
   //     System.out.println("bumbum");
   //     System.out.println(favoriteMovies);

        ArrayList<Film> moviesSelect = new ArrayList<>();
        ArrayList<Film> movies = dataBaseInput.getFilms();


        //verific care dintre filme sunt bune de adaugta

        for (String favorite : favoriteMovies) {
            for (Film film : movies) {
                if (film.getTitle().equals(favorite)) {
                    //il lasanu fac nimic
                    moviesSelect.add(film);
                }
            }
        }


        // ArrayList<Film> selected = new ArrayList<>();

        HashMap<String, Integer> hashMap = new HashMap<>();

       // System.out.println(moviesSelect);

        for (Film movie : moviesSelect) {

           // System.out.println(" nume film-" + movie.getTitle());
            //now take the filters


            int add = 1;
            int genreNr = 0;
            for (int i = 0; i < 2; i++) {

                if (filters.get(i) == null)
                    continue;

                for (String keyword : filters.get(i)) {
                    if (i == 0)
                        if(keyword != null && movie.getYear() != Integer.parseInt(keyword))
                             add = 0;

                    if (i == 1) {
                        for (String genres : movie.getGenres()) {
                            if (keyword != null && (genres.equals(keyword)))
                                genreNr = 1;
                        }
                    }
                }
                if(filters.get(0).get(0) == null && genreNr == 1){
                    if (hashMap.containsKey(movie.getTitle())) {
                        hashMap.put(movie.getTitle(), hashMap.get(movie.getTitle()) + 1);
                    } else {
                        hashMap.put(movie.getTitle(), 1);
                    }
                }
                if(filters.get(1).get(0) == null && add == 1){
                    if (hashMap.containsKey(movie.getTitle())) {
                        hashMap.put(movie.getTitle(), hashMap.get(movie.getTitle()) + 1);
                    } else {
                        hashMap.put(movie.getTitle(), 1);
                    }
                }
                if (add == 1 && genreNr == 1) {
                    if (hashMap.containsKey(movie.getTitle())) {
                        hashMap.put(movie.getTitle(), hashMap.get(movie.getTitle()) + 1);
                    } else {
                        hashMap.put(movie.getTitle(), 1);
                    }
                }
            }




            //
//            int add = 1;
//            for (int i = 0; i < 2; i++) {
//                if (filters.get(i) == null)
//                    continue;
//
//                for (String keyword : filters.get(i)) {
//                    if (i == 0) {
//                        if (keyword != null && movie.getYear() != Integer.parseInt(keyword))
//                            add = 0;
//                    } else if (i == 1) {
//                        if (keyword != null && (!(movie.getGenres().contains(keyword)))) {
//                            System.out.println("loa - >" + movie);
//                            add = 0;
//                        }
//                    }
//
//                }
//                if (add == 1) {
//                    if (hashMap.containsKey(movie.getTitle())) {
//                        hashMap.put(movie.getTitle(), hashMap.get(movie.getTitle()) + 1);
//                    } else {
//                        hashMap.put(movie.getTitle(), 1);
//                    }
//                }
//                //selected.add(movie);
//            }
        }


        List<Map.Entry<String, Integer>> sortVideos = new LinkedList<>(hashMap.entrySet());
        if(sort_type.equals("asc"))
            Collections.sort(sortVideos, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    if(o2.getValue() == o1.getValue())
                        return o1.getKey().toString().compareTo(o2.getKey().toString());
                    return o1.getValue() - o2.getValue();
                }
            });
        if(sort_type.equals("desc"))
            Collections.sort(sortVideos, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    if(o2.getValue().intValue() == o1.getValue().intValue())
                        return o2.getKey().toString().compareTo(o1.getKey().toString());
                    return o2.getValue().intValue() - o1.getValue().intValue();
                }
            });




//        List<Map.Entry<String, Integer>> sortVideos = new LinkedList<>(hashMap.entrySet());
//
//        Collections.sort(sortVideos, new Comparator<Map.Entry<String, Integer>>() {
//            @Override
//            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//                return o2.getValue() - o1.getValue();
//            }
//        });
//

        System.out.println(sortVideos);

        ArrayList<String> finalArray = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, Integer> entry : sortVideos) {
            i++;
            if (i > N)
                break;
            finalArray.add(entry.getKey());
        }
        System.out.println(":ce ma -:");
        System.out.println(finalArray);

        return "Query result: " + finalArray;


    }



    public String FavoriteShows(int N, List<List<String>> filters, String sort_type) {

        System.out.println("macar aici\n");
        ArrayList<User> users = dataBaseInput.getUsers();
        ArrayList<String> favoriteShows = new ArrayList<String>();

        for (User user : users) {
            favoriteShows.addAll(user.getFavoriteMovies());
        }

        ArrayList<Show> shows = dataBaseInput.getShows();
        ArrayList<Show> showSelect = new ArrayList<Show>();

        //verific care dintre filme sunt bune de adaugta

        for (String favorite : favoriteShows) {
            for (Show show : shows) {
                if (show.getTitle().equals(favorite)) {
                    //il lasanu fac nimic
                    showSelect.add(show);
                }
            }
        }

        ArrayList<Show> selected = new ArrayList<Show>();


        for (Show show : showSelect) {

            System.out.println(show.getTitle());
            System.out.println(show.getGenres());

            //now take the filters

            int add = 1;
            int genreNr = 0;

            for (int i = 0; i < 2; i++) {

                if (filters.get(i) == null)
                    continue;

                for (String keyword : filters.get(i)) {
                    if (i == 0 && keyword != null && show.getYear() != Integer.parseInt(keyword))
                        add = 0;

                    if (i == 1) {
                        for (String genres : show.getGenres()) {
                            if (keyword != null && (genres.equals(keyword)))
                                genreNr = 1;
                        }
                    }
                }
                if (add == 1 && genreNr == 1) {
                    if(!selected.contains(show))
                        selected.add(show);
                }
            }
        }

        return "Query result: " + selected;


    }

    //Longest - primele N video-uri sortate după durata lor. Pentru seriale se consideră suma duratelor sezoanelor.

    public String LongestVideo(int N, List<List<String>> filters, String sort_type) {


        for (List<String> f : filters) {
            System.out.println("->[pentru cand ma duc in longest " + f);
        }

        ArrayList<Film> movies = dataBaseInput.getFilms();

        ArrayList<Film> selected = new ArrayList<>();

        int i = 0;
        for (Film movie : movies) {

            int add = 1;
            //am anul si genul
            for (List<String> filterList : filters) {

                if (filterList == null)
                    continue;
                //sunt la ani
                if (filterList == filters.get(0)) {
                    //am un singur an, deci e un singur elemennt in lista
                    for (String filter : filterList) {
                        if (filter != null && movie.getYear() != Integer.parseInt(filter))
                            add = 0;
                    }
                }

                if (filterList == filters.get(1)) {
                    //sunt la gen
                    int noGenre = 0;
                    for (String filter : filterList) {


                        for (String genres : movie.getGenres()) {

                            if (filter != null && genres.equals(filter)) {
                                System.out.println("filter-- - :" + filter);
                                System.out.println(" --- genul " + genres);
                                System.out.println(" --- genul " + movie);
                                noGenre++;

                            }
                        }
                    }
                    //nu exista niciun gen in lista aia si sa fie egale
                    if (noGenre == 0)
                        add = 0;
                }
            }
            if (add == 1) {
                System.out.println(movie.getTitle() + movie.getYear());
                System.out.println(movie.getDuration());
                selected.add(movie);
            }


        }
        if (sort_type.equals("asc")) {
            Collections.sort(selected, new Comparator<Film>() {
                @Override
                public int compare(Film o1, Film o2) {
                    if(o1.getDuration() == o2.getDuration())
                        return o1.getTitle().compareTo(o2.getTitle());
                    return o1.getDuration() - o2.getDuration();
                }
            });
        }
        if (sort_type.equals("desc")) {
            Collections.sort(selected, new Comparator<Film>() {
                @Override
                public int compare(Film o1, Film o2) {
                    if(o1.getDuration() == o2.getDuration())
                        return o2.getTitle().compareTo(o1.getTitle());
                    return o2.getDuration() - o1.getDuration();
                }
            });
        }
        return "Query result: " + selected.toString();
    }

    public String LongestShow(int N, List<List<String>> filters, String sort_type) {

        for (List<String> f : filters) {
            System.out.println("->[pentru cand ma duc in longest " + f);
        }

        ArrayList<Show> movies = dataBaseInput.getShows();

        ArrayList<Show> selected = new ArrayList<>();

        int i = 0;
        for (Show movie : movies) {

            int add = 1;
            //am anul si genul
            for (List<String> filterList : filters) {

                if (filterList == null)
                    continue;
                //sunt la ani
                if (filterList == filters.get(0)) {
                    //am un singur an, deci e un singur elemennt in lista
                    for (String filter : filterList) {
                        if (filter != null && movie.getYear() != Integer.parseInt(filter))
                            add = 0;
                    }
                }

                if (filterList == filters.get(1)) {
                    //sunt la gen
                    int noGenre = 0;
                    for (String filter : filterList) {


                        for (String genres : movie.getGenres()) {

                            if (filter != null && genres.equals(filter)) {
                                System.out.println("filter-- - :" + filter);
                                System.out.println(" --- genul " + genres);
                                System.out.println(" --- genul " + movie);
                                noGenre++;

                            }
                        }
                    }
                    //nu exista niciun gen in lista aia si sa fie egale
                    if (noGenre == 0)
                        add = 0;
                }
            }
            if (add == 1) {
                System.out.println(movie.getTitle() + movie.getYear());
                System.out.println(movie.getDuration());
                selected.add(movie);
            }


        }
        if (sort_type.equals("asc")) {
            Collections.sort(selected, new Comparator<Show>() {
                @Override
                public int compare(Show o1, Show o2) {
                    if(o1.getDuration() == o2.getDuration())
                        return o1.getTitle().compareTo(o2.getTitle());
                    return o1.getDuration() - o2.getDuration();
                }
            });
        }
        if (sort_type.equals("desc")) {
            Collections.sort(selected, new Comparator<Show>() {
                @Override
                public int compare(Show o1, Show o2) {
                    if(o1.getDuration() == o2.getDuration())
                        return o2.getTitle().compareTo(o1.getTitle());
                    return o2.getDuration() - o1.getDuration();
                }
            });
        }
        return "Query result: " + selected.toString();
    }


    //Most Viewed - primele N video-uri sortate după numărul de vizualizări.
    // Fiecare utilizator are și o structură de date în care ține cont de câte ori a văzut un video.
    // În cazul sezoanelor se ia întregul serial ca și număr de vizualizări, nu independent pe sezoane.

    public String mostViewed(int N, List<List<String>> filters, String sortType, String videoType) {

        ArrayList<User> users = this.dataBaseInput.getUsers();
        HashMap<String, Integer> videoNames = new HashMap<>();

        for (User user : users) {
            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                //if it is a movie
                if (videoNames.containsKey(entry.getKey())) {
                    videoNames.put(entry.getKey(), videoNames.get(entry.getKey()).intValue() + entry.getValue());
                } else
                    videoNames.put(entry.getKey(), entry.getValue());
            }
        }

        HashMap<Film, Integer> selected = new HashMap<>();

        ArrayList<Film> movies = this.dataBaseInput.getFilms();
        for (Map.Entry<String, Integer> entry : videoNames.entrySet()) {
            for (Film film : movies) {
                if (film.getTitle().equals(entry.getKey())) {
                    selected.put(film, entry.getValue());
                }
            }
        }

        HashMap<Film, Integer> selectedAfterFilters = new HashMap<>();

        int i = 0;
        for (Map.Entry<Film, Integer> movie : selected.entrySet()) {

            int add = 1;
            //am anul si genul
            for (List<String> filterList : filters) {

                if (filterList == null)
                    continue;
                //sunt la ani


                if (filterList == filters.get(1)) {
                    //sunt la gen
                    int noGenre = 0;
                    for (String filter : filterList) {


                        for (String genres : movie.getKey().getGenres()) {

                            if (filter != null && genres.equals(filter)) {
                                System.out.println("filter-- - :" + filter);
                                System.out.println(" --- genul " + genres);
                                System.out.println(" --- genul " + movie);
                                noGenre++;

                            }
                        }
                    }
                    //nu exista niciun gen in lista aia si sa fie egale
                    if (noGenre == 0)
                        add = 0;
                }
            }
            if (add == 1) {

                String yearFilter = filters.get(0).get(0);
                //am un singur an, deci e un singur elemennt in lista

                if (yearFilter != null && movie.getKey().getYear() == Integer.parseInt(yearFilter)) {

                    System.out.println(" year movie " + movie.getKey().getYear() + " vlaue ? " + movie.getValue() + "filteryear : " + yearFilter + movie.getKey().getTitle());

                    selectedAfterFilters.put(movie.getKey(), movie.getValue());

                }
                if(yearFilter == null){
                    selectedAfterFilters.put(movie.getKey(), movie.getValue());
                }
            }
            if(add == 0){
                if(filters.get(0).get(0) != null)
                    if(movie.getKey().getYear() == Integer.parseInt(filters.get(0).get(0))){
                        selectedAfterFilters.put(movie.getKey(), movie.getValue());
                    }
            }

        }

        //reset the total views
        System.out.println("<<<<<<<<<<<<<<<<<<<");
        System.out.println(selectedAfterFilters);
        ArrayList<Film> sortedFilms = new ArrayList<Film>();
        for (Map.Entry<Film, Integer> movie : selectedAfterFilters.entrySet()) {
            movie.getKey().setTotalViews(movie.getValue());
            sortedFilms.add(movie.getKey());
        }

        if (sortType.equals("asc")) {
            Collections.sort(sortedFilms, new Comparator<Film>() {
                @Override
                public int compare(Film o1, Film o2) {
                    if(o1.getTotalViews() == o2. getTotalViews())
                        return o1.getTitle().compareTo(o2.getTitle());
                    return o1.getTotalViews() - o2.getTotalViews();
                }
            });
        }

        if (sortType.equals("desc")) {
            Collections.sort(sortedFilms, new Comparator<Film>() {
                @Override
                public int compare(Film o1, Film o2) {
                    if(o1.getTotalViews() == o2. getTotalViews())
                        return o2.getTitle().compareTo(o1.getTitle());
                    return o2.getTotalViews() - o1.getTotalViews();
                }
            });
        }

        System.out.println("caut asta s ci tot");

        for(Film film : sortedFilms) {
            System.out.println(film.getTitle() + film.getTotalViews());
        }

        return "Query result: " + sortedFilms.toString();
    }


    public String mostViewedShows(int N, List<List<String>> filters, String sortType, String videoType) {

        ArrayList<User> users = this.dataBaseInput.getUsers();
        //si userii au vieouri

        //for each user get his favorite films of viedos

        HashMap<String, Integer> videoNames = new HashMap<>();

        for (User user : users) {
            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {

                //if it is a movie
                if (videoNames.containsKey(entry.getKey())) {
                    videoNames.put(entry.getKey(), videoNames.get(entry.getKey()).intValue() + entry.getValue());
                } else
                    videoNames.put(entry.getKey(), entry.getValue());

            }
        }


        HashMap<Show, Integer> selected = new HashMap<>();

        ArrayList<Show> movies = this.dataBaseInput.getShows();
        for (Map.Entry<String, Integer> entry : videoNames.entrySet()) {
            for (Show film : movies) {
                if (film.getTitle().equals(entry.getKey())) {
                    selected.put(film, entry.getValue());
                }
            }
        }


        HashMap<Show, Integer> selectedAfterFilters = new HashMap<>();

        int i = 0;
        for (Map.Entry<Show, Integer> movie : selected.entrySet()) {

            int add = 1;
            int exist = 0;
            //am anul si genul
            for (List<String> filterList : filters) {

                if (filterList == null)
                    continue;
                //sunt la ani


                if (filterList == filters.get(1)) {
                    //sunt la gen
                    int noGenre = 0;
                    for (String filter : filterList) {


                        for (String genres : movie.getKey().getGenres()) {
                            if (filter != null && genres.equals(filter)) {
                                System.out.println("filter-- - :" + filter);
                                System.out.println(" --- genul " + genres);
                                System.out.println(" --- genul " + movie);
                                noGenre++;
                                exist = 1;
                            }
                        }
                    }
                    //nu exista niciun gen in lista aia si sa fie egale
                    if (noGenre == 0)
                        add = 0;
                }
            }
            if (add == 1) {

                String yearFilter = filters.get(0).get(0);
                //am un singur an, deci e un singur elemennt in lista

                if (yearFilter != null && movie.getKey().getYear() == Integer.parseInt(yearFilter)) {

                    System.out.println(" year movie " + movie.getKey().getYear() + " vlaue ? " + movie.getValue() + "filteryear : " + yearFilter + movie.getKey().getTitle());

                    selectedAfterFilters.put(movie.getKey(), movie.getValue());

                }
                if(yearFilter == null){
                    selectedAfterFilters.put(movie.getKey(), movie.getValue());
                }
            }
            if(filters.get(0).get(0) == null && exist != 0){
                System.out.println("?????????????");
                selectedAfterFilters.put(movie.getKey(), movie.getValue());
            }
        }

        System.out.println("nr" + N);
        //reset the total views

        System.out.println(selectedAfterFilters);
        ArrayList<Show> sortedFilms = new ArrayList<Show>();
        for (Map.Entry<Show, Integer> movie : selectedAfterFilters.entrySet()) {
            movie.getKey().setTotalViews(movie.getValue());
            sortedFilms.add(movie.getKey());
        }

        if (sortType.equals("asc")) {
            Collections.sort(sortedFilms, new Comparator<Show>() {
                @Override
                public int compare(Show o1, Show o2) {
                    if(o1.getTotalViews() == o2. getTotalViews())
                        return o1.getTitle().compareTo(o2.getTitle());
                    return o1.getTotalViews() - o2.getTotalViews();
                }
            });
        }

        if (sortType.equals("desc")) {
            Collections.sort(sortedFilms, new Comparator<Show>() {
                @Override
                public int compare(Show o1, Show o2) {
                    if(o1.getTotalViews() == o2. getTotalViews())
                        return o2.getTitle().compareTo(o1.getTitle());
                    return o2.getTotalViews() - o1.getTotalViews();
                }
            });
        }


        return "Query result: " + sortedFilms.toString();
    }




}



