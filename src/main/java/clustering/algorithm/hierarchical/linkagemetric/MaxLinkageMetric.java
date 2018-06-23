package clustering.algorithm.hierarchical.linkagemetric;

import clustering.distance.IDistance;
import clustering.model.Cluster;

public class MaxLinkageMetric<T> implements LinkageMetric<T> {
	private IDistance<T> distance;
	public Object auxA, auxB;

	public MaxLinkageMetric(IDistance<T> distance) {
		this.distance = distance;
	}

	@Override
	public double metric(Cluster<T> a, Cluster<T> b) {
		double dmax = Double.MIN_VALUE;
		for (int i = 0; i < a.getNRObjs(); i++) {
			for (int j = 0; j < b.getNRObjs(); j++) {
				double aux = distance.distance(a.get(i), b.get(j));
				if (dmax < aux) {
					dmax = aux;
					auxA = a.get(i);
					auxB = b.get(j);
				}
				if (dmax == distance.getIninity()) {
					return dmax;
				}
			}
		}
		return dmax;
	}


	public Object getAuxA() {
		return auxA;
	}

	public Object getAuxB() {
		return auxB;
	}
}
