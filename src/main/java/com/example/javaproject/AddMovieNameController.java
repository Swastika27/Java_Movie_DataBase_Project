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


public class AddMovieNameController {
    private ArrayList<Movies> movieList;
    @FXML private TextField title;

    public void setMovieList(ArrayList<Movies> movieList) {
        this.movieList = movieList;
    }


    public void onAddClick(ActionEvent actionEvent) throws IOException {

            String movieTitle = title.getText();

            if (alreadyExists(movieTitle)) {
                title.setText(null);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Movie already exists");
                alert.setHeaderText("Movie with this title already exists");
                alert.setContentText("The movie you are trying to add is already in this production company");
                alert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Add-Movie-Details.fxml"));
                Parent root = loader.load();

                AddMovieDetailsController addMovieDetailsController = loader.getController();
                try {
                    addMovieDetailsController.setMovieList(movieList);
                    addMovieDetailsController.setTitle(movieTitle);
                } catch (Exception e) {
                    System.out.println(e);
                }

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 540, 400);
                String css = this.getClass().getResource("CSS.css").toExternalForm();
                scene.getStylesheets().add(css);
                stage.setScene(scene);


        }
      }

    public boolean alreadyExists(String title) {
        int size = 0;
        try{
            size = movieList.size();
        } catch (Exception e) {
            System.out.println("in addmovie scene " + e);
        }
            for (int i = 0; i < size; i++) {
                if (movieList.get(i).getTitle().equalsIgnoreCase(title))
                    return true;
            }
            return false;

    }

    public void onBackClick(ActionEvent actionEvent) throws IOException {
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
