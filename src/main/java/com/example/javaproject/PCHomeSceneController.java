package com.example.javaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.Movies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static javafx.application.Platform.exit;

public class PCHomeSceneController {
    @FXML private Label PCname;
    @FXML private TableView<Movies> table;
    @FXML private TableColumn<Movies, String> titleCol;
    @FXML private TableColumn<Movies, Integer> releaseYearCol;
    @FXML private TableColumn<Movies, String> genre1Col;
    @FXML private TableColumn<Movies, String> genre2Col;
    @FXML private TableColumn<Movies, String> genre3Col;
    @FXML private TableColumn<Movies, Integer> runningTimeCol;
    @FXML private TableColumn<Movies, Long> budgetCol;
    @FXML private TableColumn<Movies, Long> revenueCol;

    private ArrayList<Movies> movieList;


    public void setMovieList(ArrayList<Movies> movieList) {
        this.movieList = movieList;
    }


    public Label getPCname() {
        return PCname;
    }


    @FXML private void initialize() {
        System.out.println("okay 1");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        releaseYearCol.setCellValueFactory(new PropertyValueFactory<>("yearOfRelease"));
        genre1Col.setCellValueFactory(new PropertyValueFactory<>("genre1"));
        genre2Col.setCellValueFactory(new PropertyValueFactory<>("genre2"));
        genre3Col.setCellValueFactory(new PropertyValueFactory<>("genre3"));
        runningTimeCol.setCellValueFactory(new PropertyValueFactory<>("runningTime"));
        budgetCol.setCellValueFactory(new PropertyValueFactory<>("budget"));
        revenueCol.setCellValueFactory(new PropertyValueFactory<>("revenue"));

        }
    public void init() {
        try {
            table.getItems().setAll(movieList);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void onRecentClick(ActionEvent actionEvent) {
        movieList.sort(Comparator.comparing(Movies::getYearOfRelease).reversed());
        ArrayList<Movies> searchResult = new ArrayList<>();

        int size = movieList.size();

        for (int i = 0; i < size ; i++) {
            if (movieList.get(0).getYearOfRelease() == movieList.get(i).getYearOfRelease())
            {
                searchResult.add(movieList.get(i));
            }
            else
                break;
        }

        table.getItems().clear();
        table.getItems().setAll(searchResult);
    }

    public void onMaxRevenueClick(ActionEvent actionEvent) {
        List<Movies> searchResult = new ArrayList();
        movieList.sort(Comparator.comparing(Movies::getRevenue).reversed());

        int size = movieList.size();

        for(int i = 0; i < size; i++)
        {
            if(movieList.get(0).getRevenue() == movieList.get(i).getRevenue())
            {
                searchResult.add(movieList.get(i));
            }
            else
                break;
        }

        table.getItems().clear();
        table.getItems().setAll(searchResult);
    }
    public void onTransferClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Transfer-Movie-Scene.fxml"));
        Parent root = loader.load();

        TransferMovieSceneController transferMovieSceneController = loader.getController();
        transferMovieSceneController.setMovieList(movieList);
        try {
            transferMovieSceneController.init();
            //addMovieDetailsController.setTitle(movieTitle);
        } catch (Exception e) {
            System.out.println(e);
        }

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,600,400);
        String css = this.getClass().getResource("CSS.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
    }

    public void onExitClick(ActionEvent actionEvent) throws IOException {
        //Main.socketWrapper.write("EXIT");
        exit();
    }

    public void onAddMovieClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Add-Movie-Name.fxml"));
        Parent root = loader.load();

        AddMovieNameController addMovieNameController = loader.getController();
        addMovieNameController.setMovieList(movieList);


        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,525,340);
        String css = this.getClass().getResource("CSS.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
    }

    public void viewProfileClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Profile-scene.fxml"));
        Parent root = loader.load();

        ProfileSceneController controller = loader.getController();
        controller.setMovieList(movieList);
        controller.getPCname().setText(movieList.get(0).getProductionCompany());
        controller.getMovieNumText().setText(String.valueOf(movieList.size()));
        controller.getBudgetText().setText(String.valueOf(totalBudget()));
        controller.getRevenueText().setText(String.valueOf(totalRevenue()));
        controller.getProfitText().setText(String.valueOf(totalProfit()));

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,600,400);
        String css = this.getClass().getResource("CSS.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
    }

    public void onAllMoviesClick(ActionEvent actionEvent) {
        table.getItems().clear();
        table.getItems().setAll(movieList);
    }

    public long totalBudget()
    {
        long totalBudget = 0;
        for(Movies m: movieList) {
            totalBudget += m.getBudget();
        }
        return totalBudget;
    }
    public long totalProfit()
    {
        long totalProfit = 0;
        for(Movies m: movieList) {
            totalProfit += m.getProfit();
        }
        return totalProfit;
    }
    public long totalRevenue()
    {
        long totalRevenue = 0;
        for(Movies m: movieList) {
            totalRevenue += m.getRevenue();
        }
        return totalRevenue;
    }
}
