import java.awt.*;

public class Player{
    private final int DESERT = 0,
            GRAIN = 1,
            LUMBER = 2,
            WOOL = 3,
            ORE = 4,
            BRICK = 5;
    public int[] resources = {0,0,0,0,0,0};
    public Color color;
    public boolean turn;
    public int victory = 0;


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
}
