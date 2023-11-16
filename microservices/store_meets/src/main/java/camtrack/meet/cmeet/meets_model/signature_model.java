package camtrack.meet.cmeet.meets_model;


import java.io.Serializable;

public class signature_model implements Serializable {
    String username;
    String userid;
    byte[] Signature;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public byte[] getSignature() {
        return Signature;
    }

    public void setSignature(byte[] signature) {
        Signature = signature;
    }
}
