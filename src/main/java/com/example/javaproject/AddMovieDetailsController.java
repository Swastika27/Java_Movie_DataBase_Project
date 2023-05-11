package com.example.javaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Movies;

import java.io.IOException;
import java.util.ArrayList;

public class AddMovieDetailsController {
    private ArrayList<Movies> movieList;
    private String title;
    @FXML TextField releaseYearText;
    @FXML TextField genreText;
    @FXML TextField runningTimeText;
    @FXML TextField budgetText;
    @FXML TextField revenueText;
    public void setMovieList(ArrayList<Movies> movieList) {
        this.movieList = movieList;
    }

    public void setTitle(String title) {this.title = title;}

    public void onAddClick(ActionEvent actionEvent) throws IOException {

        try {
            String[] genre = new String[3];
            String[] genre2 = genreText.getText().split(",");
            for(int i = 0; i < genre2.length; i++) {
                genre[i] = genre2[i];
            }
            System.out.println(genre[0] + genre[1] + genre[2]);

            int releaseYear = Integer.parseInt(releaseYearText.getText());
            int runningTime = Integer.parseInt(runningTimeText.getText());
            String productionCompany = movieList.get(0).getProductionCompany();
            long budget = Long.parseLong(budgetText.getText());
            long revenue = Long.parseLong(revenueText.getText());

            Movies m = new Movies(title, releaseYear, genre, runningTime, productionCompany, budget, revenue);

            try {
                movieList.add(m);
            } catch (Exception e) {
                System.out.println("while adding to local list " + e);
            }
            try {
                Main.socketWrapper.write(m);
            } catch (Exception e) {
                System.out.println("on add click in details controller" + e);
            }
            loadHomeScene(actionEvent);
        } catch (Exception e) {
            System.out.println("while parsing movie details " + e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Number format error");
            alert.setHeaderText("Attempt to input string instead of number");
            alert.setContentText("Please enter number for Release Year,Running Time, Budget and Revenue." );
            alert.showAndWait();
        }

        }


    public void loadHomeScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PC-Home-view.fxml"));
        Parent root = loader.load();

        PCHomeSceneController controller = loader.getController();
        controller.setMovieList(movieList);
        controller.getPCname().setText(movieList.get(0).getProductionCompany());

        controller.init();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1000,510);
        String css = this.getClass().getResource("CSS.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
    }
}
