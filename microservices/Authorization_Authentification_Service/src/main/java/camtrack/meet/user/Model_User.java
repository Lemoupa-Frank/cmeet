package camtrack.meet.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "User", schema = "public")
public class Model_User {
    @Id
    private  String userId;
    private  String display_name;
    private  String number;
    private  String department;
    private  String password;

    /**
     * A constructor to fully initialize user for
     * his session
     *
     * @param userId      - user e-mail
     * @param display_name - user display name
     * @param number      - user phone number
     * @param department  - user department
     * @param password    - Password of the user
     */
    public Model_User(String userId, String display_name, String number, String department, String password) {
        this.userId = userId;
        this.display_name = display_name;
        this.number = number;
        this.department = department;
        this.password = password;
    }

    /**
     * A constructor used for user which are not logged
     * this helpfull in adding attendees or storing attendees
     * @param userId      user email
     * @param password    users display password
     */
    public Model_User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    // Hibernate expects entities to have a no-arg constructor,

    public Model_User() {
    }
    public String getUserId() {
        return userId;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getNumber() {
        return number;
    }

    public String getDepartment() {
        return department;
    }

    public String getPassword() {
        return password;
    }
}
