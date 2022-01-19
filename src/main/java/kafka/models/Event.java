package kafka.models;

public class Event {
    final private Integer id;
    final private String body;
    final private Integer partitionId;

    public Event(Integer id, String body, Integer partitionId) {
        this.id = id;
        this.body = body;
        this.partitionId = partitionId;
    }

    public Integer getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Integer getPartitionId() {
        return partitionId;
    }
}
