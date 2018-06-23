package clustering.algorithm.hierarchical;

import clustering.algorithm.ClusteringListener;
import clustering.algorithm.hierarchical.linkagemetric.LinkageMetric;
import clustering.algorithm.hierarchical.linkagemetric.MaxLinkageMetric;
import clustering.algorithm.hierarchical.stopcondition.ClusteringStopCondition;
import clustering.algorithm.hierarchical.stopcondition.NumberOfClustersStopCondition;
import clustering.distance.IDistance;
import clustering.model.Cluster;
import clustering.model.Partition;

import java.util.List;
import java.util.Map;

public class Hierarhic<T> {


	private List<T> objects;
	private LinkageMetric<T> linkMetric;
	private ClusteringListener<T> list;
	private ClusteringStopCondition<T> stopC;
	private HierarhicCache<T> cache = new HierarhicCache<T>();

	public Hierarhic(LinkageMetric<T> lm, List<T> patCand, int nrDesiredClusters) {
		this(lm, patCand, new NumberOfClustersStopCondition<T>(
				nrDesiredClusters));
	}

	public Hierarhic(LinkageMetric<T> lm, List<T> objs,
			ClusteringStopCondition<T> stopC) {
		this.objects = objs;
		linkMetric = lm;
		this.stopC = stopC;
	}

	public Hierarhic(IDistance<T> d, List<T> patCand, int nrDesiredClusters) {
		this(new MaxLinkageMetric<T>(d), patCand,
				new NumberOfClustersStopCondition<T>(nrDesiredClusters));

		// linkMetric = new MaxLinkageMetric<T>(d);
		// linkMetric = new MinLinkageMetric<T>(d);
	}

	public void setClusteringListener(ClusteringListener<T> list) {
		this.list = list;
	}

	public void setHierarhicClusteringListener(
			HierarhicClusteringListener<T> list) {
		this.list = list;
	}

	public Partition<T> clusterizare() {
		Partition<T> part = new Partition<T>();
		for (int i = 0; i < objects.size(); i++) {
			part.addSingletonCluster(objects.get(i));
		}

		boolean change = true;
		while (change) {
			hierarhicStep(part);
			if (stopC.isStopConditionReached(part)) {
				change = false;
			}
			if (list != null) {
				list.intermediatePartition(part);
			}
		}
		return part;
	}

	/**
	 * Pasul facut la fiecare iteratie Se cauta clusteri care sunt apropiati si
	 * se reunesc
	 *
	 * @param part
	 */
	private void hierarhicStepNoCache(Partition<T> part) {
		long tm = System.currentTimeMillis();
		int nrClusters = part.getNRClusters();
		Cluster<T> minCl1 = part.get(0);
		Cluster<T> minCl2 = part.get(1);
		double dmin = dist(minCl1, minCl2, null, Double.MAX_VALUE);

		System.out.println("alta iteratie nrClusters" + nrClusters);
		// caut perechi de clusteri care au distanta minima intre ele
		for (int i = 0; i < nrClusters - 1; i++) {
			Cluster<T> cl1 = part.get(i);
			for (int j = i + 1; j < nrClusters; j++) {
				double auxDist = linkMetric.metric(cl1, part.get(j));
				if (auxDist < dmin) {
					dmin = auxDist;
					minCl1 = cl1;
					minCl2 = part.get(j);
				}
			}
		}

		Cluster<T> c = new Cluster<T>(minCl1, minCl2);
		part.delete(minCl1);
		part.delete(minCl2);
		part.add(c);
		long timp = (System.currentTimeMillis() - tm);
		System.out.println("NR clusteri in partitia curenta:"
				+ part.getNRClusters() + " timp:" + timp);
	}

	private void hierarhicStep(Partition<T> part) {
		long tm = System.currentTimeMillis();
		int nrClusters = part.getNRClusters();
		Cluster<T> minCl1 = part.get(0);
		Cluster<T> minCl2 = part.get(1);
		double dmin = dist(minCl1, minCl2, null, Double.MAX_VALUE);
		System.out.println("alta iteratie nrClusters" + nrClusters);
		// caut perechi de clusteri care au distanta minima intre ele
		for (int i = 0; i < nrClusters - 1; i++) {
			Cluster<T> cl1 = part.get(i);
			// try to avoid the inner 'for' altogether
			HierarhicCache.MinCacheItem min = cache.getMinFromCache(cl1, linkMetric, dmin);
			if (min != null) {
				if (min.dmin < dmin) {
					//linkMetric.metric(cl1, min.minCL)==min.dmin;
					dmin = min.dmin;
					minCl1 = cl1;
					minCl2 = min.minCL;
				}
				continue;
			}
			Cluster<T> minCl2Int = part.get(i + 1);
			double dminInt = dist(cl1, minCl2Int, null, dmin);

			// in order to avoid multiple cache lookup for cl1 in the inner
			// for
			Map<Cluster<T>, Double> cacheLMLine = cache.getLMCacheLine(cl1);
			for (int j = i + 1; j < nrClusters; j++) {
				double auxDist = dist(cl1, part.get(j), cacheLMLine, dmin);
				if (auxDist < dminInt) {
					dminInt = auxDist;
					minCl2Int = part.get(j);
				}

			}
			cache.addMinToCache(cl1, dminInt, minCl2Int);
			if (dminInt < dmin) {
				dmin = dminInt;
				minCl1 = cl1;
				minCl2 = minCl2Int;
			}
		}
		notifyToBeMerged(part, minCl1, minCl2);

		Cluster<T> c = new Cluster<T>(minCl1, minCl2);
		part.delete(minCl1);
		part.delete(minCl2);
		part.add(c);
		cache.partitionChanges(minCl1, minCl2, c);
		long timp = (System.currentTimeMillis() - tm);
		System.out.println("NR clusteri in partitia curenta:"
				+ part.getNRClusters() + " timp:" + timp);

	}

	private void notifyToBeMerged(Partition<T> part, Cluster<T> minCl1,
			Cluster<T> minCl2) {
		if (list != null) {
			if (list instanceof HierarhicClusteringListener) {
				linkMetric.metric(minCl1, minCl2);
				((HierarhicClusteringListener<T>) list).clustersToBeMerged(
						part, minCl1, minCl2);
			}
		}
	}

	private double dist(Cluster<T> a, Cluster<T> b,
			Map<Cluster<T>, Double> cacheLine, double currentMin) {
		Double rez = cache.getLMCache(cacheLine, b);
		if (rez != null) {
			return rez;
		}
		// System.out.println("metric computed " + nrClusters);

		double val = linkMetric.metric(a, b);
		cache.putLMCache(a, b, val);
		return val;
	}
}
