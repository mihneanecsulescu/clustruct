package clustering.distance;


public interface IDistance<T> {
	public double distance(T a, T b);

	public double getIninity();
}
