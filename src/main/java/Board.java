import java.awt.*;

public class Board{

    private Hexagon[] hexes = new Hexagon[19];
    private final int SELECTIONRADIUS = 20;
    private int hexSelected;
    private int hexPosition;
    private boolean placingSettlement = false;


    public Board(int[][] info){
        for (int i = 0; i < info.length; i ++) {
            hexes[i] = new Hexagon(info[i]);
        }
    }

    public boolean placeSettlement(int x, int y){
        if (!placingSettlement) {
            for (int j = 0; j < hexes.length; j ++) {
                Hexagon hex = hexes[j];
                for (int i = 0; i < 6; i++) {
                    if (Math.pow(hex.hexagonNode(i)[0] - x, 2) + Math.pow(hex.hexagonNode(i)[1] - y, 2) < Math.pow(SELECTIONRADIUS, 2)) {
                        hexSelected = j;
                        hexPosition = i;
                        placingSettlement = true;
                        return true;
                    }
                }
            }
            return false;
        } else {
            if (Math.pow(hexes[hexSelected].hexagonNode(hexPosition)[0] - x, 2) + Math.pow(hexes[hexSelected].hexagonNode(hexPosition)[1] - y, 2) < Math.pow(SELECTIONRADIUS, 2)) {
                hexes[hexSelected].setSettlement(hexPosition,"white");
            }
            placingSettlement = false;
        }
        return true;
    }

    public void draw(Graphics g){
        for (int i = 0; i < hexes.length; i ++){
            hexes[i].draw(g);
        }
        for (int i = 0; i < hexes.length; i ++){
            hexes[i].draw(g);
        }
    }
}

