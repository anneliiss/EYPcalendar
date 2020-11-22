import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;
import lombok.Data;

@Data
public class EYPEvent {
    private String name;
    private EventDateTime start;
    private EventDateTime end;
    private String location;
    private String link;
    private EventType eventType;

    public EYPEvent(String name, String start, String end, String location, String link, EventType eventType) {
        this.name = name;
        this.location = location;
        this.link = link;
        this.eventType = eventType;

        DateTime startDateTime = new DateTime(start);
        this.start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("GMT");
        DateTime endDateTime = new DateTime(end);
        this.end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("GMT");
    }
}
