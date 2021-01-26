import java.sql.*;

public class EventDao {

    private Connection connect(){
        String url = "jdbc:sqlite:events.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void insert(EventData eventData) {
        String sql = "INSERT INTO events(specific_calendar_event_id, general_calendar_event_id, " +
                "name, start, end, location, link, event_type) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, eventData.getSpecificCalendarEventId());
            ps.setString(2, eventData.getGeneralCalendarEventId());
            ps.setString(3, eventData.getName());
            ps.setString(4, eventData.getStart());
            ps.setString(5, eventData.getEnd());
            ps.setString(6, eventData.getLocation());
            ps.setString(7, eventData.getLink());
            ps.setString(8, eventData.getEventType().name());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public EventData getEventByName(String name){
        String sql = "SELECT * FROM events WHERE name = ?";
        try (Connection conn = this.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                EventData eventData = EventData.builder()
                        .name(name)
                        .specificCalendarEventId(rs.getString("specific_calendar_event_id"))
                        .generalCalendarEventId(rs.getString("general_calendar_event_id"))
                        .start(rs.getString("start"))
                        .end(rs.getString("end"))
                        .location(rs.getString("location"))
                        .link(rs.getString("link"))
                        .eventType(EventType.valueOf(rs.getString("event_type")))
                        .build();
                return eventData;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
