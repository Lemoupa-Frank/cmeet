package camtrack.meet.cmeet.websocket_Message_Obj;

import java.util.ArrayList;

public class byteMessager
{
    ArrayList<Byte> messages;


    public byteMessager()
    {
        this.messages = new ArrayList<>();
    }

    public ArrayList<Byte> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Byte> messages) {
        this.messages = messages;
    }
}
