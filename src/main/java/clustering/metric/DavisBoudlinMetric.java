package clustering.metric;

import clustering.distance.CosineDistance;
import clustering.distance.IDistance;
import clustering.model.Cluster;
import clustering.model.Partition;
import clustering.model.VectorModelProvider;

/**
 * Calculeaza masura DavisBoudlin pentru o partitie
 *
 * @author istvan
 *
 */
public class DavisBoudlinMetric<T> implements PartitionMetric<T> {
	private static IDistance<double[]> dist = new CosineDistance();
	private VectorModelProvider<T> vectModel;

	public DavisBoudlinMetric(VectorModelProvider<T> vectModel) {
		this.vectModel = vectModel;
	}

	public double compute(Partition<T> currentPart) {
		double davisBoudlin = 0;
		for (int i = 0; i < currentPart.getNRClusters(); i++) {
			double max = 0;
			// double sum = 0;
			for (int j = i + 1; j < currentPart.getNRClusters(); j++) {
				Cluster<T> cl1 = currentPart.get(i);
				Cluster<T> cl2 = currentPart.get(j);
				double[] cl1Centre = MetricUtils.computeClusterCenter(cl1,
						vectModel);
				double[] cl2Centre = MetricUtils.computeClusterCenter(cl2,
						vectModel);
				double val = (MetricUtils.computeAverageObjectDistance(
						cl1Centre, cl1, vectModel) + MetricUtils
						.computeAverageObjectDistance(cl2Centre, cl2, vectModel))
						/ dist.distance(cl1Centre, cl2Centre);
				if (val > max) {
					max = val;
				}
				// sum += val;
			}
			davisBoudlin += max;
			// davisBoudlin += sum;
		}
		davisBoudlin = davisBoudlin / currentPart.getNRClusters();
		return davisBoudlin;
	}
}
