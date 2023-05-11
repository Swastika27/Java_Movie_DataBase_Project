package util;

import util.Movies;

import java.io.Serializable;

public class TransferInfo implements Serializable {
    private String movieTitle;
    private String destinationCompany;

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getDestinationCompany() {
        return destinationCompany;
    }

    public void setDestinationCompany(String destinationCompany) {
        this.destinationCompany = destinationCompany;
    }
}
