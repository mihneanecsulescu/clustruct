package clustering.distance;

/**
 * @author I. Coman
 */
public class CosineDistance implements IDistance<double[]> {

    @Override
    public double distance(double[] a, double[] b) {
        double aBProduct = 0;
        double aSquared = 0;
        double bSquared = 0;

        for (int i = 0; i < a.length; i++) {
            aBProduct += a[i] * b[i];
        }

        for (double elem : a) {
            aSquared += elem * elem;
        }
        aSquared = Math.sqrt(aSquared);

        for (double elem : b) {
            bSquared += elem * elem;
        }
        bSquared = Math.sqrt(bSquared);

        return 10 / (Math.abs(aBProduct) / (aSquared * bSquared));
//        double distance = 10 / (aBProduct / (aSquared * bSquared));
//        return distance > 0 ? distance : 0;
    }

    @Override
    public double getIninity() {
        return Double.MAX_VALUE;
    }
}
