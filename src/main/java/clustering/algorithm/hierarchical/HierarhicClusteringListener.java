package clustering.algorithm.hierarchical;

import clustering.algorithm.ClusteringListener;
import clustering.model.Cluster;
import clustering.model.Partition;

public interface HierarhicClusteringListener<T> extends ClusteringListener<T> {
	public void clustersToBeMerged(Partition<T> part, Cluster<T> a, Cluster<T> b);
}
