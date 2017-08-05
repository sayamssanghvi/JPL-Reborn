package com.zodiac.sanghvi.jplreborn;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Sanghvi on 6/9/17.
 */
public class Player
{
    String Name,TeamName,Img;
}

class Match
{
    String Team1,Team2,Img_Team1,Img_Team2;
}

class Player_Stats
{
    private int Age,Fifties,Hundreds,JPLPlayed,PBest,Runs,Thirties,Wickets;
    private String TeamName,Name;
    private String Img;

    public Player_Stats(){}

    public Player_Stats(String Name,String TeamName, int Age, int Fifties, int Hundreds, int JPLPlayed, int PBest, int Runs, int Thirties, int Wickets,String Img)
    {
        this.Age=Age;
        this.Name=Name;
        this.Img=Img;
        this.TeamName=TeamName;
        this.Fifties=Fifties;
        this.Runs=Runs;
        this.Hundreds=Hundreds;
        this.Thirties=Thirties;
        this.PBest=PBest;
        this.JPLPlayed=JPLPlayed;
        this.Wickets=Wickets;
    }


    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getFifties() {
        return Fifties;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setFifties(int fifties) {
        Fifties = fifties;
    }

    public int getHundreds() {
        return Hundreds;
    }

    public void setHundreds(int hundreds) {
        Hundreds = hundreds;
    }

    public int getJPLPlayed() {
        return JPLPlayed;
    }

    public void setJPLPlayed(int jplplayed) {
        this.JPLPlayed = jplplayed;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public int getPBest() {
        return PBest;
    }

    public void setPBest(int pbest) {
        this.PBest = pbest;
    }

    public int getRuns() {
        return Runs;
    }

    public void setRuns(int runs) {
        Runs = runs;
    }

    public int getThirties() {
        return Thirties;
    }

    public void setThirties(int thirties) {
        Thirties = thirties;
    }

    public int getWickets() {
        return Wickets;
    }

    public void setWickets(int wickets) {
        Wickets = wickets;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

}

class Bat_Bowl
{
    static String Batting,Bowling,Toss,Choose,Won;
}

class Game_play
{
    int Runs,Wickets;
}
