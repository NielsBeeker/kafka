package kafka.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import kafka.models.*;

import java.util.ArrayList;

public class EventBusService {
    private EventBus eventBus;
    private ArrayList<ConsumerGroup> consumerGroups;
    final private Logger logger;

    public EventBusService(EventBus eventBus, Logger logger) {
        this.eventBus = eventBus;
        this.consumerGroups = new ArrayList<>();
        this.logger = logger;
    }

    public boolean subscribe(final String subject, ConsumerGroup consumerGroup) {
        if (!this.eventBus.getTopics().containsKey(subject)) {
            logger.log(Level.WARNING, "This channel doesn't exist");
            return false;
        }


        this.consumerGroups.add(consumerGroup);
        return true;

    }

    public boolean publish(final String subject, Event event) {
        var topics = this.eventBus.getTopics();
        if (!topics.containsKey(subject)) {
            this.eventBus.createTopic(subject);
        }
        for(Partition partition : topics.get(subject).getPartitions())
        {
            if(partition.getPartitionId() == event.getPartitionId()) {
                partition.addEvent(event);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Event> poll(Consumer consumer, Integer nbEvent, Integer timeout) {
        Topic topic = this.eventBus.getTopics().get(subject);
        ArrayList<Event> events = new ArrayList<>();
        for(var partition : topic.getPartitions()){
            if (consumer.partitionsId.contains(partition.partionId)) {
                if (events.size() == nbEvent)
                    break;
                for (var event : partition) {
                    if (events.size() == nbEvent)
                        break;
                    events.add(event);
                }
            }
        }
        return events;
    }


}
