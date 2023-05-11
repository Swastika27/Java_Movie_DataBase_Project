package com.example.javaproject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Movies;
import util.SocketWrapper;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    private Stage stage;
    public static SocketWrapper socketWrapper;
    public static ArrayList<String> pcNames;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public SocketWrapper getSocketWrapper() {
        return socketWrapper;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        connectToServer();
        showWelcomeScene();
    }

    public void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        socketWrapper = new SocketWrapper(serverAddress, serverPort);
        new ReadThreadClient(this);
    }

    public void showWelcomeScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("welcome-view.fxml"));
            //MainSceneController controller = fxmlLoader.getController();

        Scene scene = new Scene(fxmlLoader.load(), 400, 330);

        String css = this.getClass().getResource("CSS.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Movie Database");
        stage.setScene(scene);
        stage.show();
    }

    public void showPCHomeScene(ArrayList<Movies> movieList) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PC-Home-view.fxml"));
        Parent root = loader.load();

        PCHomeSceneController controller = loader.getController();
        controller.getPCname().setText(movieList.get(0).getProductionCompany());
        controller.setMovieList(movieList);

        controller.init();

        Scene scene = new Scene(root,1000,510);
        String css = this.getClass().getResource("CSS.css").toExternalForm();
        stage.setTitle(movieList.get(0).getProductionCompany());
        scene.getStylesheets().add(css);
        stage.setScene(scene);
    }

    public static void main(String[] args) {

    launch();
    }
}