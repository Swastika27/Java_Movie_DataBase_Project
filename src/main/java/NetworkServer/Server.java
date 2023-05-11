package NetworkServer;

import util.Movies;
import util.SocketWrapper;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class Server {
    private ServerSocket serverSocket;
    private HashMap<String, SocketWrapper> clientMap;
    private ArrayList<Movies> allMovies;
    private HashMap<String, String> userMap;
    private ArrayList<String> pcNames;

    Server() throws Exception {
        clientMap = new HashMap<>();
        FileOp fileOp = new FileOp();
        allMovies = fileOp.readFromFile();
        System.out.println(allMovies.size());
        pcNames = new ArrayList<>();
        userMap = new HashMap<>();
        for(Movies m:allMovies) {
            boolean isAlreadyThere =false;
            for(String sr:userMap.keySet()){
                if(sr.equalsIgnoreCase(m.getProductionCompany())){
                    isAlreadyThere=true;
                    break;
                }
            }
            if(!isAlreadyThere){
                userMap.put(m.getProductionCompany().toLowerCase(),"abc");
                pcNames.add(m.getProductionCompany());
            }
        }

        //ReadFromFileThread thr = new ReadFromFileThread();


        try{
            serverSocket = new ServerSocket(33333);

            while(true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts: " + e);
        }

    }

    public void serve(Socket clientSocket) throws IOException {
        SocketWrapper socketWrapper = new SocketWrapper(clientSocket);

        new ReadThreadServer(clientMap,socketWrapper,allMovies,userMap,pcNames);

    }




    public static void main(String[] args) throws Exception { new Server(); }
}
