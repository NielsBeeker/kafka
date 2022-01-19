package kafka.models;

import java.util.ArrayList;

public class Topic {
    final private Integer topicId;
    private ArrayList<Partition> partitions;

    public Topic(final Integer topicId, final Integer nbPartitionTopic, final Integer nbPartitions) {
        this.topicId = topicId;
        partitions = new ArrayList<>();
        for (int i = 0; i < nbPartitionTopic; i++) {
            partitions.add(new Partition(nbPartitions + i));
        }

    }
}
