import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventData {
    private String specificCalendarEventId;
    private String generalCalendarEventId;
    private String name;
    private String start;
    private String end;
    private String location;
    private String link;
    private EventType eventType;

}
