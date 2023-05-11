package com.example.javaproject;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import util.MoviePCWrapper;
import util.Movies;
import java.io.IOException;
import java.util.ArrayList;

public class ReadThreadClient implements Runnable {
    private final Thread thr;
    private final Main main;
    private ArrayList<Movies> movieList;

    public ReadThreadClient(Main main) {
        this.main = main;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = Main.socketWrapper.read();
                if (o != null) {
                    if(o instanceof String) {
                        System.out.println((String) o);
                        main.getSocketWrapper().write("okay");
                    }
                    if (o instanceof Movies) {
                        System.out.println("got transfer info");
                        movieList.add((Movies) o);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    main.showPCHomeScene(movieList);
                                } catch (IOException e) {
                                    System.out.println("showing pc home scene " + e);
                                }
                            }
                        });

                    }
                    else if (o instanceof Boolean) {
                        System.out.println("Read Thread Client has received boolean");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Incorrect credentials");
                                    alert.setHeaderText("The username and password you provided is not correct");
                                    alert.setContentText("" );
                                    alert.showAndWait();
                                } catch (Exception e) {
                                    System.out.println("in alert "+ e);
                                }
                            }
                        });
                    }
                    else if (o instanceof MoviePCWrapper){
                        MoviePCWrapper moviePCWrapper = (MoviePCWrapper)o;

                        movieList = moviePCWrapper.getMovieList();
                        Main.pcNames = moviePCWrapper.getPCNames();
                        if(movieList.size() != 0) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        main.showPCHomeScene(movieList);
                                    } catch (IOException e) {
                                        System.out.println("showing pc home scene " + e);
                                    }
                                }
                            });
                        }
                        else {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("No such production company");
                                        alert.setHeaderText("No such production company exists");
                                        alert.setContentText("" );
                                        alert.showAndWait();
                                    } catch (Exception e) {
                                        System.out.println("in alert "+ e);
                                    }
                                }
                            });
                        }
                        main.getSocketWrapper().write("okay server");

                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {

                main.getSocketWrapper().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
