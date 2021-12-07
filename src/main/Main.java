package main;

import Actions.DataBaseInput;
import Commands.*;
import actor.Actor;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import entertainment.Film;
import entertainment.Video;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation

        DataBaseInput data = new DataBaseInput(input);

        //pun in cealata parte

        for(Actor actor : data.getActors() ){
            actor.setFilmographyAsRef(data);
        }

        //System.out.println(input.getCommands().get(0));
        //filePath1.substring(60)

        System.out.println("\n\n\nNEW COMMAND \n ---------------------- " + filePath1.substring(61) + "\n" + input.getCommands());

        Comenzi commands = new Comenzi(data);
        for(ActionInputData command: data.getCommands()){

            int N = command.getNumber();
            String objectType = command.getObjectType();


            List<List<String> >filters = command.getFilters();
            for(List<String > f : filters){
                System.out.println("->[" + f);
            }
            String sortType = command.getSortType();
            String criteria = command.getCriteria();

            if(command.getActionType().equals(Constants.COMMAND)){


                if(command.getType().equals(Constants.FAVORITE)){
                    String returnString = commands.favorite(command.getTitle(), command.getUsername());

                    System.out.println(command.getUsername());
                   arrayResult.add(fileWriter.writeFile(command.getActionId(), "nu se ia in calcul", returnString));
                }
                if(command.getType().equals(Constants.VIEW)){
                    String returnString = commands.view(command.getTitle(), command.getUsername());
                    arrayResult.add(fileWriter.writeFile(command.getActionId(), "nu", returnString));
                }

                if(command.getType().equals(Constants.RATING)){

                    Video video = data.getVideo(command.getTitle());

                    String ceva = commands.rating(video, command.getTitle(), command.getGrade(), command.getSeasonNumber(), command.getUsername());
                    System.out.println(ceva);
                    arrayResult.add(fileWriter.writeFile(command.getActionId(), "olala", ceva));
                }
            }

            if(command.getActionType().equals(Constants.QUERY)){


                if(objectType.equals(Constants.MOVIES)){
                    if(criteria.equals("ratings")){
                        QueryVideo queryVideo = new QueryVideo(data);

                        String res = queryVideo.MovieRating(N, filters, sortType);
                        arrayResult.add(fileWriter.writeFile(command.getActionId(), "a", res));
                    }
                    if(criteria.equals("favorite")){
                        QueryVideo queryVideo = new QueryVideo(data);
                        String res = queryVideo.FavoriteMovie(N, filters, sortType);
                      // System.out.println("RESULT- >> > >: " + res);
                        arrayResult.add(fileWriter.writeFile(command.getActionId(), "a", res));
                    }
                    if(criteria.equals("longest")){
                        QueryVideo queryVideo = new QueryVideo(data);
                        String res = queryVideo.LongestVideo(N, filters, sortType);
                        // System.out.println("RESULT- >> > >: " + res);
                        arrayResult.add(fileWriter.writeFile(command.getActionId(), "a", res));
                    }
                    if(criteria.equals("most_viewed")){
                        QueryVideo queryVideo = new QueryVideo(data);
                        String res = queryVideo.mostViewed(N, filters, sortType, "movies");
                        // System.out.println("RESULT- >> > >: " + res);
                        arrayResult.add(fileWriter.writeFile(command.getActionId(), "a", res));
                    }

                }

                if(objectType.equals(Constants.SHOWS)){
                    if(criteria.equals("ratings")){
                        QueryVideo queryVideo = new QueryVideo(data);
                        String res = queryVideo.ShowRating(N, filters, sortType);
                        arrayResult.add(fileWriter.writeFile(command.getActionId(), "a", res));
                    }
                    if(criteria.equals("favorite")){
                        System.out.println("bb\n");
                        QueryVideo queryVideo = new QueryVideo(data);
                        String res = queryVideo.FavoriteShows(N, filters, sortType);
                       arrayResult.add(fileWriter.writeFile(command.getActionId(), "a", res));
                    }
                    if(criteria.equals("longest")){
                        QueryVideo queryVideo = new QueryVideo(data);
                        String res = queryVideo.LongestShow( N, filters, sortType);
                        arrayResult.add(fileWriter.writeFile(command.getActionId(), "a", res));
                    }
                    if(criteria.equals("most_viewed")){
                        QueryVideo queryVideo = new QueryVideo(data);
                        String res = queryVideo.mostViewedShows(N, filters, sortType, "movies");
                         System.out.println("RESULT- >> > >: " + res);
                        arrayResult.add(fileWriter.writeFile(command.getActionId(), "a", res));
                    }
                }


                if(objectType.equals(Constants.ACTORS)){
                    if(criteria.equals("average")) {
                        QueryActors queryActors = new QueryActors(data);
                        System.out.println("ratings" + command.getActionId());
                        String res = queryActors.compareActorsRating(sortType, N);
                        arrayResult.add(fileWriter.writeFile(command.getActionId(), "cva", res));

                    }
                    if(criteria.equals("awards")) {
                        QueryActors queryActors = new QueryActors(data);
                        String res = queryActors.compareActorsAwards(sortType, N, filters);
                        arrayResult.add(fileWriter.writeFile(command.getActionId(), "cva", res));

                    }

                    if(criteria.equals("filter_description")) {
                        QueryActors queryActors = new QueryActors(data);
                        String res = queryActors.compareActorsFilterDescription(sortType, N, filters);
                        arrayResult.add(fileWriter.writeFile(command.getActionId(), "cva", res));

                    }
                }
                if(objectType.equals(Constants.USERS)) {
                    if (criteria.equals("num_ratings")) {
                        QueryUsers queryUsers = new QueryUsers(data);
                        String res = queryUsers.numberOfRatings(N, sortType);
                        arrayResult.add(fileWriter.writeFile(command.getActionId(), "a", res));


                    }
                }
            }

            if(command.getActionType().equals(Constants.RECOMMENDATION)) {

                RStandard rStandard = new RStandard(data);
                if (command.getType().equals("standard")) {
                    arrayResult.add(fileWriter.writeFile(command.getActionId(), "A", rStandard.RecommendationStandard(command.getUsername())));
                }
                if (command.getType().equals("best_unseen")) {
                    arrayResult.add(fileWriter.writeFile(command.getActionId(), "A", rStandard.RecommendationBestUnseen(command.getUsername())));
                }
                if (command.getType().equals("search")) {
                    arrayResult.add(fileWriter.writeFile(command.getActionId(), "A", rStandard.Search(command.getUsername(), command.getGenre())));
                }
                if (command.getType().equals("popular")) {
                    arrayResult.add(fileWriter.writeFile(command.getActionId(), "A", rStandard.RecommendationPopular(command.getUsername())));
                }
                if (command.getType().equals("favorite")) {
                    RPremium rPremium = new RPremium(data);
                    arrayResult.add(fileWriter.writeFile(command.getActionId(), "A", rPremium.RecommendationFavorite(command.getUsername(), N, filters)));
                }
            }

        }


        fileWriter.closeJSON(arrayResult);
    }
}
