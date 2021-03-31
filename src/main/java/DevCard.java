import java.awt.*;

public class DevCard{
    String type;
    Image pic;

    public DevCard(String t){
        type = t;
    }

    public void draw(Graphics g, int x, int y){
        g.setColor(Color.green);
        g.fillRect(x,y,100,200);
    }
}
