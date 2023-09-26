package camtrack.meet.cmeet.repository;

import camtrack.meet.cmeet.meets_model.UserMeetings;
import camtrack.meet.cmeet.meets_model.UserMeetingsPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class usermeets_service
{
    private final usermeets_repsitory usermeetsRepsitory;

    @Autowired
    public usermeets_service(usermeets_repsitory usermeetsRepsitory) {
        this.usermeetsRepsitory = usermeetsRepsitory;
    }

    public Iterable<UserMeetings> getAllUsers() {
        return usermeetsRepsitory.findAll();
    }
    public UserMeetings Save(UserMeetings s) {
        return usermeetsRepsitory.save(s);
    }
    public boolean check_if_it(UserMeetingsPK s) {
        return usermeetsRepsitory.existsById(s);
    }
    public Iterable<UserMeetings> FindUserByMeets(String MeetsId){return usermeetsRepsitory.findUserByMeeting(MeetsId);}

    public List<UserMeetings> finduserbyMeets(String meetingId){return usermeetsRepsitory.findRolesAndSignables(meetingId);}

    public int update(UserMeetings userAttendee){ return usermeetsRepsitory.updateSignatureAndRoleBy(userAttendee.getSignature(),userAttendee.getUserMeetingsPK());}

    public boolean ifUserMeets(UserMeetings um){return  usermeetsRepsitory.existsById(um.getUserMeetingsPK());}

}
