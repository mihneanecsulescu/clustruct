package clustering.algorithm.hierarchical;

import clustering.algorithm.hierarchical.linkagemetric.LinkageMetric;
import clustering.model.Cluster;

import java.util.HashMap;
import java.util.Map;

/**
 * Used for speedup the Hierarhic clustering Provide caching functionality
 *
 * @author istvan
 *
 * @param <T>
 */
public class HierarhicCache<T> {
	/**
	 * tin minte find max
	 */
	private Map<Cluster<T>, Map<Cluster<T>, Double>> linkageMetricCache = new HashMap<Cluster<T>, Map<Cluster<T>, Double>>();

	public Double getLMCache(Cluster<T> a, Cluster<T> b) {
		Map<Cluster<T>, Double> cache = getLMCacheLine(a);
		if (cache != null) {
			return cache.get(b);
		}
		return null;
	}

	public Double getLMCache(Map<Cluster<T>, Double> cacheLine, Cluster<T> b) {
		if (cacheLine != null) {
			return cacheLine.get(b);
		}
		return null;
	}

	public Map<Cluster<T>, Double> getLMCacheLine(Cluster<T> a) {
		Map<Cluster<T>, Double> cache = linkageMetricCache.get(a);
		return cache;
	}

	public void putLMCache(Cluster<T> a, Cluster<T> b, double v) {
		Map<Cluster<T>, Double> cache = getLMCacheLine(a);
		if (cache == null) {
			cache = new HashMap<Cluster<T>, Double>();
			linkageMetricCache.put(a, cache);
		}
		cache.put(b, v);
	}

	public void removeFromCache(Cluster<T> a) {
		linkageMetricCache.remove(a);
		// minCache.remove(a);
		a.setLabel(null);
	}

	// private Map<Cluster<T>, MinCacheItem> minCache = new HashMap<Cluster<T>,
	// MinCacheItem>();
	/** clusteri ce s-au eliminat in interatia anterioara */
	private Cluster<T> removedCl1;
	private Cluster<T> removedCl2;
	private Cluster<T> addedCl;

	public void partitionChanges(Cluster<T> removed1, Cluster<T> removed2,
			Cluster<T> added) {
		removedCl1 = removed1;
		removedCl2 = removed2;
		addedCl = added;
		removeFromCache(removed1);
		removeFromCache(removed2);
	}

	public void addMinToCache(Cluster<T> a, double dmin, Cluster<T> minCL) {
		// minCache.put(a, new MinCacheItem(dmin, minCL));
		a.setLabel(new MinCacheItem(dmin, minCL));
	}

	public MinCacheItem getMinFromCache(Cluster<T> a, LinkageMetric<T> lm,
			double currentMin) {

		// MinCacheItem cacheItem = minCache.get(a);
		MinCacheItem cacheItem = (MinCacheItem) a.getLabel();
		if (cacheItem == null) {
			return null;
		}
		// trebuie recalculat minimul doar minimul era fata de un cluster care
		// s-a eliminat intre timp
		if (cacheItem.minCL == removedCl1 || cacheItem.minCL == removedCl2) {
			// daca minimul s-a obtinut cu clusteri ce nu mai sunt
			// nu pot trage nici o concluzie
			return null;
		}

		// minimul e cel vechi sau posibil distatanta spre clusterul nou creat
		// in iteratia anterioara
		double dAdded = lm.metric(a, addedCl);
		putLMCache(a, addedCl, dAdded);

		if (dAdded < cacheItem.dmin) {
			cacheItem.dmin = dAdded;
			cacheItem.minCL = addedCl;
		}
		return cacheItem;
	}

	public class MinCacheItem {
		double dmin;
		Cluster<T> minCL;

		public MinCacheItem(double dmin, Cluster<T> minCL) {
			this.dmin = dmin;
			this.minCL = minCL;
		}
	}
}
