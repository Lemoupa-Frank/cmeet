package camtrack.meet.cmeet;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


@Configuration

public class WebSocketServerConfig {

    static MyWebSocketServer server;
    List<String[]> messagee_array = new ArrayList<>();
    List<byte[]> byte_message_array = new ArrayList<>();
    @PostConstruct
    public void init() {
        int port = 8085;
        server = new MyWebSocketServer(port);
        server.start();
        System.out.println("WebSocket server started on port " + port);
    }

    private static class MyWebSocketServer extends WebSocketServer {

        public MyWebSocketServer(int port) {
            super(new InetSocketAddress(port));
        }

        @Override
        public void onOpen(WebSocket conn, ClientHandshake handshake) {
            System.out.println("New connection opened");
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
        public void onMessage(WebSocket conn, String message)
        {

            System.out.println("Received message: " + message);
            server.broadcast(message);
        }

        @Override
        public void onMessage(WebSocket conn, ByteBuffer message) {
            super.onMessage(conn, message);

            System.out.println("Received Byte Message: " + message);
            server.broadcast(message);
        }
    }
}
