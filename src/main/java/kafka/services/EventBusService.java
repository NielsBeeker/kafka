package kafka.services;

import java.util.Optional;
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


    // Create topic with nb of partitions
    public createTopic(Integer nbPartitions) {

    }

    // Needs to check if subject exists, then if consumer group exists
    // Finally returns group id where the consumer has been added, then to be used in poll function
    public Optional<Integer> subscribe(final String subject, final int consumerGroupId) {
        if (!this.eventBus.getTopics().containsKey(subject)) {
            logger.log(Level.WARNING, "This channel doesn't exist");
            return Optional.empty();
        }
        // Check if consumer group exists, for the topic with the good subject, then add a consumer in the consumer group
        for (var group : this.consumerGroups) {
            if (group.getGroupId() == consumerGroupId) {
                if (group.addConsumer(new Consumer())) {
                    return Optional.of(group.getGroupId());
                }
                else {
                    logger.log(Level.WARNING, "Cannot add consumer, group is full");
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
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
