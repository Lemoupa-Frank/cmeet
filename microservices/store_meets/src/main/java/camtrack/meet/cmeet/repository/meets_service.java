package camtrack.meet.cmeet.repository;

import camtrack.meet.cmeet.meets_model.camtrackmeets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
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

    public Iterable<camtrackmeets> MyMeets(String userId, OffsetDateTime startDate, OffsetDateTime endDate) {return meetsRepository.get_user_meet_for_period(userId,startDate,endDate);}

    public boolean check_if_meeting_exixt(String s){return meetsRepository.existsById(s);
    }

    public List<camtrackmeets> todays_meets(String userId){return meetsRepository.todays_meets_for_user(userId);}

    public List<camtrackmeets> Departmentmeets(String department, LocalDate startDate, LocalDate endDate){return meetsRepository.DepartmentMeetings(department,startDate,endDate);}

    public int DepartmentmeetsCount(String department, LocalDate startDate, LocalDate endDate){return meetsRepository.DepartmentMeetingsCount(department,startDate,endDate);}

    public Optional<camtrackmeets> getmeeting(String meetid)
    {
        return meetsRepository.findById(meetid);
    }

}
