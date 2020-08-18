package ru.sergg.consistent.build;

import ru.sergg.consistent.build.vms.Component;
import ru.sergg.consistent.build.vms.Issue;
import ru.sergg.consistent.build.vms.Vms;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:sergeygomanyuk@yandex.ru">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class ConsistentBuild {

    private Vms vms;

    public Set<Component> getConsistentBuild(Set<Component> components) {
        Set<Issue> issues = vms.getIssues(components);

        Set<Component> consistentComponents = new HashSet<>();

        consistentComponents.addAll(vms.getComponents(issues, components));

        if(consistentComponents.equals(components)) {
            return consistentComponents;
        } else {
            return getConsistentBuild(consistentComponents);
        }
    }

    public Set<Component> getConsistentBuild(Set<Component> components, Set<Issue> issues) {
        Set<Component> componentsToImplementIssues = vms.getComponents(issues, components);
        Set<Component> fullComponentSet = new HashSet<>(components);
        fullComponentSet.addAll(componentsToImplementIssues);
        return getConsistentBuild(fullComponentSet);
    }
}
