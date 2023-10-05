package camtrack.meet.cmeet;

import camtrack.meet.cmeet.websocket_Message_Obj.Message;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration

public class WebSocketServerConfig {

    MyWebSocketServer server;
    HashMap<String, ArrayList<ByteBuffer>> Event_Message_groups = new HashMap<>();
    HashMap<String, String> Event_Controller = new HashMap<>();

    ArrayList<String> meetid = new ArrayList<>();

    /*LinkedHashMap<String, ArrayList<ByteBuffer>> Event_Message_groups = new LinkedHashMap<>(2, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, ArrayList<ByteBuffer>> eldest) {
            return size() > 2;
        }
    };
    LinkedHashMap<String, String> Event_Controller = new LinkedHashMap<>(2, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            return size() > 2;
        }
    }; //For owner messages*/

    @PostConstruct
    public void init() {
        int port = 8085;
        server = new MyWebSocketServer(port);
        server.start();
        System.out.println("WebSocket server started on port " + port);
    }

    private class MyWebSocketServer extends WebSocketServer {

        public MyWebSocketServer(int port) {
            super(new InetSocketAddress(port));
        }

        @Override
        public void onOpen(WebSocket conn, ClientHandshake handshake) {
            System.out.println("New connection opened");
            System.out.println(handshake.getFieldValue("X-UID"));
            if (Event_Message_groups.containsKey(handshake.getFieldValue("X-UID"))) {
                if (!Event_Controller.isEmpty()) {
                    Thread thread = new Thread(() -> {
                        conn.send(Event_Controller.get(handshake.getFieldValue("X-UID")));
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(WebSocketServerConfig.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        for (ByteBuffer bf : Event_Message_groups.get(handshake.getFieldValue("X-UID"))) {
                            conn.send(bf);
                        }
                    });
                    thread.start();
                }
            }
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            // Handle WebSocket connection close event
            System.out.println("Connection closed: " + reason);
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            // Handle WebSocket error event
            System.out.println("WebSocket error: " + ex.getMessage());
        }

        @Override
        public void onStart() {

        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            System.out.println("Received message: " + message);
            JsonObject json = JsonParser.parseString(Objects.requireNonNull(message)).getAsJsonObject();
            if (Event_Controller.containsKey(json.get("MeetingId").getAsString())) {
                Event_Controller.remove(json.get("MeetingId").getAsString());
                Event_Controller.put(json.get("MeetingId").getAsString(), message);
            }
            else {Event_Controller.put(json.get("MeetingId").getAsString(), message);}
            if(!meetid.contains(json.get("MeetingId").getAsString()))
            {meetid.add(json.get("MeetingId").getAsString());}
            Event_Message_groups.put(json.get("MeetingId").getAsString(), new ArrayList<>());
            if (meetid.size()==10)
            {
                Event_Message_groups.remove(meetid.get(0));
                Event_Controller.remove(meetid.get(0));
                meetid.remove(0);
                System.out.println("size of meetid:"+meetid.size()+" size of Event_Controller:"+Event_Controller.size()+ " Size of Event_Message_groups:"+Event_Message_groups.size() );
            }
            server.broadcast(message);
        }

        @Override
        public void onMessage(WebSocket conn, ByteBuffer message) {
            super.onMessage(conn, message);
            String jsons = new String(Objects.requireNonNull(message).array(), message.position(), message.remaining());
            Gson gson = new Gson();
            Message sender_obj = gson.fromJson(jsons, Message.class);
            Event_Message_groups.get(sender_obj.getMeetingId()).add(message);
            System.out.println(jsons);
            System.out.println(Event_Message_groups);
            server.broadcast(message);
        }

    }
}
