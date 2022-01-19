package kafka.models;

import org.w3c.dom.events.EventException;

import java.util.ArrayList;

public class Partition {
    final private Integer partitionId;
    private Integer eventIndex = 0;
    private ArrayList<Event> events;

    public Partition(Integer partitionId) {
        this.partitionId = partitionId;
        this.events = new ArrayList<>();
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public Integer getPartitionId() {
        return partitionId;
    }

    public void setEventIndex(final Integer eventIndex) {
        this.eventIndex = eventIndex;
    }

    public boolean addEvent(final Event event) {
        return this.events.add(event);
    }
}
