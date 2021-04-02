//Evan Lu
//Edge.java
//Edge class between nodes, and includes road functionality
import java.awt.*;

public class Edge{
    //each edge is defined by its 2 nodes
    Node node1;
    Node node2;
    public boolean isRoad = false;
    Color c;

    public Edge(Node n1, Node n2){
        node1 = n1;
        node2 = n2;
    }
    //check equal edge by checking each pair of nodes
    public boolean equalEdge(Edge e){
        return ((node1.equalNode(e.node1) && node2.equalNode(e.node2)) || (node1.equalNode(e.node2) && node2.equalNode(e.node1)));
    }

    public void makeRoad(Color cc){
        //make road true and color
        isRoad = true;
        c = cc;
    }

    public void draw(Graphics g){
        g.setColor(c);
        //draw the road
        if (isRoad){
            //set the basic stroke
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            //draw the start point and end point
            g2.drawLine(node1.hex.hexagonNode(node1.p)[0],node1.hex.hexagonNode(node1.p)[1],node2.hex.hexagonNode(node2.p)[0],node2.hex.hexagonNode(node2.p)[1]);
            //set the basicstroke back to 1
            g2.setStroke(new BasicStroke(1));
        }
    }

}
