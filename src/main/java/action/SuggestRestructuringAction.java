package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import component.PackageStructureComponent;

import java.io.IOException;
import java.util.Map;

public class SuggestRestructuringAction extends AnAction {

    public SuggestRestructuringAction() {
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        PackageStructureComponent packageStructureComponent = e.getProject().getComponent(PackageStructureComponent.class);
        String currentFilePath = e.getDataContext().getData(PlatformDataKeys.VIRTUAL_FILE).getPath();
        System.out.println(constructPythonCommandLineArguments(currentFilePath, packageStructureComponent.getPackagePathByClassLabel()));
        try {
            executePythonScript(currentFilePath, packageStructureComponent.getPackagePathByClassLabel());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private String constructPythonCommandLineArguments(String currentFilePath, Map<Integer, String> packagePathByClassLabel) {
        StringBuilder argumentsStringBuilder = new StringBuilder();

        argumentsStringBuilder
                .append(currentFilePath);

        packagePathByClassLabel
                .forEach((classLabel, packagePath) ->
                        argumentsStringBuilder
                                .append(" ")
                                .append(classLabel)
                                .append(packagePath));

        return argumentsStringBuilder.toString();
    }

    private void executePythonScript(String currentFilePath, Map<Integer, String> packagePathByClassLabel) throws IOException, InterruptedException {
//        String cmd = "cmd /c start /B E:\\Facultate\\Thesis\\clustruct\\src\\main\\python\\main.py " + constructPythonCommandLineArguments(currentFilePath, packagePathByClassLabel);
        String cmd = "cmd /c start E:\\Facultate\\Thesis\\clustruct\\src\\main\\python\\main.py " + constructPythonCommandLineArguments(currentFilePath, packagePathByClassLabel);
        Process p = Runtime.getRuntime().exec(cmd);
        p.waitFor();
    }
}
