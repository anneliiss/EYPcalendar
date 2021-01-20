import java.util.List;

public class Main {

    public static void main(String[] args) {


        List<EventData> mpEventsList = getEventsFromMembersPlatform();
        for (EventData mpEvent : mpEventsList) {
            createNewCalendarEvent(mpEvent);
        }
        System.out.println("Done!");

    }


    public static List<EventData> getEventsFromMembersPlatform(){

        GetDataFromMembersPlatform getData = new GetDataFromMembersPlatform();

        EventType eventType = EventType.REGIONALSESSION;

        UrlParams urlParams = new UrlParams();
        urlParams.setEventType(eventType.getMembersPlatformId());
        String baseUrl = urlParams.toString();

        return getData.getEventsByEventTypeFromOnePage(eventType, baseUrl);
    }

    public static void createNewCalendarEvent(EventData mpEvent){

        CalendarActions calendarActions = new CalendarActions();

        String calendarId = mpEvent.getEventType().getCalendarId();

        calendarActions.createEvent(mpEvent, calendarId);

    }
}
