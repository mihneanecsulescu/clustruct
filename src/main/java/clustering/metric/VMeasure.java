package clustering.metric;

import clustering.model.Base;
import clustering.model.Cluster;
import clustering.model.Partition;

public class VMeasure<T extends Base> {

    private double[][] n; //n[c][k] - number of samples from class c assigned to cluster k
    private double[] nC; //number of samples belonging to class c
    private double[] nK; //number of samples belonging to class k
    private Partition<T> partition;
    private double totalN;

    public VMeasure(Partition<T> partition) {
        this.nC = new double[]{0, 7, 11, 2, 2};
        this.nK = new double[partition.getNRClusters() + 1];
        this.partition = partition;
        this.totalN = partition.getNRAllObjects();
        this.n = new double[nC.length][partition.getNRClusters() + 1];
        for (int i = 0; i < partition.getNRClusters(); i++) {
            Cluster<T> cluster = partition.get(i);
            for (T t : cluster.getAll()) {
                n[t.getClassLabel()][i + 1]++;
            }
            nK[i + 1] = cluster.getNRObjs();
        }
    }

    public double compute() {

        return 2 / ((1 / computeHomogenity()) + (1 / computeCompleteness()));
    }

    private double computeHomogenity() {
        double conditionalEntropyOfClassesGivenCluster = 0;
        double entropyOfClasses = 0;

        for (int c = 1; c < nC.length; c++) {
            for (int k = 1; k < nK.length; k++) {
                if (n[c][k] != 0) {
                    conditionalEntropyOfClassesGivenCluster -= (n[c][k] / totalN) * Math.log(n[c][k] / nK[k]);
                }
            }
        }

        for (int c = 1; c < nC.length; c++) {
            entropyOfClasses -= (nC[c] / totalN) * Math.log(nC[c] / totalN);
        }

        double h = 1 - (conditionalEntropyOfClassesGivenCluster / entropyOfClasses);
        System.out.println("homogenity: " + h);
        return h;
    }

    private double computeCompleteness() {
        double conditionalEntropyOfClustersGivenClass = 0;
        double entropyOfClusters = 0;

        for (int k = 1; k < nK.length; k++) {
            for (int c = 1; c < nC.length; c++) {
                if (n[c][k] != 0) {
                    conditionalEntropyOfClustersGivenClass -= (n[c][k] / totalN) * Math.log(n[c][k] / nC[c]);
                }
            }
        }

        for (int k = 1; k < nK.length; k++) {
            entropyOfClusters -= (nK[k] / totalN) * Math.log(nK[k] / totalN);
        }

        double c = 1 - (conditionalEntropyOfClustersGivenClass / entropyOfClusters);
        System.out.println("completeness: " + c);
        return c;
    }
}
