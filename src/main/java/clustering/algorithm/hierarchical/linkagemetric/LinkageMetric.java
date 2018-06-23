package clustering.algorithm.hierarchical.linkagemetric;

import clustering.model.Cluster;

/**
 * Folosit la clustering hierarhic pentru a decide care clusteri se reunesc (Se
 * reunesc cele mai apropiate)
 *
 * @author istvan
 *
 * @param <T>
 */
public interface LinkageMetric<T> {
	double metric(Cluster<T> a, Cluster<T> b);
}
