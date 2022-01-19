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

    public boolean addTopic(final String subject) {
        var lowerSubject = subject.toLowerCase();
        var newTopic = new Topic(lowerSubject, nbPartitionTopic);
        return this.topics.put(lowerSubject, newTopic) != null;
    }
}
// TOPIC
// a la base, aucun topic, quandn ouveau msg, creation nouveau channel => topic