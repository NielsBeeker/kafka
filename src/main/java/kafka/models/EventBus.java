package kafka.models;

import java.util.ArrayList;

public class EventBus {
    private ArrayList<Topic> topics;
    final private Integer nbTopics;
    final private Integer nbPartitionTopic = 5;
    private Integer nbPartition = 0;

    public EventBus(Integer nbTopics, Integer nbPartitionTopic) {
        this.nbTopics = nbTopics;
        this.topics = new ArrayList<>();
        for (int i = 0; i < this.nbTopics; i++) {
            this.topics.add(new Topic(i, this.nbPartitionTopic, this.nbPartition));
            nbPartition += nbPartitionTopic;
        }
    }
}
