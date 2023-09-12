package camtrack.meet.cmeet.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import camtrack.meet.cmeet.meets_model.camtrackmeets;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface meets_repository extends CrudRepository<camtrackmeets, String>
{
    @Query("SELECT m from camtrackmeets m INNER JOIN UserMeetings um ON m.meetingId=um.userMeetingsPK.meetingId WHERE um.userMeetingsPK.userId = :userId")
    List<camtrackmeets> findUserMeets(String userId);
}