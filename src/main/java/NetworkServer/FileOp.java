package NetworkServer;

import util.Movies;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileOp {
     ArrayList<Movies> moviesList = new ArrayList();

    private static final String INPUT_FILE_NAME = "movies.txt";
    private static final String OUTPUT_FILE_NAME = "movies.txt";

    public ArrayList<Movies>  readFromFile() throws Exception
    {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        while(true)
        {
            String line = br.readLine();
            if(line == null)
                break;
            else
            {
                String[] movieInfo = line.split(",");
                Movies m = new Movies(movieInfo);
                moviesList.add(m);
                //m.printMovieInfo();
            }
        }
        br.close();
        return moviesList;
    }

    public void writeToFile(ArrayList<Movies> moviesList) throws Exception
    {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        int size = moviesList.size();
        for(int i = 0; i < size; i++)
        {
            Movies m = moviesList.get(i);
            bw.write(m.getTitle()+","+m.getYearOfRelease()+","+m.getGenre1()+","+m.getGenre2()+","+m.getGenre3()+","+
                    m.getRunningTime()+","+m.getProductionCompany()+","+m.getBudget()+","+m.getRevenue());
            bw.write(System.lineSeparator());
        }
        bw.close();
    }
}
