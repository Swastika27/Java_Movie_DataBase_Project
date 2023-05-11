package com.example.javaproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Movies;
import util.TransferInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TransferMovieSceneController {
private ArrayList<Movies> movieList;
    @FXML private ChoiceBox<String> movieSelectBox;
    @FXML private ChoiceBox<String> companySelectionBox;
    @FXML private TextField pcText;


    public void setMovieList(ArrayList<Movies> movieList) {
        this.movieList = movieList;
    }

    public void init() {
        try{
            for (int i = 0; i < movieList.size(); i++) {
                movieSelectBox.getItems().add(movieList.get(i).getTitle());
            }
            Collections.sort(Main.pcNames);
            for (int i = 0; i < Main.pcNames.size(); i++) {
                companySelectionBox.getItems().add(Main.pcNames.get(i));
            }
        } catch (Exception e) {
            System.out.println("in adding movie names " + e);
        }
    }

    public void onConfirmClick(ActionEvent actionEvent) throws IOException {
        TransferInfo data = new TransferInfo();
        data.setMovieTitle(movieSelectBox.getValue());
        data.setDestinationCompany(companySelectionBox.getValue());
        System.out.println("receiver " + data.getDestinationCompany() + " Movie " + data.getMovieTitle());
        try {
            movieList.remove(searchByTitle(data.getMovieTitle()));
            Main.socketWrapper.write(data);
        } catch (Exception e) {
            System.out.println("on sender end " + e);
        }
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        showPCHomeScene(movieList,stage);
    }
    public void showPCHomeScene(ArrayList<Movies> movieList, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PC-Home-view.fxml"));
        Parent root = loader.load();

        PCHomeSceneController controller = loader.getController();
        controller.getPCname().setText(movieList.get(0).getProductionCompany());
        controller.setMovieList(movieList);

        controller.init();

        Scene scene = new Scene(root,1000,510);
        String css = this.getClass().getResource("CSS.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
    }

    public Movies searchByTitle(String title) {
        for(Movies m : movieList) {
            if(m.getTitle().equalsIgnoreCase(title))
                return m;
        }
        return null;
    }
}
