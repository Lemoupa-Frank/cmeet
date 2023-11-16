package camtrack.meet.cmeet;

import camtrack.meet.cmeet.meets_model.UserMeetings;
import camtrack.meet.cmeet.meets_model.UserMeetingsPK;
import camtrack.meet.cmeet.meets_model.camtrackmeets;
import camtrack.meet.cmeet.repository.meets_service;
import camtrack.meet.cmeet.repository.usermeets_service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/store_meet")
public class meets_controller {

    private final meets_service meetsService;
    private final usermeets_service usermeetsService;

    @Autowired
    public meets_controller(meets_service meetsService, usermeets_service usernamesService)
    {
        this.meetsService = meetsService;
        this.usermeetsService = usernamesService;
    }




    @Operation(summary = "Obtenir toutes les réunions")
    @GetMapping("/All")
    public Iterable<camtrackmeets> allEvents()
    {
        return meetsService.getAllUsers();
    }

    @Operation(summary = "Obtenir toutes les réunions d'un utilisateur pour une periode")
    @GetMapping("/findMeetingbyUser")
    public Iterable<camtrackmeets> Myevents(@Parameter(description = "identifiant de l'utilisateur") @RequestParam("userId") String userId,@Parameter(description = "Date de debut") @RequestParam("Startdate") OffsetDateTime start_date,@Parameter(description = "Date de fin") @RequestParam("enddate") OffsetDateTime end_date)
    {
        return meetsService.MyMeets(userId,start_date,end_date);
    }
    @Operation(summary = "Tous les reunions pour un department pour une periode specific")
    @GetMapping("/DepartmentMeetings")
    public List<camtrackmeets> getDmeetings(@Parameter(description = "Nom du Departement") @RequestParam("DepartmentName") String Departement_name,@Parameter(description = "Date de debut") @RequestParam("Startdate") LocalDate start_date,@Parameter(description = "Date de fin") @RequestParam("enddate") LocalDate end_date)
    {
        return meetsService.Departmentmeets(Departement_name,start_date,end_date);
    }

    @Operation(summary = "Le nombre de reunion eu par çe departement pour une periode")
    @GetMapping("countOFMeetingsDepart")
    public int getNumberofMeetings(@Parameter(description = "Nom du Departement") @RequestParam("DepartmentName") String Departement_name,@Parameter(description = "Date de debut") @RequestParam("Startdate") LocalDate start_date,@Parameter(description = "Date de fin") @RequestParam("enddate") LocalDate end_date)
    {
        return meetsService.DepartmentmeetsCount(Departement_name,start_date,end_date);
    }

    @Operation(summary = "Mettre à jour une réunion d'un utilisateur")
    @PostMapping("/update_meetings")
    public void update_meetings(@RequestBody camtrackmeets cm)
    {
        meetsService.update_user_meeting(cm);
    }


