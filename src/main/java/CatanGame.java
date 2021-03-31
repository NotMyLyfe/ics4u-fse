//Evan Lu
// Tetris.java
//simple tetris game
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CatanGame extends JFrame{
    Catan game;

    public CatanGame() {
        super("Basic Graphics");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new Catan();
        add(game);
        pack();
        setVisible(true);
    }

    public static void main(String[] arguments) {
        CatanGame frame = new CatanGame();
    }
}

class Catan extends JPanel implements MouseListener, ActionListener, KeyListener {

    boolean[] keys;
    private Board CatanBoard;
    String screen = "game";

    Timer myTimer;
    Image back;
    Font comicFnt;
    private boolean makeTrade = false;
    Trade catanTrade = new Trade();
    int x = 0,y = 0;

    public static final int WIDTH = 1000, HEIGHT = 800;

    private int[][] testboard = {{0,0,4,10},{1,0,3,2},{2,0,2,9},{0,1,1,1},{1,1,5,6},{2,1,3,4},{3,1,5,10},{0,2,1,9},{1,2,2,11},{2,2,0,0},{3,2,2,3},{4,2,4,8},{1,3,2,8},{2,3,4,3},{3,3,1,4},{4,3,3,5},{2,4,5,5},{3,4,1,6},{4,4,3,11}};


    public Catan() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(this);
        addKeyListener(this);

        comicFnt = new Font("Comic Sans MS", Font.BOLD, 40);

        keys = new boolean[KeyEvent.KEY_LAST + 1];



        myTimer = new Timer(100, this);
        setFocusable(true);
        requestFocus();
        //get it from server.
        CatanBoard = new Board(testboard,comicFnt);

    }





    @Override
    public void actionPerformed(ActionEvent e){
        if (screen.equals("game")){
            getInput();
        }
        repaint();
    }

    public void getInput(){
    }

    @Override
    public void paint(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0,WIDTH,HEIGHT);
        if (screen.equals("game")){
            CatanBoard.draw(g);
            g.setColor(Color.black);
            if (!makeTrade){
                g.drawRect(700,700,100,100);
            } else {
                if (makeTrade) {

                    catanTrade.displayTrade(g);
                }
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        CatanBoard.placeSettlement(x,y);
        CatanBoard.placeRoad(x,y);
        catanTrade.createTrade(x, y);

        if (700 < x && x < 800 && 700 < y && y < 800 && !makeTrade) {
            makeTrade = true;
        }


    }

    public void mouseClicked(MouseEvent e) {
        myTimer.start();

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;

    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e) {
    }
}
