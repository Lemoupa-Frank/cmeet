package camtrack.meet.cmeet.meets_model;

import com.google.api.services.calendar.model.Event;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
@Entity
@Table(name = "user_meetings", catalog = "cmeet", schema = "public")
@NamedQueries({
        @NamedQuery(name = "UserMeetings.findAll", query = "SELECT u FROM UserMeetings u"),
        @NamedQuery(name = "UserMeetings.findByMeetingId", query = "SELECT u FROM UserMeetings u WHERE u.userMeetingsPK.meetingId = :meetingId"),
        @NamedQuery(name = "UserMeetings.findByUserId", query = "SELECT u FROM UserMeetings u WHERE u.userMeetingsPK.userId = :userId"),
        @NamedQuery(name = "UserMeetings.findBySignature", query = "SELECT u FROM UserMeetings u WHERE u.signature = :signature"),
        @NamedQuery(name = "UserMeetings.findByRole", query = "SELECT u FROM UserMeetings u WHERE u.role = :role"),
        @NamedQuery(name = "UserMeetings.findBySignable", query = "SELECT u FROM UserMeetings u WHERE u.signable = :signable")})
public class UserMeetings implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserMeetingsPK userMeetingsPK;
    @Column(name = "signature")
    private String signature;
    @Column(name = "role")
    private String role;
    @Column(name = "signable")
    private Boolean signable;

    public UserMeetings() {
    }

    public UserMeetings(UserMeetingsPK userMeetingsPK) {
        this.userMeetingsPK = userMeetingsPK;
    }

    public UserMeetings(String meetingId, String userId) {
        this.userMeetingsPK = new UserMeetingsPK(meetingId, userId);
    }

    public UserMeetingsPK getUserMeetingsPK() {
        return userMeetingsPK;
    }

    public void setUserMeetingsPK(UserMeetingsPK userMeetingsPK) {
        this.userMeetingsPK = userMeetingsPK;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getSignable() {
        return signable;
    }

    public void setSignable(Boolean signable)
    {
        this.signable = signable;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userMeetingsPK != null ? userMeetingsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserMeetings)) {
            return false;
        }
        UserMeetings other = (UserMeetings) object;
        if ((this.userMeetingsPK == null && other.userMeetingsPK != null) || (this.userMeetingsPK != null && !this.userMeetingsPK.equals(other.userMeetingsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.frs.customer.UserMeetings[ userMeetingsPK=" + userMeetingsPK + " ]";
    }

}
