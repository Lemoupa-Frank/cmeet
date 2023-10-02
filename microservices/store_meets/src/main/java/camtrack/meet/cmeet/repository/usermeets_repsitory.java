package camtrack.meet.cmeet.repository;

import camtrack.meet.cmeet.meets_model.UserMeetings;
import camtrack.meet.cmeet.meets_model.UserMeetingsPK;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface usermeets_repsitory extends CrudRepository<UserMeetings, UserMeetingsPK>
{
    @Query("SELECT new UserMeetings(um.role, um.userMeetingsPK, um.signable) FROM UserMeetings um WHERE um.userMeetingsPK.meetingId = :meetingId")
    List<UserMeetings> findRolesAndSignables(String meetingId);
    @Transactional
    @Modifying
    @Query("update UserMeetings u set u.signature = :signature WHERE u.userMeetingsPK = :userMeetingsPK")
    int updateSignatureAndRoleBy(String signature, UserMeetingsPK userMeetingsPK);
    @Query("SELECT um from UserMeetings um WHERE um.userMeetingsPK.meetingId = :meetingId")
    List<UserMeetings> findUserByMeeting(String meetingId);
}
