package action;

import clustering.algorithm.hierarchical.linkagemetric.MaxLinkageMetric;
import clustering.distance.ParagraphVectorCosineDistance;
import clustering.model.Cluster;
import clustering.model.ParagraphVector;
import clustering.model.Partition;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.ui.Messages;
import component.PackageStructureComponent;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuggestRestructuringAction extends AnAction {

    public SuggestRestructuringAction() {
    }

    @Override
    public void actionPerformed(AnActionEvent e) {

        PackageStructureComponent packageStructureComponent = e.getProject().getComponent(PackageStructureComponent.class);
        String currentFilePath = e.getDataContext().getData(PlatformDataKeys.VIRTUAL_FILE).getPath();
        Map<Integer, String> packagePathsByClassLabel = packageStructureComponent.getPackagePathsByClassLabel();
        System.out.println(constructPythonCommandLineArguments(currentFilePath, packagePathsByClassLabel));
        try {
            executePythonScript(currentFilePath, packagePathsByClassLabel);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            List<ParagraphVector> paragraphVectors = FileUtils.getParagraphVectorsFromCSVFile("E:\\Facultate\\Thesis\\clustruct\\src\\main\\java\\files\\VectRepr.txt");
            File bestPackage = new File(packagePathsByClassLabel.get(computeBestPackageClassLabel(constructPartition(paragraphVectors))));
            File currentFile = new File(currentFilePath);

            final int result = Messages.showYesNoDialog(
                    e.getProject(),
                    currentFile.getName() + " fits in the " + bestPackage.getName() + " package. Would you like to move it?",
                    "Restructuring Found",
                    "Yes",
                    "No",
                    null);

            if (result == Messages.YES) {
                org.apache.commons.io.FileUtils.moveFileToDirectory(currentFile, bestPackage, false);
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private String constructPythonCommandLineArguments(String currentFilePath, Map<Integer, String> packagePathByClassLabel) {
        StringBuilder argumentsStringBuilder = new StringBuilder();

        argumentsStringBuilder
                .append("\"")
                .append(currentFilePath)
                .append("\"");

        packagePathByClassLabel
                .forEach((classLabel, packagePath) ->
                        argumentsStringBuilder
                                .append(" ")
                                .append("\"")
                                .append(classLabel)
                                .append("|")
                                .append(packagePath)
                                .append("\""));

        return argumentsStringBuilder.toString();
    }

    private void executePythonScript(String currentFilePath, Map<Integer, String> packagePathByClassLabel) throws IOException, InterruptedException {
//        String cmd = "cmd /c start /B E:\\Facultate\\Thesis\\clustruct\\src\\main\\python\\main.py " + constructPythonCommandLineArguments(currentFilePath, packagePathByClassLabel);
        String cmd = "cmd /c start /wait E:\\Facultate\\Thesis\\clustruct\\src\\main\\python\\main.py " + constructPythonCommandLineArguments(currentFilePath, packagePathByClassLabel);
        Process p = Runtime.getRuntime().exec(cmd);
        p.waitFor();
    }

    private Partition<ParagraphVector> constructPartition(List<ParagraphVector> paragraphVectors) {
        Partition<ParagraphVector> partition = new Partition<>();

        Map<Integer, Cluster<ParagraphVector>> clustersByClassLabel = new HashMap<>();
        Cluster<ParagraphVector> toBeMoved = new Cluster<>();

        for (ParagraphVector paragraphVector : paragraphVectors) {
            if (paragraphVector.getClassLabel() == -1) {
                toBeMoved.add(paragraphVector);
            } else {
                Integer classLabel = paragraphVector.getClassLabel();
                if (clustersByClassLabel.get(classLabel) == null) {
                    clustersByClassLabel.put(classLabel, new Cluster<>());
                }
                clustersByClassLabel.get(classLabel).add(paragraphVector);
            }
        }

        partition.add(toBeMoved);
        clustersByClassLabel.forEach((classLabel, cluster) -> {
            cluster.setClassLabel(classLabel);
            partition.add(cluster);
        });

        return partition;
    }

    private Integer computeBestPackageClassLabel(Partition<ParagraphVector> partition) {
        MaxLinkageMetric<ParagraphVector> completeMetric = new MaxLinkageMetric<>(new ParagraphVectorCosineDistance());
        Cluster<ParagraphVector> toBeMoved = partition.get(0);

        Integer bestPackageClassLabel = -1;
        Double minDistane = Double.MAX_VALUE;
        for (int i = 1; i < partition.getNRClusters(); i++) {
            Cluster<ParagraphVector> cluster = partition.get(i);
            double distance = completeMetric.metric(cluster, toBeMoved);
            System.out.println(cluster.getClassLabel() + ": " + distance);

            if (distance < minDistane) {
                minDistane = distance;
                bestPackageClassLabel = cluster.getClassLabel();
            }
        }

        return bestPackageClassLabel;
    }
}
