package clustering.algorithm;


import clustering.model.Partition;

public interface ClusteringListener<T> {
	public void intermediatePartition(Partition<T> part);
}
