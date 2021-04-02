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

    /*const HARBOUR_LOC = [
            [0, 0, NODE.NORTH, NODE.NORTH_WEST],
            [0, 1, NODE.NORTH, NODE.NORTH_EAST],
            [1, 0, NODE.NORTH_WEST, NODE.SOUTH_WEST],
            [1, 3, NODE.NORTH, NODE.NORTH_EAST],
            [2, 4, NODE.NORTH_EAST, NODE.SOUTH_EAST],
            [3, 1, NODE.NORTH_WEST, NODE.SOUTH_WEST],
            [3, 4, NODE.SOUTH, NODE.SOUTH_EAST],
            [4, 2, NODE.SOUTH, NODE.SOUTH_WEST],
            [4, 3, NODE.SOUTH, NODE.SOUTH_EAST]
            ];*/
    //hexagonx, hexagony, nodeposition1, nodeposition2
    private final int[][] HARBORLOC = {
            {0,0,0,5},
            {0,1,0,1},
            {1,0,5,4},
            {1,3,0,1},
            {2,4,1,2},
            {3,1,5,4},
            {3,4,3,2},
            {4,2,3,4},
            {4,3,3,2}
    };

    private int[] harborinfo = new int[9];

    public int[] harborTypes = new int[9];


    public Board(int[][] info, Font f, int[] hh){
        //set the harbor info (set harbor types)
        for (int i = 0; i < 8; i ++){
            harborTypes[i] = hh[i];
        }
        //set font
        cmcFnt = f;
        //there are 19 hexagons, so we do it for each of them
        for(int i = 0; i < 19; i++){
            //get hexagon
            hexes[i] = new Hexagon(info[i]);
            //for each node around the hexagon
            for (int j = 0; j < 6; j ++){
                //check if it's in the list
                boolean inlis = false;
                Node n = new Node(hexes[i],j);
                for (int k = 0; k < allNodes.size(); k ++){
                    if (n.equalNode(allNodes.get(k))){
                        inlis = true;
                        break;
                    }
                }
                //if it's not in the list, add it
                if (!inlis){
                    allNodes.add(allNodes.size(),n);
                }
            }
            //check this node and the next
            for (int j = 0; j < 6; j ++){
                //check if it's in the list
                boolean inlis = false;
                //get the 2 nodes
                Node n1 = new Node(hexes[i],j);
                Node n2 = new Node(hexes[i],(j+1)%6);
                Edge e = new Edge(n1,n2);
                //check if it's in the list
                for (int k = 0; k < allEdges.size(); k ++){
                    if (e.equalEdge(allEdges.get(k))){
                        inlis = true;
                        break;
                    }
                }
                //if its not in the list then add it
                if (!inlis){
                    allEdges.add(allEdges.size(),e);
                }
            }
        }
    }

    public Board(JsonElement info, Font f, int[] hh){

        for (int i = 0; i < 8; i ++){
            harborTypes[i] = hh[i];
        }
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
        //use pythagorean thereom
        return (Math.pow(x1-x2,2) + Math.pow(y1-y2,2) < rad*rad);
    }

    public boolean selectNode(int x, int y){
        //check each node
        for (int i = 0; i < allNodes.size(); i ++){
            Node n = allNodes.get(i);
            //compare dist to see if its within the selection radius
            if (compareDist(n.hex.hexagonNode(n.p)[0],n.hex.hexagonNode(n.p)[1],x,y, SELECTIONRADIUS)){
                selectedNode = n;
                index = i;
                return true;
            }
        }
        return false;
    }

    public boolean selectEdge(int x, int y){
        //check each edge
        for (int i = 0; i < allEdges.size(); i ++){
            //get the nodes
            Node n1 = allEdges.get(i).node1;
            Node n2 = allEdges.get(i).node2;
            //get the coordinates
            int x1 = n1.hex.hexagonNode(n1.p)[0];
            int y1 = n1.hex.hexagonNode(n1.p)[1];
            int x2 = n2.hex.hexagonNode(n2.p)[0];
            int y2 = n2.hex.hexagonNode(n2.p)[1];
            //selection epicenter is the midpoint of the line segment, with selection radius
            if (compareDist(x,y, (float) ((x1+x2)/2.0), (float) ((y1+y2)/2.0),SELECTIONRADIUS)){
                //get the selected Edge and index
                selectedEdge = allEdges.get(i);
                index = i;
                //check if we actually selected a new road
                return !selectedEdge.isRoad;
            }
        }
        return false;
    }

    public boolean placeSettlement(int x, int y, boolean start, Player p){
        //if we havent selected a node
        if (!placingSettlement) {
            //select a node
            placingSettlement = selectNode(x,y);
            //if we did, then set placing road to false and placing settlement to true
            if (placingSettlement){
                placingRoad = false;
            }

        } else {
            //check if we clicked near the same node
            if (compareDist(selectedNode.hex.hexagonNode(selectedNode.p)[0],selectedNode.hex.hexagonNode(selectedNode.p)[1],x,y, SELECTIONRADIUS)) {
                //if we are starting make sure we arent placing a city：if we already have a settlement here, make it a city
                if (allNodes.get(index).isSettlement && !start){
                    allNodes.get(index).placeCity();
                } else {
                    //if we havent had a settlement, add a settlement instead
                    allNodes.get(index).placeSettlement(p.color);
                }
                //check if we got a harbor
                for (int i = 0; i < 9; i ++){
                    //check if we got on a harbored node
                    if (selectedNode.equalNode(HARBORLOC[i][0],HARBORLOC[i][1],HARBORLOC[i][2]) || selectedNode.equalNode(HARBORLOC[i][0],HARBORLOC[i][1],HARBORLOC[i][3])){
                        //if the harbor type is 0, it means its a 1?3 port
                        if (harborinfo[i] == 0){
                            //set each trade val to at most 3
                            for (int j = 1; j < 6; j ++){
                                p.tradevals[j] = Math.min(3,p.tradevals[i]);
                            }
                        } else {
                            //else, the trade val is 2:1
                            p.tradevals[harborinfo[i]] = 2;
                        }
                    }
                }
            }
            //end placing settlement
            placingSettlement = false;
            return true;
            //minus resources
        }
        //return false if not enough resources
        return false;
    }

    public boolean placeRoad(int x, int y, boolean start){
        //if we arent already placing a road
        if (!placingRoad){
            //select an edge
            placingRoad = selectEdge(x,y);
            //if we did, make settlement false
            if (placingRoad){
                placingSettlement = false;
            }
        } else {
            //if we are selecting a road, get the nodes
            Node n1 = selectedEdge.node1;
            Node n2 = selectedEdge.node2;
            //get the coordinates
            int x1 = n1.hex.hexagonNode(n1.p)[0];
            int y1 = n1.hex.hexagonNode(n1.p)[1];
            int x2 = n2.hex.hexagonNode(n2.p)[0];
            int y2 = n2.hex.hexagonNode(n2.p)[1];
            //if we actually selected the right edge midpoint
            if (compareDist((float) ((x1+x2)/2.0) , (float) ((y1+y2)/2.0),x,y,SELECTIONRADIUS)){
                allEdges.get(index).makeRoad(Color.white);
            }
            //placing road goes false
            placingRoad = false;
            //minus resources
            return true;
        }
        //return false if not enough resources
        return false;
    }

    public void setSettlement(Node n, Color c){
        //set the settlement: find it, and place settlement
        for (int i = 0; i < allNodes.size(); i ++){
            if (allNodes.get(i).equalNode(n)){
                allNodes.get(i).placeSettlement(c);
            }
        }
    }

    public void setCity(Node n, Color c){
        //set the city: find it, and place the city
        for (int i = 0; i < allNodes.size(); i ++){
            if (allNodes.get(i).equalNode(n)){
                allNodes.get(i).placeCity();
            }
        }
    }

    public void setRoad(Edge e, Color c){
        //set the road: find it and place the road
        for (int i = 0; i < allEdges.size(); i ++){
            if (allEdges.get(i).equalEdge(e)){
                allEdges.get(i).makeRoad(c);
            }
        }
    }

    public void draw(Graphics g){
        //draw each hex
        for (int i = 0; i < hexes.length; i ++){
            hexes[i].drawHex(g,cmcFnt);
        }
        //draw each edge
        for (int i = 0; i < allEdges.size(); i ++){
            allEdges.get(i).draw(g);
        }
        //draw each node
        for (int i = 0; i < allNodes.size(); i ++){
            allNodes.get(i).draw(g);
        }

    }
}

