package util;

import java.io.Serializable;
import java.util.ArrayList;

public class MoviePCWrapper implements Serializable {
    private ArrayList<Movies> movieList;
    private ArrayList<String> PCNames;

    public ArrayList<Movies> getMovieList() {
        return movieList;
    }

    public void setMovieList(ArrayList<Movies> movieList) {
        this.movieList = movieList;
    }

    public ArrayList<String> getPCNames() {
        return PCNames;
    }

    public void setPCNames(ArrayList<String> PCNames) {
        this.PCNames = PCNames;
    }
}
