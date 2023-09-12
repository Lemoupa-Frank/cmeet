package camtrack.meet.cmeet.meets_model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import com.google.api.services.calendar.model.Event;

import java.io.Serial;
import java.io.Serializable;
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
    @Column(name = "dateofcreation")
    private String dateofcreation;
    @Column(name = "startdate")
    private String startdate;
    @Column(name = "enddate")
    private String enddate;
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

    public String getDateofcreation() {
        return dateofcreation;
    }

    public void setDateofcreation(String dateofcreation) {
        this.dateofcreation = dateofcreation;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
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