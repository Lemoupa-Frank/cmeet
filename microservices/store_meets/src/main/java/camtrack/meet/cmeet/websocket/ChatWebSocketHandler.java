package camtrack.meet.cmeet.websocket;

import org.springframework.lang.NonNull;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatWebSocketHandler implements WebSocketHandler {

    private List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New WebSocket connection established: " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = (String) message.getPayload();
        System.out.println("Received message from " + session.getId() + ": " + payload);

        // Broadcast the message to all connected clients
        for (WebSocketSession clientSession : sessions) {
            if (clientSession.isOpen()) {
                clientSession.sendMessage(new TextMessage(payload));
            }
        }
    }

    @Override
    public void handleTransportError(@NonNull  WebSocketSession session, Throwable exception) throws Exception {

    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("WebSocket connection closed: " + session.getId());
    }


    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
