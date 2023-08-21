package camtrack.meet;

import camtrack.meet.repository.user_service;
import camtrack.meet.user.Model_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")

public class user_controller {

    private final user_service userService;

    @Autowired
    public user_controller(user_service userService) {
        this.userService = userService;
    }
    @GetMapping
    public Iterable<Model_User> findAllEmployees() {
        return userService.getAllUsers();
    }

    @GetMapping("/login")
    public Model_User findUser(@RequestParam("userId") String userId, @RequestParam("password") String password)
    {
        Model_User user;
        user = userService.getUser(userId);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }

    @PostMapping
    public Model_User addOneEmployee(@RequestBody Model_User user)
    {
        return this.userService.Save(user);
    }
}
