package clustering.distance;

public class EuclideanDistance implements IDistance<double[]> {
	
	public static double w[]={1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	

	//pentru radius
	//public static double w[]={0.11, 0.11, 0.10, 0.11, 0.09, 0.06, 0.11, 0.11, 0.11, 0.09};
	
	//pentru ulna
	//public static double w[]={0.11, 0.11, 0.10, 0.11, 0.09, 0.06, 0.11, 0.11, 0.11, 0.09};
	
	
	
	@Override
	public double distance(double[] a, double[] b) {
		double dist = 0;
		for (int i = 0; i < a.length; i++) {
				double aux = a[i] - b[i];
				dist += aux * aux;
		}
		return Math.sqrt(dist);
	}

	@Override
	public double getIninity() {
		return Double.MAX_VALUE;
	}

}
