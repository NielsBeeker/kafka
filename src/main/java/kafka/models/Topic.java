package kafka.models;

import java.util.ArrayList;

public class Topic {
    private ArrayList<Partition> partitions;
    private String subject;
    private Integer nbPartitionMax;

    public Topic(final String subject, Integer nbPartitionMax) {
        this.subject = subject;
        this.nbPartitionMax = nbPartitionMax;
        partitions = new ArrayList<>();
        for (int i = 0; i < this.nbPartitionMax; i++) {
            partitions.add(new Partition(i));
        }

    }

    public ArrayList<Partition> getPartitions() {
        return partitions;
    }
}
