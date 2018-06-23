package clustering.metric;


import clustering.distance.CosineDistance;
import clustering.distance.IDistance;
import clustering.model.Cluster;
import clustering.model.Partition;
import clustering.model.VectorModelProvider;

public class DunnMetric<T> implements PartitionMetric<T> {
	private static IDistance<double[]> dist = new CosineDistance();
	private VectorModelProvider<T> vectModel;

	public DunnMetric(VectorModelProvider<T> vectModel) {
		this.vectModel = vectModel;
	}

	public double compute(Partition<T> currentPart) {
		double max = 0;
		for (int k = 0; k < currentPart.getNRClusters(); k++) {
			Cluster<T> cl = currentPart.get(k);
			double[] clCentre = MetricUtils.computeClusterCenter(cl,
					vectModel);
			double val = MetricUtils.computeAverageObjectDistance(
					clCentre, cl, vectModel);
			if (max < val) {
				max = val;
			}
		}

		double min1 = Double.MAX_VALUE;
		for (int i = 0; i < currentPart.getNRClusters(); i++) {
			double min2 = Double.MAX_VALUE;
			for (int j = 0; j < currentPart.getNRClusters(); j++) {
				if (j == i) {
					continue;
				}
				Cluster<T> cl1 = currentPart.get(i);
				Cluster<T> cl2 = currentPart.get(j);
				double[] cl1Centre = MetricUtils.computeClusterCenter(
						cl1, vectModel);
				double[] cl2Centre = MetricUtils.computeClusterCenter(
						cl2, vectModel);
				double val = dist.distance(cl1Centre, cl2Centre) / max;
				if (min2 > val) {
					min2 = val;
				}
			}
			if (min1 > min2) {
				min1 = min2;
			}
		}
		return min1;
	}
}
