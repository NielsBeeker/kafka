package kafka.models;

public class Consumer {
    // ID
    private final Integer consumerId;
    // Consumer group ID
    private final Integer consumerGroupId;

    public Consumer(Integer consumerId, Integer consumerGroupId) {
        this.consumerId = consumerId;
        this.consumerGroupId = consumerGroupId;
    }
}
