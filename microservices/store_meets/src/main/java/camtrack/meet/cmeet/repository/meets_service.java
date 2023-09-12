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

    public Iterable<camtrackmeets> MyMeets(String s) {
        return meetsRepository.findUserMeets(s);
    }
}
