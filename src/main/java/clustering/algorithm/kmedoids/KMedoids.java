package clustering.algorithm.kmedoids;

import clustering.algorithm.ClusteringListener;
import clustering.distance.IDistance;
import clustering.model.Cluster;
import clustering.model.Partition;

import java.util.ArrayList;
import java.util.List;

public class KMedoids<T> {
	/** distantele intre obiecte */
	private IDistance<T> d;
	private ClusteringListener<T> list;

	public KMedoids(IDistance<T> d) {
		this.d = d;
	}

	public void setClusteringListener(ClusteringListener<T> l) {
		this.list = l;
	}

	public List<T> calculMedoiziInitiali(List<T> objects, double distMin) {
		return calculMedoiziInitiali(objects, distMin, 4);
	}

	public List<T> calculMedoiziInitiali(List<T> objects, double distMin,
			int nrMedoids) {
		List<T> medoizi = new ArrayList<T>();
		int k = objects.size();// nr medoizi
		if (nrMedoids > 0) {
			k = nrMedoids;
		}
		objects = new ArrayList<T>(objects);
		// aleg aleator un obiect din multimea de obiecte ca fiind centroidul
		// (obiectul reprezentativ) pentru primul cluster
		T obj = objects.get(0);
		// ca prim obiect aleg obiectul cel mai departat de toate celelalte (cel
		// mai apropiat de el sa fie cel mai departat)
		double max = 0;
		for (int i = 0; i < objects.size(); i++) {
			double sum = 0;
			for (int j = 0; j < objects.size(); j++)
				if (j != i) {
					double v = d.distance(objects.get(i), objects.get(j));
					sum += v;
				}
			if (max < sum) {
				obj = objects.get(i);
				max = sum;
			}
		}

		medoizi.add(obj);
		objects.remove(obj);
		// distata fata de cel mai apropiat medoid (avem unul deci e chiar ala
		List<Double> minsToMedoids = new ArrayList<Double>(objects.size());
		for (int i = 0; i < objects.size(); i++) {
			minsToMedoids.add(d.distance(objects.get(i), obj));
		}

		System.out.println("Alegerea initiala: " + obj + " " + max + " poz:"
				+ objects.indexOf(obj));
		while (medoizi.size() < k) {
			T obrepr = null;
			double distMax = 0;
			for (int i = 0; i < objects.size(); i++) {

				// obiectul i nu deja ales ca centroid
				// obiectul cel mai apropiat sa fie cel mai departat
				// double distCandidat = distantaObiectCluster(objects.get(i),
				// medoizi);
				double distCandidat = minsToMedoids.get(i);
				if ((distCandidat >= distMin) && (distCandidat > distMax)) {
					distMax = distCandidat;
					obrepr = objects.get(i);
					// System.out.println("distC=" + distCandidat);
				}

			}
			if (obrepr != null) {
				medoizi.add(obrepr);
				int poz = objects.indexOf(obrepr);
				objects.remove(poz);
				minsToMedoids.remove(poz);
				// actualizez distanta spre cel mai apropiat medoid
				updateMins(objects, obrepr, minsToMedoids);
				System.out.println("Alegerea medoid" + obrepr + " " + distMax
						+ " medoizi size:" + medoizi.size() + " poz:"
						+ (poz + medoizi.size()));
			} else {
				k--;
				// System.out.print("k=" + k + " distMax=" + distMax);
			}
		}
		return medoizi;
	}

	/**
	 * actualizeaza distanta spre cel mai apropiat medioi
	 *
	 * @param objects
	 * @param newMedoid
	 * @param minsToMedoids
	 */
	private void updateMins(List<T> objects, T newMedoid,
			List<Double> minsToMedoids) {
		for (int i = 0; i < objects.size(); i++) {
			double dMin = minsToMedoids.get(i);
			double dist = d.distance(objects.get(i), newMedoid);
			if (dist < dMin) {
				minsToMedoids.set(i, dist);
			}
		}
	}

	private double distantaObiectCluster(T obj, List<T> medoizi) {
		double min = Double.MAX_VALUE;
		for (int i = 0; i < medoizi.size(); i++) {
			double dist = d.distance(obj, medoizi.get(i));
			if (min > dist)
				min = dist;
		}
		return min;
	}

	public Partition<T> clustering(List<T> objects, List<T> medoizi, int MAX) {
		System.out.println("Numarul de clusteri este:" + medoizi.size());

		Partition<T> c = new Partition<T>(medoizi.size());
		Partition<T> ca = null;

		int nrIteratii = 0;
		boolean gata = false;
		while (!gata && nrIteratii <= MAX) {
			nrIteratii++;

			ca = c.copie();

			c.goleste();

			for (int i = 0; i < objects.size(); i++) {
				double min = 50000;
				// double medieMin = 50000;
				Cluster jmin = c.get(0);
				for (int j = 0; j < medoizi.size(); j++) {
					double distance = d
							.distance(medoizi.get(j), objects.get(i));
					if (distance < min) {
						jmin = c.get(j);
						min = distance;
						// double medie = (ca.getNRClusters() != 0) ?
						// distMedieNou(
						// objects.get(i), ca.get(j))
						// : 5000;
						// medieMin = medie;
					}
					// else if (distance == min) {
					// double medie = (ca.getNRClusters() != 0) ? distMedieNou(
					// objects.get(i), ca.get(j))
					// : 5000;
					// if (medie < medieMin) {
					// jmin = c.get(j);
					// medieMin = medie;
					// }
					// }
				}
				jmin.add(objects.get(i));
			}

			List<T> f1 = new ArrayList<T>(medoizi);
			medoizi.clear();
			boolean schimbat = false;
			// gasesc noii medoizi
			for (int ii = 0; ii < c.getNRClusters(); ii++) {
				Cluster<T> cl = c.get(ii);
				if (cl.eVid()) {
					continue;
				}
				Cluster<T> clv = null;
				if (nrIteratii != 1) {
					clv = ca.get(ii);
				}
				T medV = f1.get(ii); // medoidul vechi
				T hNou = medV;
				if (nrIteratii == 1 || !cl.egal(clv)) {
					schimbat = true;
					for (int h = 0; h < cl.getNRObjs(); h++) {
						if (cl.get(h) != medV) {
							double t = 0;
							for (int j = 0; j < cl.getNRObjs(); j++) {
								t += d.distance(cl.get(j), cl.get(h))
										- d.distance(cl.get(j), medV);
							}
							if (t < 0) {
								hNou = cl.get(h); // medoidul nou
								break;
							}
						}
					}
				}
				medoizi.add(hNou);
			}
			c.eliiminaGoale();

			System.out.println("Numar nou de clusteri " + c.getNRClusters());
			if (!schimbat) {
				gata = true;
			}
			if (list != null) {
				list.intermediatePartition(c);
			}
		}
		System.out.println("Numarul de iteratii:" + nrIteratii);
		return c;
	}

	private double distMedieNou(T obj, Cluster<T> cluster) // distanta
	// medie dintre
	// obiectul i si elem.
	// clusterului cluster
	{
		double suma = 0;
		for (int j = 0; j < cluster.getNRObjs(); j++) {
			suma += d.distance(obj, cluster.get(j));
		}
		return suma / cluster.getNRObjs();
	}
}
