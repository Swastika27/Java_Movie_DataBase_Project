package com.example.javaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import util.Movies;

import java.io.IOException;
import java.util.ArrayList;

public class ProfileSceneController {
    @FXML private Label PCname;
    @FXML private Label movieNumText;
    @FXML private Label budgetText;
    @FXML private Label revenueText;
    @FXML private Label profitText;
    private ArrayList<Movies> movieList;

    public Label getPCname() {
        return PCname;
    }

    public Label getMovieNumText() {
        return movieNumText;
    }

    public Label getBudgetText() {
        return budgetText;
    }

    public Label getRevenueText() {
        return revenueText;
    }

    public Label getProfitText() {
        return profitText;
    }

    public void setMovieList(ArrayList<Movies> movieList) {
        this.movieList = movieList;
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
