package camtrack.meet;

import camtrack.meet.repository.user_service;
import camtrack.meet.role_record.roles_record;
import camtrack.meet.user.Model_User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")

public class user_controller {

    private final user_service userService;
    roles_record record  = new roles_record();
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
    @PostMapping("/getUser")
    public Model_User getUserFromID(@Parameter(description = "identifiant de l'utilisateur") @RequestParam("userId") String userId)
    {
        return userService.getUser(userId);
    }
    @Operation(summary = "Les utilisateur qui etait absent pour la reunion")
    @GetMapping("/Absentee")
    public List<Model_User> getAbsentees(@Parameter(description = "MeetingID") @RequestParam("MeetingId") String MeetingId)
    {
        return userService.getAbsentee(MeetingId);
    }

    @Operation(summary = "Definir Role")
    @GetMapping("/updateRole")
    public String Define_Role(@Parameter(description = "identifiant de l'utilisateur") @RequestParam("userId") String userId, @Parameter(description = "Donneé pour le role") @RequestParam("Role_Data") String role,@Parameter(description = "Mot de passe du role") @RequestParam("role_identifier") String role_identifier)
    {
        if(record.get_data(role).equals(role_identifier))
        {
            userService.update_role(userId,role);
            return "Success";

        }
        else
        {
            return "Failure";
        }
    }
}
