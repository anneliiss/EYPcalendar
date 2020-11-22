import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.Html;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class GetDataFromMembersPlatform {

    public List<EYPEvent> getEventsByEventTypeFromOnePage(EventType eventType, String baseUrl) {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);

        List<EYPEvent> mpEvents = new ArrayList<>();

        try {
            HtmlPage page = client.getPage(baseUrl);
            List<HtmlElement> eventHtmlElements = page.getByXPath("//section//div[starts-with(@class, 'views-row')]/*");
            for (HtmlElement eventHtmlElement : eventHtmlElements) {
                if (eventHtmlElement.hasAttribute("about")) {
                    EYPEvent currentEvent = getEventInfo(eventHtmlElement, eventType);
                    mpEvents.add(currentEvent);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return mpEvents;


    }

    private EYPEvent getEventInfo(HtmlElement eventHtmlElement, EventType eventType) {
        String name = "";
        String start = "";
        String end = "";
        String location = "";
        String link = "";
        if (eventHtmlElement.hasAttribute("about")) {
            link = getEventLink(eventHtmlElement);
        }
        Iterator<DomElement> childElements = eventHtmlElement.getChildElements().iterator();
        while (childElements.hasNext()) {
            DomElement currentElement = childElements.next();
            if (currentElement.hasAttribute("content")) {
                name = getEventName(currentElement);
            } else if (currentElement.asXml().contains("dc:date")) {
                List<String> dates = getEventDates(currentElement);
                start = dates.get(0);
                end = dates.get(1);
                location = getEventLocation(currentElement);
            }

        }

        EYPEvent currentEvent = new EYPEvent(name, start, end, location, link, eventType);

        return currentEvent;
    }

    private String getEventLink(HtmlElement currentElement) {
        String link = "https://members.eyp.org/" + currentElement.getAttribute("about");
        System.out.println("link: " + link);
        return link;
    }

    private String getEventName(DomElement currentElement) {
        String name = currentElement.getAttribute("content");
        System.out.println("name: " + name);
        return name;
    }

    private List<String> getEventDates(DomElement currentElement) {
        List<String> dates = new ArrayList<>();
        ListIterator<HtmlElement> timeElements = currentElement.getElementsByTagName("span").listIterator();
        while (timeElements.hasNext()) {
            HtmlElement currentChild = timeElements.next();
            if (currentChild.hasAttribute("content")) {
                if (currentChild.asXml().contains("start")) {
                    dates.add(currentChild.getAttribute("content")); //start date
                    String start = currentChild.getAttribute("content");
                    System.out.println("start: " + start);
                } else if (currentChild.asXml().contains("single")) {
                    dates.add(currentChild.getAttribute("content")); //start date
                    dates.add(currentChild.getAttribute("content")); //end date is the same as start
                    String start = currentChild.getAttribute("content");
                    System.out.println("start: " + start);
                    String end = currentChild.getAttribute("content");
                    System.out.println("end: " + end);
                } else {
                    dates.add(currentChild.getAttribute("content")); //end date (not the same as start)
                    String end = currentChild.getAttribute("content");
                    System.out.println("end: " + end);
                }
            }
        }
        return dates;

    }

    private String getEventLocation(DomElement currentElement) {
        List<HtmlElement> locationElement = currentElement.getElementsByTagName("a");
        String location = locationElement.get(0).getTextContent();
        System.out.println("loc: " + location);
        return location;
    }


}
