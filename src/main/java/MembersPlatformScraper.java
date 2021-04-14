import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.*;

public class MembersPlatformScraper {

    public List<EventData> getEventsByEventType(EventType eventType) {
        UrlParams urlParams = new UrlParams();
        urlParams.setEventType(eventType.getMembersPlatformId());
        urlParams.setDate("1");

        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);

        List<EventData> mpEvents = new ArrayList<>();

        int pageNr = 0;

        try {
            while (true) {
                urlParams.setPageNr(String.valueOf(pageNr));
                String baseUrl = urlParams.toString();
                HtmlPage page = client.getPage(baseUrl);
                List<HtmlElement> eventHtmlElements = page.getByXPath("//section//div[starts-with(@class, 'views-row')]/*");
                if (eventHtmlElements.size() < 1) {
                    //no more events
                   return mpEvents;
                }
                for (HtmlElement eventHtmlElement : eventHtmlElements) {
                    if (eventHtmlElement.hasAttribute("about")) {
                        EventData currentEvent = getEventInfo(eventHtmlElement, eventType);
                        mpEvents.add(currentEvent);
                    }
                }
                pageNr++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return mpEvents;
    }

    private EventData getEventInfo(HtmlElement eventHtmlElement, EventType eventType) {
        String name = "";
        String start = "";
        String end = "";
        String location = "";
        String link = "";
        if (eventHtmlElement.hasAttribute("about")) {
            link = getEventLink(eventHtmlElement);
        }
        for (DomElement currentElement : eventHtmlElement.getChildElements()) {
            if (currentElement.hasAttribute("content")) {
                name = getEventName(currentElement);
            } else if (currentElement.asXml().contains("dc:date")) {
                List<String> dates = getEventDates(currentElement);
                start = dates.get(0).substring(0, 10);
                end = dates.get(1).substring(0, 10);
                //get just the date without the time and timezone
                location = getEventLocation(currentElement);
            }

        }
        EventData eventData = EventData.builder()
                .name(name)
                .start(start)
                .end(end)
                .location(location)
                .link(link)
                .eventType(eventType)
                .build();

        return eventData;
    }

    private String getEventLink(HtmlElement currentElement) {
        String link = "https://members.eyp.org" + currentElement.getAttribute("about");
        return link;
    }

    private String getEventName(DomElement currentElement) {
        String name = currentElement.getAttribute("content");
        return name;
    }

    private List<String> getEventDates(DomElement currentElement) {
        List<String> dates = new ArrayList<>();
        for (HtmlElement currentChild : currentElement.getElementsByTagName("span")) {
            if (currentChild.hasAttribute("content")) {
                if (currentChild.asXml().contains("start")) {
                    String start = currentChild.getAttribute("content");
                    dates.add(start); //start date

                } else if (currentChild.asXml().contains("single")) {
                    String start = currentChild.getAttribute("content");
                    String end = currentChild.getAttribute("content");
                    dates.add(start); //start date
                    dates.add(end); //end date is the same as start

                } else {
                    String end = currentChild.getAttribute("content");
                    dates.add(end); //end date (not the same as start)
                }
            }
        }
        return dates;

    }

    private String getEventLocation(DomElement currentElement) {
        List<HtmlElement> locationElement = currentElement.getElementsByTagName("a");
        String location = locationElement.get(0).getTextContent();
        return location;
    }
}
