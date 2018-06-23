package clustering.algorithm.hierarchical.stopcondition;

import clustering.model.Partition;

public interface ClusteringStopCondition<T> {
	boolean isStopConditionReached(Partition<T> currentPart);
}
