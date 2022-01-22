package kafka.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class EventBus {
    private HashMap<String, Topic> topics;
    final private Integer nbPartitionTopic = 5; // Fixed size

    public EventBus() {
        this.topics = new HashMap<>();
    }

    public boolean createTopic(final String subject) {
        var lowerSubject = subject.toLowerCase();
        var newTopic = new Topic(lowerSubject, nbPartitionTopic);
        return this.topics.put(lowerSubject, newTopic) != null;
    }

    public HashMap<String, Topic> getTopics() {
        return topics;
    }

    public Topic getTopic(String subject) {
        return this.topics.get(subject);
    }
}
// TOPIC
// a la base, aucun topic, quandn ouveau msg, creation nouveau channel => topic