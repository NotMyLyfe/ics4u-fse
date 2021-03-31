import java.awt.*;

public class Trade{
    private final int DESERT = 0,
            GRAIN = 1,
            LUMBER = 2,
            WOOL = 3,
            ORE = 4,
            BRICK = 5;
    int[] resources = {0,0,0,0,1,0};
    boolean isTrade = false;

    public Trade(){

    }

    public boolean canTrade(Player p){
        for (int i = 1; i < 6; i ++){
            if (-resources[i] > p.resourceNum(i)){
                return false;
            }
        }
        return true;
    }

    public void makeTrade(Player p){
        isTrade = true;
        for (int i = 1; i < 6; i ++){
            p.resources[i] += resources[i];
        }
    }

    public void createTrade(int x, int y){
        for (int i = 1; i < 6; i ++){

            if (i*100+400 < x && x < i*100 + 500 && 600 < y && y < 700){
                resources[i] += 1;
            }
            if (i*100+400 < x && x < i*100 + 500 && 700 < y && y < 800){
                resources[i] -= 1;
            }
        }
    }

    public void displayTrade(Graphics g){
        for (int i = 1; i < 6; i ++) {
            g.drawRect(i * 100 + 400, 700, 100, 100);
            g.drawRect(i * 100 + 400, 600, 100, 100);
            if (resources[i] > 0){
                g.drawString(""+resources[i],i * 100 + 400, 600);
            } else if (resources[i] < 0){
                g.drawString(""+(-resources[i]),i * 100 + 400, 700);
            }
        }
    }
}
