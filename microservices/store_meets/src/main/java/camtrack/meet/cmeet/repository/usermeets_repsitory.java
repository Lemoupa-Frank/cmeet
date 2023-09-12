package camtrack.meet.cmeet.repository;

import camtrack.meet.cmeet.meets_model.UserMeetings;
import camtrack.meet.cmeet.meets_model.UserMeetingsPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface usermeets_repsitory extends CrudRepository<UserMeetings, UserMeetingsPK>
{
    @Query("SELECT um from UserMeetings um WHERE um.userMeetingsPK.meetingId = :meetingId")
    List<UserMeetings> findUserByMeeting(String meetingId);
}
