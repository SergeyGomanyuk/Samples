package ru.sergg.consistent.build.vms;

import java.util.Collection;
import java.util.Set;

/**
 * @author <a href="mailto:sergeygomanyuk@yandex.ru">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public interface Vms {
    // return full set of issues implemented by components (all tags included)
    Set<Issue> getIssues(Set<Component> components);

    // return full set of components that implements set of issues
    Set<Component> getComponents(Set<Issue> issues, Set<Component> components);

    Set<Component> getComponentsByBuild(String buildId);
}
