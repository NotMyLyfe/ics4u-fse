import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.ServiceConfigurationError;

public class Board{

    private Hexagon[] hexes = new Hexagon[19];
    private final int SELECTIONRADIUS = 15;
    private Node selectedNode;
    private Edge selectedEdge;
    private int index;
    private boolean placingSettlement = false;
    private boolean placingRoad = false;
    Font cmcFnt;

    private ArrayList<Node> allNodes = new ArrayList<>();
    private ArrayList<Edge> allEdges = new ArrayList<>();


    public Board(JsonElement info, Font f){
        cmcFnt = f;

        for(int y = 0, i = 0; y < 5; y++){
            for(int x = 0; x < 5; x++){
                int[] jsonData = new Gson().fromJson(info.getAsJsonArray().get(y).getAsJsonArray().get(x), int[].class);
                if(jsonData[0] == -1) continue;
                int[] hexData = {x, y, jsonData[0], jsonData[1]};
                hexes[i] = new Hexagon(hexData);

                for (int j = 0; j < 6; j ++){
                    boolean inlis = false;
                    Node n = new Node(hexes[i],j);
                    for (int k = 0; k < allNodes.size(); k ++){
                        if (n.equalNode(allNodes.get(k))){
                            inlis = true;
                            break;
                        }
                    }
                    if (!inlis){
                        allNodes.add(allNodes.size(),n);
                    }
                }

                for (int j = 0; j < 6; j ++){
                    boolean inlis = false;
                    Node n1 = new Node(hexes[i],j);
                    Node n2 = new Node(hexes[i],(j+1)%6);
                    Edge e = new Edge(n1,n2);
                    for (int k = 0; k < allEdges.size(); k ++){
                        if (e.equalEdge(allEdges.get(k))){
                            inlis = true;
                            break;
                        }
                    }
                    if (!inlis){
                        allEdges.add(allEdges.size(),e);
                    }
                }
                i++;
            }
        }
    }

    private boolean compareDist(float x1, float y1, float x2, float y2, int rad){
        return (Math.pow(x1-x2,2) + Math.pow(y1-y2,2) < rad*rad);
    }

    public boolean selectNode(int x, int y){
        for (int i = 0; i < allNodes.size(); i ++){
            Node n = allNodes.get(i);
            if (compareDist(n.hex.hexagonNode(n.p)[0],n.hex.hexagonNode(n.p)[1],x,y, SELECTIONRADIUS)){
                selectedNode = n;
                index = i;
                return true;
            }
        }
        return false;
    }

    public boolean selectEdge(int x, int y){
        for (int i = 0; i < allEdges.size(); i ++){
            Node n1 = allEdges.get(i).node1;
            Node n2 = allEdges.get(i).node2;
            int x1 = n1.hex.hexagonNode(n1.p)[0];
            int y1 = n1.hex.hexagonNode(n1.p)[1];
            int x2 = n2.hex.hexagonNode(n2.p)[0];
            int y2 = n2.hex.hexagonNode(n2.p)[1];
            if (compareDist(x,y, (float) ((x1+x2)/2.0), (float) ((y1+y2)/2.0),SELECTIONRADIUS)){
                selectedEdge = allEdges.get(i);
                index = i;
                return !selectedEdge.isRoad;
            }
        }
        return false;
    }

    public boolean placeSettlement(int x, int y, boolean start){
        if (!placingSettlement) {
            placingSettlement = selectNode(x,y);
            if (placingSettlement){
                placingRoad = false;
            }

        } else {
            if (compareDist(selectedNode.hex.hexagonNode(selectedNode.p)[0],selectedNode.hex.hexagonNode(selectedNode.p)[1],x,y, SELECTIONRADIUS)) {
                if (allNodes.get(index).isSettlement && !start){
                    allNodes.get(index).placeCity();
                } else {
                    allNodes.get(index).placeSettlement(Color.white);
                }
            }
            placingSettlement = false;
            return true;
            //minus resources
        }
        //return false if not enough resources
        return false;
    }

    public boolean placeRoad(int x, int y, boolean start){
        if (!placingRoad){
            placingRoad = selectEdge(x,y);
            if (placingRoad){
                placingSettlement = false;
            }
        } else {
            Node n1 = selectedEdge.node1;
            Node n2 = selectedEdge.node2;
            int x1 = n1.hex.hexagonNode(n1.p)[0];
            int y1 = n1.hex.hexagonNode(n1.p)[1];
            int x2 = n2.hex.hexagonNode(n2.p)[0];
            int y2 = n2.hex.hexagonNode(n2.p)[1];
            if (compareDist((float) ((x1+x2)/2.0) , (float) ((y1+y2)/2.0),x,y,SELECTIONRADIUS)){
                if (start){
                    //see if we can actually add it
                    allEdges.get(index).makeRoad(Color.white);
                } else {
                    allEdges.get(index).makeRoad(Color.white);
                }
                System.out.println(index);
            }
            placingRoad = false;
            //minus resources
        }
        //return false if not enough resources
        return true;
    }

    public void setSettlement(Node n, Color c){
        for (int i = 0; i < allNodes.size(); i ++){
            if (allNodes.get(i).equalNode(n)){
                allNodes.get(i).placeSettlement(c);
            }
        }
    }

    public void setCity(Node n, Color c){
        for (int i = 0; i < allNodes.size(); i ++){
            if (allNodes.get(i).equalNode(n)){
                allNodes.get(i).placeCity();
            }
        }
    }

    public void setRoad(Edge e, Color c){
        for (int i = 0; i < allEdges.size(); i ++){
            if (allEdges.get(i).equalEdge(e)){
                allEdges.get(i).makeRoad(c);
            }
        }
    }

    public void draw(Graphics g){
        for (int i = 0; i < hexes.length; i ++){
            hexes[i].drawHex(g,cmcFnt);
        }
        for (int i = 0; i < allEdges.size(); i ++){
            allEdges.get(i).draw(g);
        }
        for (int i = 0; i < allNodes.size(); i ++){
            allNodes.get(i).draw(g);
        }

    }
}

