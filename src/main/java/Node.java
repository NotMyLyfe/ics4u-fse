import java.util.*;
import java.awt.*;
public class Node{
    //setup stuff
    Hexagon hex;
    int p;
    boolean isSettlement = false;
    boolean isCity = false;
    Color c;

    //update positino of the hexagon and the hexagon
    public Node(Hexagon h, int pp){
        p = pp;
        hex = h;
    }

    public boolean equalNode(Node n){
        //get the x's and y's of the 2 nodes
        int x1 = hex.hexagonNode(p)[0];
        int y1 = hex.hexagonNode(p)[1];
        int x2 = n.hex.hexagonNode(n.p)[0];
        int y2 = n.hex.hexagonNode(n.p)[1];
        //check if they are within range
        return (Math.pow(x1 - x2, 2) + Math.pow(y1-y2,2) < 100);
    }

    public boolean equalNode(int x, int y, int p){
        //get info and send it to a hexagon
        int[] infos = {x,y,0,0};
        Hexagon testhex = new Hexagon(infos);
        //setup node
        Node testnode = new Node(testhex,p);
        //return the equalNode
        return equalNode(testnode);
    }

    public void placeSettlement(Color cc){
        //if it is a city were done
        if (isCity){
            return;
        }
        //otherwise make it a settlement
        isSettlement = true;
        c = cc;
    }

    public void placeCity(){
        //if we place city set isCity to true and isSettlement to false
        isCity = true;
        isSettlement = false;
    }

    public void draw(Graphics g){
        //get the node position
        int x1 = hex.hexagonNode(p)[0];
        int y1 = hex.hexagonNode(p)[1];
        g.setColor(c);
        //display the 2 different things
        if (isSettlement) {
            g.fillRect(x1-7,y1-7,14,14);
        }
        if (isCity){
            g.fillOval(x1 - 20, y1 - 20, 40, 40);
        }
    }


}
