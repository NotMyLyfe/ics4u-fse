import javax.swing.*;
import java.awt.*;

public class Hexagon {
    public int type;
    public int num;
    public int x,y;
    private final int zerox = 200, zeroy = 200;
    private final int size = 60;

    private final Color yellow = Color.yellow;
    private final Color lightGreen = Color.green;
    private final Color darkGreen = new Color(0,102,0);
    private final Color gray = Color.gray;
    private final Color brown = new Color(204,102,0);
    private final Color sand = new Color(255,229,204);

    private Image lumber = new ImageIcon("src/main/java/lumber.png").getImage();
    private Image brick = new ImageIcon("src/main/java/brick.png").getImage();
    private Image sheep = new ImageIcon("src/main/java/sheep.png").getImage();
    private Image wheat = new ImageIcon("src/main/java/wheat.png").getImage();
    private Image ore = new ImageIcon("src/main/java/ore.png").getImage();



    private final int DESERT = 0,
            GRAIN = 1,
            LUMBER = 2,
            WOOL = 3,
            ORE = 4,
            BRICK = 5;

    public Hexagon(int[] info){
        type = info[2];
        num = info[3];
        x = info[0];
        y = info[1];
    }

    public void drawHex(Graphics g, Font f){
        int centerx = (int) (zerox + x*size*Math.sqrt(3) - y*size*Math.sqrt(3)/2);
        int centery = zeroy + y*size*3/2;
        int[] xpoints = new int[6];
        int[] ypoints = new int[6];
        for (int i = 0; i < 6; i ++){
            xpoints[i] = (int) (centerx + size*Math.cos(Math.PI*i/3+Math.PI/6));
            ypoints[i] = (int) (centery + size*Math.sin(Math.PI*i/3+Math.PI/6));
        }



        if (type == GRAIN){
            g.setColor(yellow);
        } else if (type == LUMBER){
            g.setColor(darkGreen);
        } else if (type == WOOL){
            g.setColor(lightGreen);
        } else if (type == ORE){
            g.setColor(gray);
        } else if (type == BRICK){
            g.setColor(brown);
        } else {
            g.setColor(sand);
        }


        g.fillPolygon(xpoints, ypoints, 6);
        g.setColor(Color.black);
        g.drawPolygon(xpoints, ypoints, 6);
        if (type == GRAIN){
            g.drawImage(wheat,centerx-25,centery-25,null);
        } else if (type == LUMBER){
            g.drawImage(lumber,centerx-25,centery-25,null);
        } else if (type == WOOL){
            g.drawImage(sheep,centerx-25,centery-25,null);
        } else if (type == ORE){
            g.drawImage(ore,centerx-25,centery-25,null);
        } else if (type == BRICK){
            g.drawImage(brick,centerx-25,centery-25,null);
        } else {
            //g.setColor(sand);
        }
        g.setFont(f);
        g.drawString(""+num, centerx-15,centery+10);
    }


    public int[] hexagonNode(int dir){
        int centerx = (int) (zerox + x*size*Math.sqrt(3) - y*size*Math.sqrt(3)/2);
        int centery = zeroy + y*size*3/2;
        int[] xpoints = new int[6];
        int[] ypoints = new int[6];
        for (int i = 0; i < 6; i ++){
            xpoints[i] = (int) (centerx + size*Math.cos(-Math.PI*i/3+Math.PI/2));
            ypoints[i] = (int) (centery + size*Math.sin(-Math.PI*i/3+Math.PI/2));
        }
        return new int[] {xpoints[dir], ypoints[dir]};
    }


}
