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
    public boolean tradeWithBank = false;
    public Color color;
    public boolean turn;
    public int victory = 0;
    public ArrayList<DevCard> devCards = new ArrayList<>();


    public Player(boolean tt, Color cc){
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
        for (int i = 1; i < 6; i ++){
            resources[i] += trade[i];
        }
    }

    public void addDevCard(){
        //tell server and add a devCard
    }

    public void useDevCard(String type){
        for (int i = 0; i < devCards.size(); i ++) {
            if (devCards.get(i).type.equals(type)) {
                devCards.remove(i);
                return;
            }
        }
    }

    public void drawDevCards(Graphics g){
        for (int i = 0; i < devCards.size(); i ++){
            devCards.get(i).draw(g, 900-i*100,800);
        }
    }

    public void playDevCard(int x, int y){
        for (int i = 0; i < devCards.size(); i ++){

        }
    }

    public void displayResources(Graphics g){
        for (int i = 1; i < 6; i ++){
            g.setColor(Color.black);
            g.drawString(""+resources[i],i*100-50,750);
        }
    }
}
