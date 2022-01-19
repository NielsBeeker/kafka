package kafka.models;

import java.util.ArrayList;

public class Topic {
    final private Integer topicId;
    private ArrayList<Partition> partitions;
    final private Integer nbPartitions;

    public Topic(Integer topicId, Integer nbPartitions) {
        this.topicId = topicId;
        this.nbPartitions = nbPartitions;
        partitions = new ArrayList<>();
        for (int i = 0; i < nbPartitions; i++) {
            partitions.add(new Partition(i));
        }

    }
}
