package clustering.metric;

import clustering.distance.CosineDistance;
import clustering.distance.IDistance;
import clustering.model.Cluster;
import clustering.model.Partition;
import clustering.model.VectorModelProvider;

public class SilhouetteMetric<T> implements PartitionMetric<T> {
	private static IDistance<double[]> dist = new CosineDistance();
	private VectorModelProvider<T> vectModel;

	public SilhouetteMetric(VectorModelProvider<T> vectModel) {
		this.vectModel = vectModel;
	}

	public double compute(Partition<T> part) {
		double avg = 0;
		for (int i = 0; i < part.getNRClusters(); i++) {
			Cluster<T> cl1 = part.get(i);
			for (int j = 0; j < cl1.getNRObjs(); j++) {
				double siluette = computeSiluette(part, cl1, j);
				avg += siluette;
			}
		}
		return avg / part.getNRAllObjects();
	}

	public double computeSiluette(Partition<T> part, Cluster<T> cl, int i) {
		// calculam distanta medie fata de obiectele din cluster
		double[] vmi = vectModel.getVectorialModel(cl.get(i));
		double avgDisin = 0;
		for (int j = 0; j < cl.getNRObjs(); j++) {
			if (i == j) {
				continue;
			}

			avgDisin += dist.distance(vmi, vectModel.getVectorialModel(cl
					.get(j)));
		}
		if (cl.getNRObjs() == 1) {
			avgDisin = 0;
		} else {
			avgDisin = avgDisin / (cl.getNRObjs() - 1);
		}
		// calculez distanta medie fata de celalalte obiecte (din alti clusteri)
		double min = Double.MAX_VALUE;
		for (i = 0; i < part.getNRClusters(); i++) {
			Cluster<T> cl2 = part.get(i);
			if (cl == cl2) {
				continue;
			}
			double aux = comp(cl2, vmi);
			if (min > aux) {
				min = aux;
			}
		}

		return (min - avgDisin) / Math.max(min, avgDisin);
	}

	public double comp(Cluster<T> cl, double[] vmi) {
		double avgDisin = 0;
		for (int j = 0; j < cl.getNRObjs(); j++) {
			avgDisin += dist.distance(vmi, vectModel.getVectorialModel(cl
					.get(j)));
		}
		avgDisin = avgDisin / (cl.getNRObjs());
		return avgDisin;
	}
}
