import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UrlParams {

    private static final String main = "https://members.eyp.org/events";
    private static final String dateText = "?event_date=";
    private static final String countryText = "&field_associated_nc_value=";
    private static final String eventTypeText = "&field_event_type_tid=";
    private static final String pageNrText = "&page=";


    private String date = "All";
    private String country = "All";
    private String eventType = "All";
    private String pageNr = "0";

    @Override
    public String toString(){
        return main + dateText + date + countryText + country + eventTypeText + eventType + pageNrText + pageNr;
    }

}
