package kafka.models;

import java.util.ArrayList;
import java.util.HashMap;

public class ConsumerGroup {
    private ArrayList<Consumer> consumers;
    private HashMap<Integer, Integer> offsetPartition; // hash (partitionsid, offset)
    final private String channel;

    public ConsumerGroup(final String subject) {
        this.channel = subject;
        this.consumers = new ArrayList<>();
        this.offsetPartition = new HashMap<>();
    }

    public boolean addConsumer(final Consumer consumer) {

        int nbPartition = offsetPartition.keySet().size();
        if (consumers.size() == nbPartition) {
            return false;
        }
        this.consumers.add(consumer);
        this.consumers.forEach(c -> c.setPartitionsId(new ArrayList<>()));


        for (int i = 0, consumerIndex = 0 ; i < nbPartition; i++, consumerIndex++) {
            if (consumerIndex == consumers.size()) {
                consumerIndex = 0;
            }
            this.consumers.get(consumerIndex).addPartition(i);
        }
        return true;
    }

}


