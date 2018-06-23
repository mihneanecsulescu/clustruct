package clustering.algorithm.hierarchical.linkagemetric;

import clustering.distance.IDistance;
import clustering.model.Cluster;

public class MinLinkageMetric<T> implements LinkageMetric<T> {
	private IDistance<T> distance;

	public MinLinkageMetric(IDistance<T> distance) {
		this.distance = distance;
	}

	@Override
	public double metric(Cluster<T> a, Cluster<T> b) {
		double dmin = Double.MAX_VALUE;
		for (int i = 0; i < a.getNRObjs(); i++) {
			for (int j = 0; j < b.getNRObjs(); j++) {
				double aux = distance.distance(a.get(i), b.get(j));
				if (dmin > aux) {
					dmin = aux;
				}
			}
		}
		return dmin;
	}
}
