/**
 * Copyright (c) Amdocs jNetX.
 * http://www.amdocs.com
 * All rights reserved.
 * This software is the confidential and proprietary information of
 * Amdocs. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with Amdocs.
 * <p>
 * $Id:$
 */
package ru.sergg.consistent.build.vms;

import java.util.Collection;
import java.util.Set;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public interface Vms {
    // return full set of issues implemented by components (all tags included)
    Set<Issue> getIssues(Set<Component> components);

    // return full set of components that implements set of issues
    Set<Component> getComponents(Set<Issue> issues, Set<Component> components);

    Set<Component> getComponentsByBuild(String buildId);
}
