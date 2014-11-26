package com.amdocs.nfvo.deployment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SERGEYGO on 21.11.2014.
 */
public class DeploymentDescriptor {
    private List<DeploymentEntry> entries = new ArrayList<>();

    public List<DeploymentEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<DeploymentEntry> entries) {
        this.entries = entries;
    }
}
