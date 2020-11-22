public enum EventType {

    NATIONALSESSION("National Selection Session", "2", "1au7lj7da6ifhdp3q12lff6s04@group.calendar.google.com"),
    REGIONALSESSION("Regional Selection Session", "3", "4mpqg49n6va1ojb8l91ppmvt50@group.calendar.google.com"),
    INTERNATIONALFORUM("International Forum", "4", "fqgfd2k9en8s382hup8nkcltqk@group.calendar.google.com"),
    INTERNATIONALSESSION("International Session", "5", "hf3f42lbbgb8jkjj503d9mspa0@group.calendar.google.com"),
    INTERNATIONALTRAINING("International Training", "70", "mi8fiajvsjnupc2ersci3uoit4@group.calendar.google.com"),
    NATIONALTRAINING("National Training", "6", "3ofi6josh0b0ni7buhhnl0v10g@group.calendar.google.com"),
    THINKTANK("Think Tank", "7", "8lh3m8fitlaoucpb4cn9njms18@group.calendar.google.com"),
    SMALLSCALEEYPEVENT("Small scale EYP event", "9", "lac5upsdjsiiuct71c2eo3ktr8@group.calendar.google.com"),
    MEMBEREVENT("Member Event", "10", "pvv72qq8pok3p042smv2uhr1r0@group.calendar.google.com"),
    OTHER("Other", "11", "4p4q66jnu04maiocb2pt59e9ro@group.calendar.google.com"),
    ALL("All EYP events", "All", "spalq0uqlp0hcfpdibkdged8u8@group.calendar.google.com");

    private final String name;
    private final String membersPlatformId;
    private final String calendarId;

    EventType(String name, String membersPlatformId, String calendarId) {
        this.name = name;
        this.membersPlatformId = membersPlatformId;
        this.calendarId = calendarId;
    }

    public String getName() {
        return name;
    }

    public String getMembersPlatformId() {
        return membersPlatformId;
    }

    public String getCalendarId() {
        return calendarId;
    }
}
