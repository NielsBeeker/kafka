package kafka.models;

import java.util.ArrayList;
import java.util.HashMap;

public class ConsumerGroup {
    private Integer groupId;
    private ArrayList<Consumer> consumers;
    private HashMap<Integer, Integer> offsetPartition; // hash (partitionsid, offset)
    private String subject;

    public ConsumerGroup() {
        this.consumers = new ArrayList<>();
        this.offsetPartition = new HashMap<>();
        this.subject = null;
    }

    public ArrayList<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(ArrayList<Consumer> consumers) {
        this.consumers = consumers;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void addOffset(Integer partitionId) {
        var value = this.offsetPartition.get(partitionId);
        this.offsetPartition.put(partitionId, value + 1);
    }

}


