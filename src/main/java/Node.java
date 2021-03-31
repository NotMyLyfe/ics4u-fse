import java.util.*;
import java.awt.*;
public class Node{

    Hexagon hex;
    int p;
    boolean isSettlement = false;
    boolean isCity = false;
    Color c;


    public Node(Hexagon h, int pp){
        p = pp;
        hex = h;
    }

    public boolean equalNode(Node n){
        int x1 = hex.hexagonNode(p)[0];
        int y1 = hex.hexagonNode(p)[1];
        int x2 = n.hex.hexagonNode(n.p)[0];
        int y2 = n.hex.hexagonNode(n.p)[1];
        return (Math.pow(x1 - x2, 2) + Math.pow(y1-y2,2) < 100);
    }

    public void placeSettlement(Color cc){
        if (isCity){
            return;
        }
        isSettlement = true;
        c = cc;
    }

    public void placeCity(){
        isCity = true;
        isSettlement = false;
    }

    public void draw(Graphics g){
        int x1 = hex.hexagonNode(p)[0];
        int y1 = hex.hexagonNode(p)[1];
        g.setColor(c);
        if (isSettlement) {
            g.fillRect(x1-7,y1-7,14,14);
        }
        if (isCity){
            g.fillOval(x1 - 10, y1 - 10, 20, 20);
        }
    }


}
