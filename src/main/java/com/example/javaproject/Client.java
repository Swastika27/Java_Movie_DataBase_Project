package com.example.javaproject;
import util.Movies;
import util.SocketWrapper;

import java.util.ArrayList;

public class Client {

    SocketWrapper socketWrapper;
    String clientName;
    ArrayList<Movies> movieList;
//    String[] productionCompanyNames;

    public SocketWrapper getSocketWrapper() {
        return socketWrapper;
    }

//    public String[] getProductionCompanyNames() {
//        return productionCompanyNames;
//    }

    Client(String serverAddress, int serverPort, String clientName){
        try{
            socketWrapper = new SocketWrapper(serverAddress, serverPort);
            this.clientName = clientName;
            socketWrapper.write(clientName);
            System.out.println(socketWrapper.read());
            socketWrapper.write("okay");

            try{
                movieList = (ArrayList<Movies>) socketWrapper.read();
                socketWrapper.write("okay");
//                productionCompanyNames = (String[]) socketWrapper.read();
//                System.out.println("okay after getting pc names");
                //PrintMovieList.printMovieList(movieList);
            } catch (Exception e){
                System.out.println(e);
            }


        } catch(Exception e) {
            System.out.println(e);
        }

    }

    public ArrayList<Movies> getMovieList() {
        return movieList;
    }
}
