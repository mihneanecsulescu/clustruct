package clustering.model;

import java.util.List;

public class ParagraphVector extends Base {
    private String label;
    private List<Double> features;

    public ParagraphVector() {}

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Double> getFeatures() {
        return features;
    }

    public void setFeatures(List<Double> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return label;
    }
}
