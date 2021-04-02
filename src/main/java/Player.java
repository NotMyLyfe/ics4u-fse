//Evan Lu
//Player.java
//general info about the player
import java.awt.*;
import java.util.ArrayList;

public class Player{
    private final int DESERT = 0,
            GRAIN = 1,
            LUMBER = 2,
            WOOL = 3,
            ORE = 4,
            BRICK = 5;
    public int[] resources = {0,0,0,0,0,0};
    public int[] tradevals = {0,4,4,4,4,4};


    public Color color;
    public boolean turn;
    public int victory = 0;
    public ArrayList<DevCard> devCards = new ArrayList<>();


    public Player(boolean tt, Color cc){
        //set the turn and cc
        turn = tt;
        color = cc;
    }

    public boolean win(){
        return victory >= 10;
    }

    public int resourceNum(int r){
        return resources[r];
    }

    public void acceptTrade(int[] trade){
        //update resources
        for (int i = 1; i < 6; i ++){
            resources[i] += trade[i];
        }
    }

    public void addDevCard(){
        //tell server and add a devCard
    }

    public void useDevCard(String type){
        //minus the dev card
        for (int i = 0; i < devCards.size(); i ++) {
            if (devCards.get(i).type.equals(type)) {
                devCards.remove(i);
                return;
            }
        }
    }

    public void playDevCard(int x,int y){
        for (int i = 0; i < devCards.size(); i ++){
            if (1000-100*i > x && x > 900-100*i && 700 < y && y < 900){
                useDevCard(devCards.get(i).type);
            }
        }
    }

    public void drawDevCards(Graphics g){
        //draw each dev card (100/200)
        for (int i = 0; i < devCards.size(); i ++){
            devCards.get(i).draw(g, 900-i*100,800);
        }
    }



    public void displayResources(Graphics g){
        for (int i = 1; i < 6; i ++){
            //for each resources display it
            g.setColor(Color.black);
            g.drawString(""+resources[i],i*80-40,750);
        }
    }
}
