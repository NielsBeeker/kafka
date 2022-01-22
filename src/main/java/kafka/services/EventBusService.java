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

    public ArrayList<Event> poll(final Integer userId, final String subject, final Integer groupId, final Integer nbEvents, final long timeout) {
        // While time < timeout, get events from subject, with groupId
        var results = new ArrayList<Event>();

        var topic = this.eventBus.getTopic(subject);
        if (topic == null) {
            logger.log(Level.WARNING, "Wrong subject given");
            return results;
        }
        // Check groupId existe, et est lié au topic

        ConsumerGroup group = null;

        for(var consumerGroup : consumerGroups) {
            if(consumerGroup.getGroupId() == groupId) {
                group = consumerGroup;
                if (consumerGroup.getSubject() != subject) {
                    logger.log(Level.WARNING, "Wrong group given");
                    return results;
                }
            }
        }
        if (group == null) {
            logger.log(Level.WARNING, "Group ID not found");
            return results;
        }

        var consumer = group.getConsumers().get(userId);

        // Check user id and its partitions
        if (consumer == null) {
            logger.log(Level.WARNING, "Wrong User ID");
            return results;
        }
        var partitions = consumer.getPartitionsId();

        LocalTime currentTime = LocalTime.now();
        var maxTime = currentTime.plusSeconds(timeout);

        // Get data from userId in groupId
        for (var partitionId : partitions) {
            for (var event : topic.getPartitions().get(partitionId).getEvents()) {
                currentTime = LocalTime.now();
                results.add(event);
                group.addOffset(partitionId);
                if (results.size() >= nbEvents || currentTime.isBefore(maxTime)) {
                    break;
                }
            }
            if (results.size() >= nbEvents || currentTime.isBefore(maxTime)) {
                break;
            }
        }
        return results;
    }

}
