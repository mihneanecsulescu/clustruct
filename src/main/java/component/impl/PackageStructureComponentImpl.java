package component.impl;

import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.project.Project;
import component.PackageStructureComponent;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PackageStructureComponentImpl extends AbstractProjectComponent implements PackageStructureComponent {

    private Map<Integer, String> packagePathsByClassLabel = new HashMap<>();
    private AtomicInteger classLabel = new AtomicInteger(0);

    protected PackageStructureComponentImpl(Project project) {
        super(project);
    }

    @Override
    public void projectOpened() {
        File currentDir = new File(myProject.getBasePath() + "/src/main"); // current directory
        constructPackagePathByClassLabelMap(currentDir, packagePathsByClassLabel);
    }

    private void constructPackagePathByClassLabelMap(File dir, Map<Integer, String> packagePathByClassLabel) {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory() && Arrays.stream(file.list()).anyMatch(f -> f.contains(".java"))) {
                    packagePathByClassLabel.put(classLabel.incrementAndGet(), file.getCanonicalPath());
                    constructPackagePathByClassLabelMap(file, packagePathByClassLabel);
                } else if (file.isDirectory()) {
                    constructPackagePathByClassLabelMap(file, packagePathByClassLabel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, String> getPackagePathsByClassLabel() {
        return packagePathsByClassLabel;
    }

    public void setPackagePathsByClassLabel(Map<Integer, String> packagePathsByClassLabel) {
        this.packagePathsByClassLabel = packagePathsByClassLabel;
    }
}
