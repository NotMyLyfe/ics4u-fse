import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.websocket.ClientEndpoint;
import com.neovisionaries.ws.client.*;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

@ClientEndpoint
public class WebSocketClient {
    private static final JsonParser PARSER = new JsonParser();
    private WebSocket webSocket;
    private String ip;
    private boolean connected = false, attempted = false;
    private String guid;
    private List<JsonObject> messages;

    public WebSocketClient(String ip){
        WebSocketClient.this.ip = ip;
    }

    public List<JsonObject> getMessages(){
        List<JsonObject> temp = new ArrayList<>(messages);
        messages.clear();
        return temp;
    }

    public boolean isConnected(){
        return WebSocketClient.this.connected;
    }

    public boolean hasAttempted() {
        return WebSocketClient.this.attempted;
    }

    public void sendMessage(JsonObject message){
        message.addProperty("guid", guid);
        if(WebSocketClient.this.connected)
            WebSocketClient.this.webSocket.sendText(message.toString());
    }

    public void connect() {
        try {
            WebSocketClient.this.webSocket = new WebSocketFactory()
                    .setConnectionTimeout(4000)
                    .createSocket(WebSocketClient.this.ip)
                    .addListener(new WebSocketAdapter() {
                        @Override
                        public void onConnected(WebSocket websocket, Map<String, List<String>> headers) {
                            System.out.println("Opened websocket connection successfully!");
                            WebSocketClient.this.connected = true;
                        }

                        @Override
                        public void onConnectError(WebSocket websocket, WebSocketException exception) {
                            WebSocketClient.this.attempted = true;
                            System.out.println("Failed to connect");
                            exception.printStackTrace();
                        }

                        @Override
                        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {
                            WebSocketClient.this.connected = false;
                            System.out.println("Disconnected");
                        }

                        @Override
                        public void onTextMessage(WebSocket websocket, String text) {
                            JsonObject jsonObject;
                            try {
                                jsonObject = PARSER.parse(text).getAsJsonObject();
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                                return;
                            }
                            if (jsonObject.has("guid"))
                                WebSocketClient.this.guid = jsonObject.get("guid").getAsString();
                            WebSocketClient.this.messages.add(jsonObject.deepCopy());
                        }

                        @Override
                        public void onError(WebSocket websocket, WebSocketException exception) {
                            exception.printStackTrace();
                        }
                    })
                    .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                    .connectAsynchronously();
            WebSocketClient.this.webSocket.setPingInterval(4000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
