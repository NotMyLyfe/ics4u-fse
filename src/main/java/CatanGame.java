//Evan Lu
// Tetris.java
//simple tetris game
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class CatanGame extends JFrame{
    Catan game;
    public static WebSocketClient webSocketClient;
    static Scanner scanner = new Scanner(System.in);

    public CatanGame(JsonObject jsonObject) {
        super("Basic Graphics");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new Catan(jsonObject);
        add(game);
        pack();
        setVisible(true);
    }

    public static void main(String[] arguments) {
        webSocketClient = new WebSocketClient("ws://localhost:8080/websocket/path");
        while(!webSocketClient.hasAttempted()) System.out.print("");
        while(!webSocketClient.isConnected()){
            System.out.println("Failed to connect, would you like to try again? (y for yes, n for no)");
            String userInput = scanner.next();
            if(userInput.equals("y")){
                webSocketClient.connect();
                while(!webSocketClient.hasAttempted()) System.out.print("");
            }
            else if(userInput.equals("n")){
                return;
            }
            else{
                System.out.println("Invalid input");
            }
        }

        AtomicBoolean gameNotStarted = new AtomicBoolean(false);
        AtomicBoolean waitResponse = new AtomicBoolean(false);
        AtomicReference<JsonObject> gameData = new AtomicReference<>();

        new Thread(() -> {
            while(webSocketClient.isConnected()){
                if(!webSocketClient.isMessagesEmpty()) {
                    for (JsonObject i : webSocketClient.getMessages()) {
                        if (i.has("game")) {
                            if (i.get("game").getAsString().equals("started")) {
                                gameNotStarted.set(true);
                                gameData.set(i);
                                waitResponse.set(false);
                                return;
                            }
                        } else {
                            System.out.println(i);
                        }
                    }
                    if (waitResponse.get())
                        waitResponse.set(false);
                }
            }
        }).start();

        String command;
        boolean gameCreator = false;
        while(true){
            command = scanner.next();
            JsonObject jsonObject = new JsonObject();
            if(command.equals("create")){
                gameCreator = true;
                jsonObject.addProperty("action", "create");
            }
            else if(command.equals("join")){
                jsonObject.addProperty("action", "join");
                jsonObject.addProperty("gamekey", scanner.next());
            }
            else{
                System.out.println("Invalid command");
                continue;
            }
            webSocketClient.sendMessage(jsonObject);
            break;
        }

        while(!gameNotStarted.get()){
            if(gameCreator){
                String startGame = scanner.next();
                JsonObject jsonObject = new JsonObject();
                if(startGame.equals("start")){
                    jsonObject.addProperty("action", "start");
                }
                else {
                    System.out.println("Invalid command");
                    continue;
                }
                waitResponse.set(true);
                webSocketClient.sendMessage(jsonObject);
                while(waitResponse.get()) System.out.print("");
            }
        }

        CatanGame frame = new CatanGame(gameData.get());
    }
}

class Catan extends JPanel implements MouseListener, ActionListener, KeyListener {

    boolean[] keys;
    private Board CatanBoard;
    String screen = "game";
    public boolean initalPieces = true;
    public String placePiece = "settlement";
    public boolean isTurn = true;

    Timer myTimer;
    Image back;
    Font comicFnt;
    private boolean makeTrade = false;
    private boolean useDevCard = false;
    Trade catanTrade = new Trade();
    int x = 0,y = 0;
    Image backbutton = new ImageIcon("src/main/java/backbutton.png").getImage();
    Image sendTrade = new ImageIcon("src/main/java/sendtrade.png").getImage();

    public static final int WIDTH = 1000, HEIGHT = 800;
    Player catanPlayer;

    private int[][] testboard = {{0,0,4,10},{1,0,3,2},{2,0,2,9},{0,1,1,1},{1,1,5,6},{2,1,3,4},{3,1,5,10},{0,2,1,9},{1,2,2,11},{2,2,0,0},{3,2,2,3},{4,2,4,8},{1,3,2,8},{2,3,4,3},{3,3,1,4},{4,3,3,5},{2,4,5,5},{3,4,1,6},{4,4,3,11}};

