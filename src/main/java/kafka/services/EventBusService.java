package kafka.services;

import kafka.models.*;

import java.util.ArrayList;
import java.util.HashMap;

public class EventBusService {
    EventBus eventBus;

    public boolean subscribe(String subject, ConsumerGroup consumerGroup) {

        if (!this.eventBus.getTopics().get(subject) && consumerGroup.getSubject())
            return false;
        consumerGroup.setSubject(subject);
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
