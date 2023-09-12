package camtrack.meet.cmeet;

import camtrack.meet.cmeet.meets_model.UserMeetings;
import camtrack.meet.cmeet.meets_model.UserMeetingsPK;
import camtrack.meet.cmeet.meets_model.camtrackmeets;
import camtrack.meet.cmeet.repository.meets_service;
import camtrack.meet.cmeet.repository.usermeets_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/store_meet")
public class meets_controller {

    private final meets_service meetsService;
    private final usermeets_service usermeetsService;

    @Autowired
    public meets_controller(meets_service meetsService, usermeets_service usermeetsService)
    {
        this.meetsService = meetsService;
        this.usermeetsService = usermeetsService;
    }




    @GetMapping("/All")
    public Iterable<camtrackmeets> allEvents()
    {
        return meetsService.getAllUsers();
    }

    @GetMapping("/findMeetingbyUser")
    public Iterable<camtrackmeets> Myevents()
    {
        String us =  "frankmichel022@gmail.com";
        return meetsService.MyMeets(us);
    }
    @GetMapping("/FindUserByMeetings")
    public Iterable<UserMeetings> Attendee_Details(@RequestParam("meetingsId") String meetingsId)
    {
        return usermeetsService.FindUserByMeets(meetingsId);
    }
    @GetMapping("/MeetsCheck")
    public Iterable<UserMeetings> viewEventChecks()
    {
        return usermeetsService.getAllUsers();
    }
    /**
     * This functions inserts into the meeting and usermeetings tables preferably every day
     * all the meeting process per request contain the meetings the user making the request is
     * part of
     * @param myevent The camtrack event gotten from the Calendar Events
     * @return still to be determined
     */
    @PostMapping
    public camtrackmeets addmeet(@RequestBody List<camtrackmeets> myevent)
    {
        //List<Event> events = myevent.getEvents();
        camtrackmeets cm = null;
        UserMeetings UM;
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
            cm.setDateofcreation(ev.getDateofcreation());
            cm.setStartdate(ev.getStartdate());
            cm.setEnddate(ev.getEnddate());
            cm.setDescription(ev.getDescription());
            cm.setAttendee(ev.getAttendee());
            cm.setTitle(ev.getTitle());


            UM.setRole(ev.getOwner().equals(ev.getUserid())?"owner":"Attendee");
            UM.setUserMeetingsPK(UPK);
            UM.setSignable(false);
            UM.setSignature(" ");

            this.meetsService.Save(cm);
            try {
                this.usermeetsService.Save(UM);
            }
            catch (Exception e)
            {
                System.out.println("Unable to Insert in user_meetings surely due to a foreign Key constraint" + e);
            }
        }
        return cm;
    }

}