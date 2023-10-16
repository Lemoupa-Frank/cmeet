package camtrack.meet.repository;

import camtrack.meet.user.Model_User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//here is the bean

@Repository
public interface User_repository extends CrudRepository<Model_User, String>
{
    /**
     * List of users that did not attend the meeting
     * @param meetingId the ID of the meeting
     * @return
     */
    @Query(value = "SELECT u.* FROM \"user\" u " +
            "JOIN user_meetings um ON u.user_id = um.user_id " +
            "WHERE um.meeting_id = :meetingId " +
            "  AND um.signature IS NULL", nativeQuery = true)
    List<Model_User> findUsersWithNullSignatureByMeetingId(String meetingId);
}

