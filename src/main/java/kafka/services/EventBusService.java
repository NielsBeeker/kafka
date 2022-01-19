package kafka.services;

import kafka.models.*;

import java.util.ArrayList;

public class EventBusService {
    EventBus eventBus;
    private ArrayList<groupConsumer> groupConsumers;

    public EventBusService(EventBus eventBus) {
        this.eventBus = eventBus;
        this.groupConsumers = new ArrayList<>();
    }


    //create topic
    //subscribe
    // poll
    // publish


    public boolean subscribe(Topic topic, groupConsumer groupConsumer) {
        if (groupConsumer.getTopicId())
            return false;
        groupConsumer.setTopicId(topic.topicId);
        return true;
    }



    public void publish(Topic topic, Event event) {
        try{
           this.eventBus.getTopics().get(topic.subject)
           .getPartition(event.getPartitionId().addEvent(event);
        }
        catch (Error er) {
            System.err.println(er);
        }
    }

    public ArrayList<Event> poll(String subject, Consumer consumer, Integer nbEvent, Integer timeout) {
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
