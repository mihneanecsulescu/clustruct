package clustering.algorithm.hierarchical.linkagemetric;

import clustering.distance.IDistance;
import clustering.model.Cluster;

public class AvgLinkageMetric<T> implements LinkageMetric<T> {
	private IDistance<T> distance;

	public AvgLinkageMetric(IDistance<T> distance) {
		this.distance = distance;
	}

	@Override
	public double metric(Cluster<T> a, Cluster<T> b) {
		double dmax = 0;
		for (int i = 0; i < a.getNRObjs(); i++) {
			for (int j = 0; j < b.getNRObjs(); j++) {
				double aux = distance.distance(a.get(i), b.get(j));
				dmax += aux;
				if (dmax == distance.getIninity()) {
					return distance.getIninity();
				}
			}
		}
		return dmax / (double) (a.getNRObjs() * b.getNRObjs());
	}

}
