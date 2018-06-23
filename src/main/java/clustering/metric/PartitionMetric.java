package clustering.metric;

import clustering.model.Partition;

public interface PartitionMetric<T> {
	double compute(Partition<T> currentPart);
}
