package clustering.model;

import java.util.ArrayList;
import java.util.List;

public class Partition<T> {
	private List<Cluster<T>> clusters;
	private int nrMinObj = 0;

	public Partition() {
		clusters = new ArrayList<Cluster<T>>();
	}

	public Partition(int nrClusters) {
		clusters = new ArrayList<Cluster<T>>(nrClusters);
		for (int i = 0; i < nrClusters; i++) {
			clusters.add(new Cluster<T>());
		}
	}

	private void computeMin() {
		nrMinObj = 0;
		for (int i = 0; i < clusters.size() && nrMinObj > 1; i++) {
			if (nrMinObj > clusters.get(i).getNRObjs()) {
				nrMinObj = clusters.get(i).getNRObjs();
			}
		}
	}

	public int getMinNrObjInCluster() {
		return nrMinObj;
	}

	public void add(Cluster<T> c) {
		clusters.add(c);
		if (nrMinObj > c.getNRObjs()) {
			nrMinObj = c.getNRObjs();
		}
	}

	public Cluster<T> addSingletonCluster(T o) {
		Cluster<T> cl = new Cluster<T>();
		cl.add(o);
		add(cl);
		return cl;
	}

	public int getNRClusters() {
		return clusters.size();
	}

	public Cluster<T> get(int poz) {
		return clusters.get(poz);
	}

	public void delete(Cluster<T> c) {
		clusters.remove(c);
		if (nrMinObj == c.getNRObjs()) {
			computeMin();// trebuie sa recalculez minimul.. poate s-a
			// eliminat
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Nr clusteri:").append(clusters.size());
		for (int i = 0; i < clusters.size(); i++) {
			sb.append("\n");
			sb.append("Clusterul ").append(i);
			sb.append("\n");
			sb.append(clusters.get(i));
		}
		return sb.toString();
	}

	public Partition<T> copie() {
		Partition<T> rez = new Partition<T>();
		for (int i = 0; i < clusters.size(); i++) {
			rez.add(clusters.get(i).copie());
		}
		return rez;
	}

	public void goleste() {
		for (int i = 0; i < clusters.size(); i++) {
			clusters.get(i).goleste();
		}
	}

	public void eliiminaGoale() {
		List<Cluster<T>> elim = new ArrayList<Cluster<T>>(1);
		for (int i = 0; i < clusters.size(); i++) {
			if (clusters.get(i).getNRObjs() == 0) {
				System.out.println(" Cluster vid , de eliminat");
				elim.add(clusters.get(i));
			}
		}
		clusters.removeAll(elim);
	}

	public int getClusterIndexFor(T obj) {
		for (int i = 0; i < clusters.size(); i++) {
			if (clusters.get(i).contains(obj)) {
				return i;
			}
		}
		return -1;
	}

	public Cluster<T> getClusterContains(T obj) {
		for (int i = 0; i < clusters.size(); i++) {
			if (clusters.get(i).contains(obj)) {
				return clusters.get(i);
			}
		}
		return null;
	}

	public static <T> Partition<T> createEmpty(int nrEmptyClusters) {
		Partition<T> rez = new Partition<T>();
		for (int i = 0; i < nrEmptyClusters; i++) {
			rez.add(new Cluster<T>());
		}
		return rez;
	}

	public void removeEmptyClusters() {

	}

	public double getNRAllObjects() {
		int rez = 0;
		for (int i = 0; i < clusters.size(); i++) {
			rez += clusters.get(i).getNRObjs();
		}
		return rez;
	}

	public Cluster<T> getSmalestCluster() {
		if (clusters.size() < 0) {
			return null;
		}
		Cluster<T> rez = clusters.get(0);
		for (int i = 1; i < clusters.size(); i++) {
			if (clusters.get(i).getNRObjs() < rez.getNRObjs()) {
				rez = clusters.get(i);
			}
		}
		return rez;
	}

	public List<Cluster<T>> getClusters() {
		return clusters;
	}
}
