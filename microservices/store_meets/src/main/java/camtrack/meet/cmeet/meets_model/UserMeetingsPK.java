package camtrack.meet.cmeet.meets_model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserMeetingsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "meeting_id")
    private String meetingId;
    @Basic(optional = false)
    @Column(name = "user_id")
    private String userId;

    public UserMeetingsPK() {
    }

    public UserMeetingsPK(String meetingId, String userId) {
        this.meetingId = meetingId;
        this.userId = userId;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (meetingId != null ? meetingId.hashCode() : 0);
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserMeetingsPK)) {
            return false;
        }
        UserMeetingsPK other = (UserMeetingsPK) object;
        if ((this.meetingId == null && other.meetingId != null) || (this.meetingId != null && !this.meetingId.equals(other.meetingId))) {
            return false;
        }
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.frs.customer.UserMeetingsPK[ meetingId=" + meetingId + ", userId=" + userId + " ]";
    }

}
