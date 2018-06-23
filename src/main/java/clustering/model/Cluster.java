package clustering.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Cluster<T> {
	private List<T> objs;
	/**
	 * putem atribui label pentru un cluster
	 * poate fi folosit si in alte scopuri (cache etc)
	 */
	private Object label;
	private Integer classLabel;

	public Cluster(Cluster<T> c1, Cluster<T> c2) {
		objs = new ArrayList<T>();
		objs.addAll(c1.objs);
		objs.addAll(c2.objs);
	}

	public Cluster() {
		objs = new ArrayList<T>();
	}

	public void add(T obj) {
		objs.add(obj);
	}

	public int getNRObjs() {
		return objs.size();
	}

	public T get(int poz) {
		return objs.get(poz);
	}

	public List<T> getAll() {
		List<T> rez = new ArrayList<T>(objs);
		return rez;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < objs.size(); i++) {
			sb.append(objs.get(i));
			sb.append("\n");
		}
		return sb.toString();
	}

	public Cluster<T> copie() {
		Cluster<T> rez = new Cluster<T>();
		rez.objs.addAll(objs);
		return rez;
	}

	public void goleste() {
		objs.clear();
	}

	public Iterator<T> iterator() {
		return objs.iterator();
	}

	public boolean contains(T obj) {
		return objs.contains(obj);
	}

	public boolean eVid() {
		return objs.size() == 0;
	}

	public boolean egal(Cluster<T> clv) {
		if (getNRObjs() != clv.getNRObjs()) {
			return false;
		}
		for (int i = 0; i < objs.size(); i++) {
			if (!clv.contains(objs.get(i))) {
				return false;
			}
		}
		return true;
	}

	public List<T> getAllObjs() {
		return Collections.unmodifiableList(objs);
	}

	public int hashCode() {
		if (eVid()) {
			return 1;
		}
		return objs.get(0).hashCode();
	}

	public void setLabel(Object label) {
		this.label = label;
	}

	public Object getLabel() {
		return label;
	}

	public Integer getClassLabel() {
		return classLabel;
	}

	public void setClassLabel(Integer classLabel) {
		this.classLabel = classLabel;
	}
}
