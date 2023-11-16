package camtrack.meet.cmeet.meets_model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Table(name = "meeting", catalog = "cmeet", schema = "public")
public class camtrackmeets implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "meeting_id")
    private String meetingId;
    @Column(name = "location")
    private String location;
    @Column(name = "number_of_participants")
    private Integer numberOfParticipants;


    @jakarta.persistence.Column(name = "dateofcreation")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime dateofcreation;
    @jakarta.persistence.Column(name = "startdate")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime startdate;
    @jakarta.persistence.Column(name = "enddate")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime enddate;
    @Column(name = "owner")
    private String owner;

    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

    @Column(name = "attendee")
    private String[] attendee;

    @Transient
    private String userid;

    @Column(name = "meeting_update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date meeting_update_time;

    @Column(name = "update_comment")
    private String update_comment;


    public camtrackmeets() {
    }

    public camtrackmeets(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public OffsetDateTime getDateofcreation() {
        return dateofcreation;
    }

    public void setDateofcreation(OffsetDateTime dateofcreation) {
        this.dateofcreation = dateofcreation;
    }

    public OffsetDateTime getStartdate() {
        return startdate;
    }

    public void setStartdate(OffsetDateTime startdate) {
        this.startdate = startdate;
    }

    public OffsetDateTime getEnddate() {
        return enddate;
    }

    public void setEnddate(OffsetDateTime enddate) {
        this.enddate = enddate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String[] getAttendee() {
        return attendee;
    }

    public void setAttendee(String[] attendee) {
        this.attendee = attendee;
    }
    public Date getMeetingupdateTime() {
        return meeting_update_time;
    }

    public void setMeetingupdateTime(Date meetingupdateTime) {
        this.meeting_update_time = meetingupdateTime;
    }

    public String getUpdateComment() {
        return update_comment;
    }

    public void setUpdateComment(String updateComment) {
        this.update_comment = updateComment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (meetingId != null ? meetingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof camtrackmeets)) {
            return false;
        }
        camtrackmeets other = (camtrackmeets) object;
        if ((this.meetingId == null && other.meetingId != null) || (this.meetingId != null && !this.meetingId.equals(other.meetingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.frs.customer.Meeting[ meetingId=" + meetingId + " ]";
    }

}