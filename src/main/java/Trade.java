import javax.swing.*;
import java.awt.*;

public class Trade{
    private final int DESERT = 0,
            GRAIN = 1,
            LUMBER = 2,
            WOOL = 3,
            ORE = 4,
            BRICK = 5;
    int[] resources = {0,0,0,0,0,0};
    boolean isTrade = false;

    private Image lumber = new ImageIcon("src/main/java/Lumberbig.png").getImage();
    private Image brick = new ImageIcon("src/main/java/brickbig.png").getImage();
    private Image sheep = new ImageIcon("src/main/java/sheepbig.png").getImage();
    private Image wheat = new ImageIcon("src/main/java/wheatbig.png").getImage();
    private Image ore = new ImageIcon("src/main/java/orebig.png").getImage();


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

    public void makeBank(Player p){

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
            int centerx = i*100+400;
            int centery = 600;
            int type = i;
            if (type == GRAIN){
                g.drawImage(wheat,centerx,centery,null);
            } else if (type == LUMBER){
                g.drawImage(lumber,centerx,centery,null);
            } else if (type == WOOL){
                g.drawImage(sheep,centerx,centery,null);
            } else if (type == ORE){
                g.drawImage(ore,centerx,centery,null);
            } else if (type == BRICK){
                g.drawImage(brick,centerx,centery,null);
            } else {
                //g.setColor(sand);
            }
            centery += 100;
            if (type == GRAIN){
                g.drawImage(wheat,centerx,centery,null);
            } else if (type == LUMBER){
                g.drawImage(lumber,centerx,centery,null);
            } else if (type == WOOL){
                g.drawImage(sheep,centerx,centery,null);
            } else if (type == ORE){
                g.drawImage(ore,centerx,centery,null);
            } else if (type == BRICK){
                g.drawImage(brick,centerx,centery,null);
            } else {
                //g.setColor(sand);
            }
            g.drawRect(i * 100 + 400, 700, 100, 100);
            g.drawRect(i * 100 + 400, 600, 100, 100);
            if (resources[i] > 0){
                g.drawString(""+resources[i],i * 100 + 400+30, 670);
            } else if (resources[i] < 0){
                g.drawString(""+(-resources[i]),i * 100 + 400+30, 770);
            }
        }
    }


}
