import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {



    public static void main(String[] args) {


        List<EYPEvent> mpEventsList = getEventsFromMembersPlatform();
        for (EYPEvent mpEvent : mpEventsList) {
            createNewCalendarEvent(mpEvent);
        }
        System.out.println("Done! Hopefully...");

    }




    public static List<EYPEvent> getEventsFromMembersPlatform(){

        GetDataFromMembersPlatform getData = new GetDataFromMembersPlatform();

        EventType eventType = EventType.REGIONALSESSION;

        UrlParams urlParams = new UrlParams();
        urlParams.setEventType(eventType.getMembersPlatformId());
        String baseUrl = urlParams.toString();

        return getData.getEventsByEventTypeFromOnePage(eventType, baseUrl);
    }

    public static void createNewCalendarEvent(EYPEvent mpEvent){

        CalendarActions calendarActions = new CalendarActions();

        String calendarId = mpEvent.getEventType().getCalendarId();

        calendarActions.createEvent(mpEvent, calendarId);

    }


}