    private boolean rollDice = false;
    public Catan(JsonObject gameData) {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(this);
        addKeyListener(this);

        comicFnt = new Font("Comic Sans MS", Font.BOLD, 40);

        keys = new boolean[KeyEvent.KEY_LAST + 1];

        System.out.println(gameData);

        myTimer = new Timer(100, this);
        setFocusable(true);
        requestFocus();
        //get it from server.
        CatanBoard = new Board(gameData.get("hexagons"),comicFnt);
        catanPlayer = new Player(true, Color.white);
    }





    @Override
    public void actionPerformed(ActionEvent e){
        if (screen.equals("game")){
            getInput();
        }
        /*
        if (new turn){
        rollDice = false;
        }
        */

        repaint();
    }

    public void getInput(){
    }

    @Override
    public void paint(Graphics g){

        if (screen.equals("game")){
            back = new ImageIcon("src/main/java/background.png").getImage();
            g.drawImage(back,0,0,null);
            catanPlayer.displayResources(g);
            CatanBoard.draw(g);
            g.setColor(Color.black);
            if (makeTrade){
                //draw back
                g.drawRect(900,500,100,100);
                g.drawImage(backbutton,900,500,null);

                //send trade

                g.drawRect(800,500,100,100);
                g.drawImage(sendTrade,800,500,null);


                catanTrade.displayTrade(g);
                //display + and -

            } else if (useDevCard){
                //draw back
                g.drawRect(900,500,100,100);
                catanPlayer.drawDevCards(g);
            } else {
                //end turn
                g.drawRect(500,700,100,100);
                //use dev Card button
                g.drawRect(600,700,100,100);
                //buy dev Card button
                g.drawRect(700,700,100,100);
                //trade button
                g.drawRect(800,700,100,100);
                //roll dice button
                g.drawRect(900,700,100,100);
            }
        }
    }

    public void mousePressed(MouseEvent e) {

        x = e.getX();
        y = e.getY();

        if (!isTurn){
            if (makeTrade){
                if (900 < x && x < 1000 && 500 < y && y < 600){
                    //send to server.
                    //if it works, update resources
                }
            }
            return;
        }

        if (initalPieces && placePiece.equals("settlement")){
            boolean bool = CatanBoard.placeSettlement(x,y,true);
            if (bool){
                placePiece = "road";
            }
            return;
        } else if (initalPieces && placePiece.equals("road")){
            if (CatanBoard.placeRoad(x,y,true)){
                placePiece = "";
                initalPieces = false;
            }
            return ;
        }



        CatanBoard.placeSettlement(x,y,false);
        CatanBoard.placeRoad(x,y,false);

        if (makeTrade){
            catanTrade.createTrade(x, y);
        }

        if (useDevCard){
            catanPlayer.playDevCard(x,y);
        }


        if (800 < x && x < 900 && 700 < y && y < 800 && !makeTrade && rollDice && !useDevCard) {
            makeTrade = true;
        }

        if (900 < x && x < 1000 && 700 < y && y < 800 && !rollDice && !useDevCard){
            rollDice = true;
            //send server that rolled dice
        }

        if (700 < x && x < 800 && 700 < y && y < 800 && !rollDice && !useDevCard){
            //send server to buy dev card/ minus resources
            //also check that they have the resources
        }

        if (600 < x && x < 700 && 700 < y && y < 800 && !makeTrade && !useDevCard){
            useDevCard = true;
            //play dev card
        }

        if (500 < x && x < 600 && 700 < y && y < 800 && rollDice && !useDevCard){
            // end turn
        }

        if (makeTrade){
            if (900 < x && x < 1000 && 500 < y && y < 600){
                makeTrade = false;
                catanTrade = new Trade();
            }

            if (800 < x && x < 900 && 500 < y && y < 600){
                makeTrade = false;
                //send it to the server
                catanTrade = new Trade();
            }
        }

        if (useDevCard){
            if (900 < x && x < 1000 && 500 < y &&  y < 600){
                useDevCard = false;
            }
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
