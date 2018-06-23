package clustering.model;

public class ParagraphVectorModel implements VectorModelProvider<ParagraphVector> {
    @Override
    public double[] getVectorialModel(ParagraphVector a) {
        return a.getFeatures().stream().mapToDouble(i -> i).toArray();
    }

    @Override
    public int getDimension() {
        return 300;
    }
}
