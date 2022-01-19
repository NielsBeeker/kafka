package kafka.models;

public class Event {
    final private Integer id;
    final private String body;
    final private EventType eventType;

    public Event(Integer id, String body, EventType eventType) {
        this.id = id;
        this.body = body;
        this.eventType = eventType;
    }

    public Integer getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public EventType getEventType() {
        return eventType;
    }
}
