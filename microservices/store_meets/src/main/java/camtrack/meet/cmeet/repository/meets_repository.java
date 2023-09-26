package camtrack.meet.cmeet.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import camtrack.meet.cmeet.meets_model.camtrackmeets;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface meets_repository extends CrudRepository<camtrackmeets, String>
{
    @Transactional
    @Modifying
    @Query("""
            update camtrackmeets c set c.numberOfParticipants = :numberOfParticipants, c.location = :location, c.startdate = :startdate, c.dateofcreation = :dateofcreation, c.enddate = :enddate, c.owner = :owner, c.description = :description, c.title = :title, c.attendee = :attendee
            where c.meetingId = :meetingId""")
    int upadteMeetin(Integer numberOfParticipants, String location, String startdate, String dateofcreation, String enddate, String owner, String description, String title, String[] attendee, String meetingId);
    @Query("SELECT m from camtrackmeets m INNER JOIN UserMeetings um ON m.meetingId=um.userMeetingsPK.meetingId WHERE um.userMeetingsPK.userId = :userId")
    List<camtrackmeets> findUserMeets(String userId);
}