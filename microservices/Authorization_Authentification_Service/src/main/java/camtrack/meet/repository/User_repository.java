package camtrack.meet.repository;

import camtrack.meet.user.Model_User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//here is the bean

@Repository
public interface User_repository extends CrudRepository<Model_User, String>
{
}

