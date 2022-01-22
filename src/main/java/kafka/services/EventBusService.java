package kafka.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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

    public ArrayList<Event> poll(final Integer userId, final String subject, final Integer groupId, final Integer nbEvents, final long timeout) {
        // While time < timeout, get events from subject, with groupId
        LocalTime currentTime = LocalTime.now();
        var maxTime = currentTime.plusSeconds(timeout);
        var results = new ArrayList<Event>();

        for(var consumerGroup : consumerGroups) {
            if(consumerGroup.getGroupId() == groupId) {
                if (consumerGroup.getSubject() != subject) {
                    logger.log(Level.WARNING, "Wrong subject given");
                    return results;
                }
            }
        }

        var topic = this.eventBus.getTopic(subject);
        if (topic == null) {
            logger.log(Level.WARNING, "Wrong subject given");
            return results;
        }
        // Check groupId existe, et est lié au topic
        while (currentTime.isBefore(maxTime) || results.size() <= nbEvents) {
            // Get data from userId in groupId

            currentTime = LocalTime.now();
        }
        return results;
    }

}
