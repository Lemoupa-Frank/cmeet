package camtrack.meet.cmeet.websocket_Message_Obj;

import com.google.gson.Gson;

public class Message {
    private String sender;
    private String MeetingId;
    private String Name;
    private Object Signature;
    private Boolean Signable;

    public Object getSignature() {
        return Signature;
    }

    public void setSignature(Object signature) {
        Signature = signature;
    }

    public Boolean getSignable() {
        return Signable;
    }

    public void setSignable(Boolean signable) {
        Signable = signable;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    // Constructors, getters, and setters

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMeetingId() {
        return MeetingId;
    }

    public void setMeetingId(String meetingId) {
        this.MeetingId = meetingId;
    }

    public Message (String Sender, String MeetingId)
    {

    }
    public Message ()
    {

    }


    public String toJson() {
        return new Gson().toJson(this);
    }


    public static Message fromJson(String json) {
        return new Gson().fromJson(json, Message.class);
    }
}
