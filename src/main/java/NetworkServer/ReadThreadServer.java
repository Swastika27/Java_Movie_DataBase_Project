package NetworkServer;
import util.*;
import java.util.ArrayList;
import java.util.HashMap;


public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final SocketWrapper socketWrapper;
    public HashMap<String, SocketWrapper> clientMap;
    private ArrayList<Movies> allMovies;
    private HashMap<String, String> userMap;
    private ArrayList<String> pcNames;


    public ReadThreadServer(HashMap<String, SocketWrapper> map, SocketWrapper socketWrapper, ArrayList<Movies> allMovies,HashMap<String,String> userMap,ArrayList<String> pcNames) {
        this.clientMap = map;
        this.socketWrapper = socketWrapper;
        this.allMovies = allMovies;
        this.userMap = userMap;
        this.pcNames = pcNames;
        this.thr = new Thread(this);
        thr.start();
    }

    public ArrayList<Movies> SearchByProductionCompany(String pc)
    {
        ArrayList<Movies> GotList = new ArrayList();
        int size = allMovies.size();

        for(int i = 0; i < size; i++)
        {
            Movies m = allMovies.get(i);
            if(m.getProductionCompany().equalsIgnoreCase(pc))
            {
                GotList.add(m);
            }
        }
        return GotList;
    }

    public void run() {
        try {
            while (true) {
                Object o = socketWrapper.read();
                if (o != null) {

                        if (o instanceof LoginDTO) {
                        System.out.println("server received login dto");
                        LoginDTO loginDTO = (LoginDTO) o;
                        String password = userMap.get(loginDTO.getUserName().toLowerCase());
                        loginDTO.setStatus(loginDTO.getPassword().equals(password));
                        System.out.println(loginDTO.isStatus());
                        if(!loginDTO.isStatus()) {
                            System.out.println(loginDTO.isStatus());
                            System.out.println("password not matched");
                            socketWrapper.write(loginDTO.isStatus());
                        }

                        else {
                            String clientName = loginDTO.getUserName();
                            clientName = clientName.toLowerCase();
                            clientMap.put(clientName, socketWrapper);
                            System.out.println("password matched");

                            socketWrapper.write("Hello " + clientName);
                            if (((String) socketWrapper.read()).equals("okay")) {
                                try {
                                    MoviePCWrapper moviePCWrapper = new MoviePCWrapper();
                                    moviePCWrapper.setMovieList(SearchByProductionCompany(clientName));
                                    moviePCWrapper.setPCNames(pcNames);
                                    socketWrapper.write(moviePCWrapper);
                                    System.out.println(socketWrapper.read());
                                } catch (Exception e) {
                                    System.out.println("Server to client " + e);
                                }
                            }
                        }

                    }
                    else if(o instanceof Movies) {
                            System.out.println("Server received a movie");
                        allMovies.add((Movies) o);
                    }
                    else if(o instanceof TransferInfo){

                        String title = ((TransferInfo) o).getMovieTitle();
                        String destinationCompany = ((TransferInfo) o).getDestinationCompany().toLowerCase();
                        System.out.println("server has received the info");

                        Movies m = null;
                        for(Movies movie : allMovies) {
                            if(movie.getTitle().equalsIgnoreCase(title)) {
                                m = movie;
                                m.setProductionCompany(destinationCompany);
                                break;
                            }
                        }

                        clientMap.get(destinationCompany).write(m);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("in read thread server " + e);
        } finally {
            try {
                System.out.println(allMovies.size());
                FileOp fileOp = new FileOp();
                fileOp.writeToFile(allMovies);

                socketWrapper.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}