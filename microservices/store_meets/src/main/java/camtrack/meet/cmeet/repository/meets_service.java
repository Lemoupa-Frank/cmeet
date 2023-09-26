package camtrack.meet.cmeet.repository;

import camtrack.meet.cmeet.meets_model.UserMeetings;
import camtrack.meet.cmeet.meets_model.UserMeetingsPK;
import camtrack.meet.cmeet.meets_model.camtrackmeets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class meets_service {
    private final meets_repository meetsRepository;

    @Autowired
    public meets_service(meets_repository meetsRepository) {
        this.meetsRepository = meetsRepository;
    }

    public Iterable<camtrackmeets> getAllUsers() {
        return meetsRepository.findAll();
    }
    public camtrackmeets Save(camtrackmeets s) {
        return meetsRepository.save(s);
    }

    public void update_user_meeting(camtrackmeets s){ meetsRepository.upadteMeetin(s.getNumberOfParticipants(),s.getLocation(),s.getStartdate(),s.getDateofcreation(),s.getEnddate(),s.getOwner(),s.getDescription(),s.getTitle(),s.getAttendee(),s.getMeetingId());}

    public Iterable<camtrackmeets> MyMeets(String s) {return meetsRepository.findUserMeets(s);}

    public boolean check_if_meeting_exixt(String s){return meetsRepository.existsById(s);
    }


}
