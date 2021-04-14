import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class EYPCalendar {

    private static MembersPlatformScraper getData = new MembersPlatformScraper();
    private static CalendarActions calendarActions = new CalendarActions();
    private static EventDao eventDao = new EventDao();
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        createEventsForAllEventTypes();
        //EventType eventType = EventType.INTERNATIONALFORUM;
        //createEventsForOneEventType(eventType);
        System.out.println("Done!");

    }


    private static void createEventsForAllEventTypes(){

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

    private static void createEventsForOneEventType(EventType eventType){
        List<EventData> eventsByEventType = getData.getEventsByEventType(eventType);
        for (EventData event : eventsByEventType) {
            createNewCalendarEvent(event);
        }
    }


    private static void createNewCalendarEvent(EventData mpEvent){

        if (eventDao.getEventByName(mpEvent.getName()) != null) {
            logger.info(String.format("Skipped event: %s, as it exists in the database", mpEvent.getName()));
            return;
        }

        String calendarId = mpEvent.getEventType().getCalendarId();
        EventData event = calendarActions.createEvent(mpEvent, calendarId);

        eventDao.insert(event);
        logInsertedEvent(event);
    }

    private static void logInsertedEvent(EventData event){
        logger.info(String.format("Added event: %s, to calendars 'All EYP events' and '%s'", event.getName(), event.getEventType().getName()));

    }
}
