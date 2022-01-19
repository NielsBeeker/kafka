package kafka.models;

import java.util.ArrayList;

public class Consumer {
    // ID
    private final Integer consumerId;
    private ArrayList<Integer> partitionsId;
    // Consumer group ID

    public Consumer(Integer consumerId) {
        this.consumerId = consumerId;
        this.partitionsId = new ArrayList<>();
    }

    public void setPartitionsId(ArrayList<Integer> partitionsId) {
        this.partitionsId = partitionsId;
    }

    public ArrayList<Integer> getPartitionsId() {
        return partitionsId;
    }

    public boolean addPartition(final Integer partitionId) {
        return this.partitionsId.add(partitionId);
    }
}
