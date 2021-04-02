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
    int bankCounter = 0;
    //trade with bank
    public boolean tradeWithBank = false;
    //import images
    private Image lumber = new ImageIcon("src/main/java/Lumberbig.png").getImage();
    private Image brick = new ImageIcon("src/main/java/brickbig.png").getImage();
    private Image sheep = new ImageIcon("src/main/java/sheepbig.png").getImage();
    private Image wheat = new ImageIcon("src/main/java/wheatbig.png").getImage();
    private Image ore = new ImageIcon("src/main/java/orebig.png").getImage();


    public Trade(){

    }
    //check if we can trade
    public boolean canTrade(Player p){
        for (int i = 1; i < 6; i ++){
            if (-resources[i] > p.resourceNum(i)){
                return false;
            }
        }
        return true;
    }
    //change trade with bank
    public void makeBank(Player p){
        if (tradeWithBank){
            tradeWithBank = false;
        } else {
            tradeWithBank = true;
        }
        bankCounter = 0;
    }

    public void createTrade(int x, int y, Player p){
        //if we are trading with bank
        if (tradeWithBank){
            //check each resource
            for (int i = 1; i < 6; i ++){
                //add resource: if we are negative, reduce it by the trading amount
                //otherwise add one
                if (i*100+400 < x && x < i*100 + 500 && 600 < y && y < 700){
                    if (resources[i] >= 0){
                        resources[i] ++;
                    } else {
                        resources[i] += p.tradevals[i];
                    }
                    bankCounter -= 1;
                }
                //similar thing happens here: if we are positive, reduce it by the normal amount
                //otherwise reduce it by the trading amount
                if (i*100+400 < x && x < i*100 + 500 && 700 < y && y < 800){
                    if (resources[i] > 0){
                        resources[i] --;
                    } else {
                        resources[i] -= p.tradevals[i];
                    }
                    bankCounter ++;
                }
            }
            return;
        }
        //if we arent trading with the bank, trade like normal
        for (int i = 1; i < 6; i ++){
            //update resources
            if (i*100+400 < x && x < i*100 + 500 && 600 < y && y < 700){
                resources[i] += 1;
            }
            if (i*100+400 < x && x < i*100 + 500 && 700 < y && y < 800){
                resources[i] -= 1;
            }
        }
    }

    public void displayTrade(Graphics g){
        //for each trade
        for (int type = 1; type < 6; type ++) {
            //get the centerx and centery of each image position
            int centerx = type*100+400;
            int centery = 600;
            //draw each resource image
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
            //add 100 to centery and do the exact same thing but 100 down
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
            //draw all the rects boxes
            g.drawRect(type * 100 + 400, 700, 100, 100);
            g.drawRect(type * 100 + 400, 600, 100, 100);
            //if we have position resources +
            if (resources[type] > 0){
                g.drawString(""+resources[type],type * 100 + 400+30, 670);
                //if we have position resources -
            } else if (resources[type] < 0){
                g.drawString(""+(-resources[type]),type * 100 + 400+30, 770);
            }
        }
    }


}
