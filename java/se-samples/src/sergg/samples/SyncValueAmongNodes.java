package sergg.samples;

import java.util.List;

/*
N - nodes (separate JVMs), each running SyncValueAmongNodes in multithreading env

Value set via setSharedValue at one (or several) nodes, shall be accessible at other nodes

Nodes should sync once per second (we cannot access DSM directly at each get operation due to performance requirements)

If value changed at several nodes, node with greater node id has higher priority

Time is not synced among nodes.
 */
public class SyncValueAmongNodes implements Runnable {

    private final DSM dsm;
    private final String nodeId;
    private final List<String> allNodeIds;

    private String val;

    public SyncValueAmongNodes(DSM dsm, String nodeId, List<String> allNodeIds) {
        this.dsm = dsm;
        this.nodeId = nodeId;
        this.allNodeIds = allNodeIds;
    }

    @Override
    public void run() {
        // todo: implement
    }

    public void setSharedValue(String shared) {
        // todo: implement
    }

    public String getSharedValue() {
        // todo: implement
        return null;
    }

    /* Threadsafe Distributed Shared Memory */
    public interface DSM {
        void set(String key, String value);
        String get(String key);
    }
}
