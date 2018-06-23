package clustering.model;

/*
 * Ofera modelul vectorial pentru un obiect T
 * Ajuta in implementarea de diferite Metrici legate de clustering
 */
public interface VectorModelProvider<T> {
	public double[] getVectorialModel(T a);

	public int getDimension();
}
