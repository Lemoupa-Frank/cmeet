package camtrack.meet.cmeet.repository;

import camtrack.meet.cmeet.meets_model.camtrackmeets;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface meets_repository extends CrudRepository<camtrackmeets, String>
{
   // @Query("SELECT m FROM camtrackmeets m WHERE m.startdate >= CURRENT_DATE AND m.startdate < CURRENT_DATE + 1")
   @Query(value = "SELECT m.*\n" +
           "FROM \"meeting\" m\n" +
           "JOIN \"user_meetings\" um ON m.meeting_id = um.meeting_id\n" +
           "JOIN \"user\" u ON um.user_id = u.user_id\n" +
           "WHERE u.department = :department AND m.startdate >= :startDate AND m.enddate <= :endDate",nativeQuery = true)
   List<camtrackmeets> DepartmentMeetings(String department, LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT COUNT(*)\n" +
            "FROM \"meeting\" m\n" +
            "JOIN \"user_meetings\" um ON m.meeting_id = um.meeting_id\n" +
            "JOIN \"user\" u ON um.user_id = u.user_id\n" +
            "WHERE u.department = :department AND m.startdate >= :startDate AND m.enddate <= :endDate",nativeQuery = true)
    int DepartmentMeetingsCount(String department, LocalDate startDate, LocalDate endDate);
   @Query("SELECT m FROM camtrackmeets m INNER JOIN UserMeetings um ON m.meetingId = um.userMeetingsPK.meetingId WHERE um.userMeetingsPK.userId = :userId AND m.startdate >= CURRENT_DATE - 60 AND m.startdate < CURRENT_DATE + 1")
   List<camtrackmeets> todays_meets_for_user(String userId);

    @Query("SELECT m FROM camtrackmeets m INNER JOIN UserMeetings um ON m.meetingId = um.userMeetingsPK.meetingId WHERE um.userMeetingsPK.userId = :userId AND m.startdate >= CURRENT_DATE - 60 AND m.startdate < CURRENT_DATE + 1")
    List<camtrackmeets> departmentMeets(String userId);

    @Transactional
    @Modifying
    @Query("""
            update camtrackmeets c set c.numberOfParticipants = :numberOfParticipants, c.location = :location, c.startdate = :startdate, c.dateofcreation = :dateofcreation, c.enddate = :enddate, c.owner = :owner, c.description = :description, c.title = :title, c.attendee = :attendee
            where c.meetingId = :meetingId""")
    int upadteMeetin(Integer numberOfParticipants, String location, OffsetDateTime startdate, OffsetDateTime dateofcreation, OffsetDateTime enddate, String owner, String description, String title, String[] attendee, String meetingId);
    @Query("SELECT m from camtrackmeets m INNER JOIN UserMeetings um ON m.meetingId=um.userMeetingsPK.meetingId WHERE um.userMeetingsPK.userId = :userId")
    List<camtrackmeets> findUserMeets(String userId);

    /*
   @Query(value = "SELECT m.*\n" +
           "FROM \"meeting\" m\n" +
           "JOIN \"user_meetings\" um ON m.meeting_id = um.meeting_id\n" +
           "JOIN \"user\" u ON um.user_id = u.user_id\n" +
           "WHERE u.department = :department AND m.startdate >= :startDate AND m.enddate <= :endDate",nativeQuery = true)
     */
}