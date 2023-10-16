package camtrack.meet.repository;

import camtrack.meet.user.Model_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class user_service {



        private final User_repository user_repository;

        @Autowired
        public user_service(User_repository user_repository) {
            this.user_repository = user_repository;
        }

        public Iterable<Model_User> getAllUsers() {
            return user_repository.findAll();
        }
        public camtrack.meet.user.Model_User getUser(String userid)
        {
            return user_repository.findById(userid).isPresent()?user_repository.findById(userid).get():null;
        }
        public Model_User Save(Model_User s){
        return user_repository.save(s);
    }

    public List<Model_User> getAbsentee(String  MeetingId)
    {
       return user_repository.findUsersWithNullSignatureByMeetingId(MeetingId);
    }
}
