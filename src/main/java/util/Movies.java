package util;

import java.io.Serializable;

public class Movies implements Serializable {
    private String title;
    private int yearOfRelease;
    private String [] genre = new String[3];
    private int runningTime;
    private String productionCompany;
    private long budget;
    private long revenue;
    private long profit;

    public static boolean isNewMovieAdded = false;

    public Movies(String[] movieInfo)
    {
        int n = 0;
        title = (movieInfo[n++]);
        yearOfRelease = Integer.parseInt(movieInfo[n++]);

        int i ;
        for(i = 0; i < 3; i++)
        {
            genre[i] = (movieInfo[n++]);

        }

        runningTime = Integer.parseInt(movieInfo[n++]);

        productionCompany = new String(movieInfo[n++]);
        budget = Long.parseLong(movieInfo[n++]);
        revenue = Long.parseLong(movieInfo[n]);
        profit = revenue - budget;
    }

    public Movies(String title, int yearOfRelease, String[] genre, int runningTime, String productionCompany, long budget, long revenue) {
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
        this.runningTime = runningTime;
        this.productionCompany = productionCompany;
        this.budget = budget;
        this.revenue = revenue;
        this.profit = revenue - budget;
    }

    public String getTitle() {
        return title;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public String[] getGenre() {
        return genre;
    }
    public String getGenre1() { return genre[0];}
    public String getGenre2() { return genre[1];}
    public String getGenre3() { return genre[2];}

    public int getRunningTime() {
        return runningTime;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public long getBudget() {
        return budget;
    }

    public long getRevenue() {
        return revenue;
    }

    public long getProfit() { return profit; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public void setGenre(String[] genre) {
        this.genre = genre;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public void setProfit(long profit) {
        this.profit = profit;
    }

    void printMovieInfo()
    {
        System.out.printf("Title: %s\n",title);
        System.out.printf("Release Year: %d\n",yearOfRelease);
        System.out.printf("Genre: %s,%s,%s\n",genre[0],genre[1],genre[2]);
        System.out.printf("Running time: %d\n",runningTime);
        System.out.printf("Production Company: %s\n",productionCompany);
        System.out.printf("Budget: %d\n",budget);
        System.out.printf("Revenue: %d\n\n",revenue);
    }

    }



