package clustering.algorithm.hierarchical.stopcondition;


import clustering.model.Partition;

public class NumberOfClustersStopCondition<T> implements
		ClusteringStopCondition<T> {
	private int nrDesiredClusters;

	public NumberOfClustersStopCondition(int nrDesiredClusters) {
		this.nrDesiredClusters = nrDesiredClusters;
	}

	@Override
	public boolean isStopConditionReached(Partition<T> currentPart) {
		return nrDesiredClusters > 0
				&& currentPart.getNRClusters() == nrDesiredClusters;
	}

}
