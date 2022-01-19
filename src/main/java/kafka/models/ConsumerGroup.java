package kafka.models;

import java.util.ArrayList;
import java.util.HashMap;

public class ConsumerGroup {
    private HashMap<Consumer, ArrayList<Integer>> consumers;
    final private Integer partitionId;
    final private Integer topicId;

    public ConsumerGroup(Integer topicId, Integer partitionId) {
        this.partitionId = partitionId;
        this.consumers = new HashMap<>();
    }

    public boolean

}
