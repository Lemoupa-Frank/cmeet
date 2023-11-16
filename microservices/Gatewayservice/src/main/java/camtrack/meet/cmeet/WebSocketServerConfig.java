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

    // map to store the byte messages for each meeting
    HashMap<String, ArrayList<ByteBuffer>> ByteMessages_per_MeetingID = new HashMap<>();

    //map to store the string messages of each meeting stored in gateway
    HashMap<String, String> StringMessage_per_MeetingID = new HashMap<>(); //store client string messages


    //array that keeps track of number meetings and their MID stored in the gateway as the came in
    ArrayList<String> List_of_MeetingID = new ArrayList<>();

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


        // Send attendance on open if the signature had already begun
        @Override
        public void onOpen(WebSocket conn, ClientHandshake handshake) {
            System.out.println("New connection opened");
            System.out.println(handshake.getFieldValue("X-UID"));
            if (ByteMessages_per_MeetingID.containsKey(handshake.getFieldValue("X-UID"))) {
                if (!StringMessage_per_MeetingID.isEmpty()) {
                    Thread thread = new Thread(() -> {
                        conn.send(StringMessage_per_MeetingID.get(handshake.getFieldValue("X-UID")));
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(WebSocketServerConfig.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        for (ByteBuffer bf : ByteMessages_per_MeetingID.get(handshake.getFieldValue("X-UID"))) {
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

            if (StringMessage_per_MeetingID.containsKey(json.get("MeetingId").getAsString())) {
                // this condition closes and ongoing meeting in the StringMessage_per_MeetingID group
                // since only admin sends string messages
                StringMessage_per_MeetingID.remove(json.get("MeetingId").getAsString());
                StringMessage_per_MeetingID.put(json.get("MeetingId").getAsString(), message);
            }
            else {
                //This condition adds a meeting in StringMessage_per_MeetingID
                //which be start signing signal for participants
                StringMessage_per_MeetingID.put(json.get("MeetingId").getAsString(), message);
            }
            if(!List_of_MeetingID.contains(json.get("MeetingId").getAsString()))
                 {
                     // if the meeting was not in the list of
                     // meeetings add it and instatiate ByteMessages_per_MeetingID
                     // where messages will be stored
                     List_of_MeetingID.add(json.get("MeetingId").getAsString());
                     ByteMessages_per_MeetingID.put(json.get("MeetingId").getAsString(), new ArrayList<>());
                 }
            if (List_of_MeetingID.size()==10)
            {
                ByteMessages_per_MeetingID.remove(List_of_MeetingID.get(0));
                StringMessage_per_MeetingID.remove(List_of_MeetingID.get(0));
                List_of_MeetingID.remove(0);
                System.out.println("size of meetid:"+ List_of_MeetingID.size()+" size of Event_Controller:"+ StringMessage_per_MeetingID.size()+ " Size of Event_Message_groups:"+ ByteMessages_per_MeetingID.size() );
            }
            server.broadcast(message);
        }

        @Override
        public void onMessage(WebSocket conn, ByteBuffer message) {
            super.onMessage(conn, message);
            String jsons = new String(Objects.requireNonNull(message).array(), message.position(), message.remaining());
            Gson gson = new Gson();
            Message sender_obj = gson.fromJson(jsons, Message.class);
            ByteMessages_per_MeetingID.get(sender_obj.getMeetingId()).add(message);
            System.out.println(jsons);
            System.out.println(ByteMessages_per_MeetingID);
            server.broadcast(message);
        }

    }
}
