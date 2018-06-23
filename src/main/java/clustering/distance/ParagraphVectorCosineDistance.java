package clustering.distance;

import clustering.model.ParagraphVector;

public class ParagraphVectorCosineDistance implements IDistance<ParagraphVector> {

    @Override
    public double distance(ParagraphVector a, ParagraphVector b) {
        double aBProduct = 0;
        double aSquared = 0;
        double bSquared = 0;

        for (int i = 0; i < a.getFeatures().size(); i++) {
            aBProduct += a.getFeatures().get(i) * b.getFeatures().get(i);
        }

        for (Double elem : a.getFeatures()) {
            aSquared += elem * elem;
        }
        aSquared = Math.sqrt(aSquared);

        for (Double elem : b.getFeatures()) {
            bSquared += elem * elem;
        }
        bSquared = Math.sqrt(bSquared);

        return 10 / (Math.abs(aBProduct) / (aSquared * bSquared));
    }

    @Override
    public double getIninity() {
        return Double.MAX_VALUE;
    }
}
