import java.awt.*;

public class Edge{
    Node node1;
    Node node2;
    public boolean isRoad = false;
    Color c;

    public Edge(Node n1, Node n2){
        node1 = n1;
        node2 = n2;
    }

    public boolean equalEdge(Edge e){
        return ((node1.equalNode(e.node1) && node2.equalNode(e.node2)) || (node1.equalNode(e.node2) && node2.equalNode(e.node1)));
    }

    public void makeRoad(Color cc){
        isRoad = true;
        c = cc;
    }

    public void draw(Graphics g){
        g.setColor(c);
        if (isRoad){
            g.drawLine(node1.hex.hexagonNode(node1.p)[0],node1.hex.hexagonNode(node1.p)[1],node2.hex.hexagonNode(node2.p)[0],node2.hex.hexagonNode(node2.p)[1]);
        }
    }

}
