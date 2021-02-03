import java.util.List;

public class EYPCalendar {

    private static GetDataFromMembersPlatform getData = new GetDataFromMembersPlatform();
    private static CalendarActions calendarActions = new CalendarActions();
    private static EventDao eventDao = new EventDao();

    public static void main(String[] args) {

        createEventsForAllEventTypes();
        System.out.println("Done!");

    }


    public static void createEventsForAllEventTypes(){

        for (EventType eventType : EventType.values()) {
            if (eventType.name().equals("ALL")) {
                continue;
            }
            List<EventData> eventsByEventType = getData.getEventsByEventType(eventType);
            for (EventData event : eventsByEventType) {
                createNewCalendarEvent(event);
            }
        }

    }


    public static void createNewCalendarEvent(EventData mpEvent){

        if (eventDao.getEventByName(mpEvent.getName()) != null) {
            System.out.println("Event exists!");
            return;
        }

        String calendarId = mpEvent.getEventType().getCalendarId();
        EventData event = calendarActions.createEvent(mpEvent, calendarId);

        eventDao.insert(event);
    }
}
