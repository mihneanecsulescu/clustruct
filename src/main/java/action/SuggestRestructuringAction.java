package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import component.PackageStructureComponent;

public class SuggestRestructuringAction extends AnAction {

    public SuggestRestructuringAction() {
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        PackageStructureComponent packageStructureComponent = e.getProject().getComponent(PackageStructureComponent.class);
        packageStructureComponent.getPackagePathByClassLabel();
    }
}