    @Operation(summary = "Mettre à jour les détails de la réunion d'un utilisateur")
    @PostMapping("/update_user_meetings")
    public void update_attendee(@Parameter(description = "les details de la reunion") @RequestBody UserMeetings um)
    {
        
        String SignatureFolder = "cmeetSignatures";
        UserMeetings attendee;
        attendee  = um;
        String filePath =   File.pathSeparator
                + SignatureFolder 
                + File.separator 
                + um.getUserMeetingsPK().getUserId()
                + ".jpg";
        String absolutePath = new File(filePath).getAbsolutePath();
        attendee.setSignature(absolutePath);
        try (FileOutputStream fos = new FileOutputStream(absolutePath)) {
            fos.write(attendee.getSignature_data());
            System.out.println("Signature written successfully.");
            fos.close();
             System.out.println(filePath);
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
        usermeetsService.update(attendee);
    }

    /**
     * GETS all the participants for a particular meeting
     * with their roles only
     * @param meetingsId unique identifier for a meeting
     * @return Returns all attendees of the meet
     */

    @Operation(summary = "Obtenir les détails de tous les participants à une réunion")
    @GetMapping("/meeting_attendance")
    public List<UserMeetings> Attendee_Details(@Parameter(description = "identifiant de la réunion") @RequestParam("meetingsId") String meetingsId)
    {
        List<UserMeetings> LUM = usermeetsService.findusersbyMeets(meetingsId);
       for (UserMeetings userMeetings : LUM) {
            try (FileInputStream fis = new FileInputStream(userMeetings.getSignature())) {
                userMeetings.setSignature_data(fis.readAllBytes());
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(Arrays.toString(userMeetings.getSignature_data()));
            System.out.println("Signature written successfully.");
        }
        return LUM;
    }

    @Operation(summary = "Obtenir les détails de tous les participants pour toutes les réunions")
    @GetMapping("/MeetsCheck")
    public Iterable<UserMeetings> viewEventChecks()
    {
        return usermeetsService.getAllUsers();
    }

    @Operation(summary = "Obtenir une Reunion")
    @GetMapping("/Get_a_meeting")
    public camtrackmeets get_a_meet(@Parameter(description = "identifiant de la réunion") @RequestParam("meetingsId") String meetingsId)
    {
        if(meetsService.getmeeting(meetingsId).isPresent())
        {
            return meetsService.getmeeting(meetingsId).get();
        }
        return null;
    }

    /**
     * This functions inserts into the meeting and usermeetings tables preferably every day
     * all the meeting process per request contain the meetings the user making the request is
     * part of
     * @param myevent The camtrack event gotten from the Calendar Events
     * @return still to be determined
     */

    @Operation(summary = "Ajouter une nouvelle réunion")
    @PostMapping
    public List<camtrackmeets> addmeet(@RequestBody List<camtrackmeets> myevent)
    {
        OffsetDateTime presentTime = OffsetDateTime.now();
        LocalDate currentDate = LocalDate.now();
        LocalTime endOfDay = LocalTime.MAX;
        OffsetDateTime endOfTheDay = OffsetDateTime.of(currentDate, endOfDay, ZoneOffset.UTC);

        camtrackmeets cm;
        UserMeetings UM = null;
        UserMeetingsPK UPK;
        for (camtrackmeets ev : myevent)
        {
            cm = new camtrackmeets();
            UM = new UserMeetings();
            UPK = new UserMeetingsPK(ev.getMeetingId(),ev.getUserid());

            cm.setMeetingId(ev.getMeetingId());
            cm.setLocation(ev.getLocation());
            cm.setNumberOfParticipants(ev.getNumberOfParticipants());
            cm.setOwner(ev.getOwner());
            System.out.println(ev.getDateofcreation());
            cm.setDateofcreation(ev.getDateofcreation());
            cm.setStartdate(ev.getStartdate());
            cm.setEnddate(ev.getEnddate());
            cm.setDescription(ev.getDescription());
            cm.setAttendee(ev.getAttendee());
            cm.setTitle(ev.getTitle());
            cm.setUpdateComment("Created by " + cm.getOwner());
            cm.setMeetingupdateTime(ev.getMeetingupdateTime());

            UM.setRole(ev.getOwner().equals(ev.getUserid())?"owner":"Attendee");
            UM.setUserMeetingsPK(UPK);
            UM.setSignable(false);
            UM.setSignature(null);


            try{
                if(!meetsService.check_if_meeting_exixt(cm.getMeetingId()))
                {this.meetsService.Save(cm);}
            }
            catch (Exception e)
            {
                System.out.println("Unable to Insert in user_meetings surely due to a foreign Key constraint" + e);
            }
            try
            {
                if(!usermeetsService.ifUserMeets(UM))
                {this.usermeetsService.Save(UM);}
            }
            catch (Exception e)
            {
                System.out.println("Unable to Insert in user_meetings surely due to a foreign Key constraint" + e);
            }
        }

        System.out.println("Present Time: " + presentTime);
        System.out.println("End of the Day: " + endOfTheDay);
        return meetsService.todays_meets(UM != null ? UM.getUserMeetingsPK().getUserId() : null);
    }

}