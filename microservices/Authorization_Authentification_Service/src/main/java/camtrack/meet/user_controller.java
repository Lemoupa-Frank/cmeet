package camtrack.meet;

import camtrack.meet.repository.user_service;
import camtrack.meet.user.Model_User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")

public class user_controller {

    private final user_service userService;

    @Autowired
    public user_controller(user_service userService) {
        this.userService = userService;
    }


    @Operation(summary = "Obtenir tous les utilisateur")
    @GetMapping
    public Iterable<Model_User> findAllEmployees() {
        return userService.getAllUsers();
    }

    @Operation(summary = "se connecter a son compte")
    @GetMapping("/login")
    public Model_User findUser(@Parameter(description = "identifiant de l'utilisateur") @RequestParam("userId") String userId,@Parameter(description = "mot de passe de l'utilisateur") @RequestParam("password") String password)
    {
        Model_User user;
        user = userService.getUser(userId);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }

    @Operation(summary = "Ajouter un utilisateur")
    @PostMapping
    public Model_User addOneEmployee(@RequestBody Model_User user)
    {
        return this.userService.Save(user);
    }

    @Operation(summary = "obtenir l'utilisateur à partir de son identifiant")
    @PostMapping("getUser")
    public Model_User getUserFromID(@Parameter(description = "identifiant de l'utilisateur") @RequestParam("userId") String userId)
    {
        return userService.getUser(userId);
    }
}
