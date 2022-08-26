package events;

// Эвент может находиться в состояниях:
//    - processing
//    - completed
//    - failed
// дополнительно:
//    - committed
//    - cancelled
class Events {

    public boolean isNew(String id) {
        return false;
    }

    // processing
    public void moveToProcessing(String id) {

    }

    public boolean isProcessing(String id) {
        return false;
    }

    // completed
    public boolean isCompleted(String id) {
        return false;
    }

    public void moveToCompleted(String id) {

    }

    // failed
    public boolean isFailed(String id) {
        return false;
    }


    public void moveToFailed(String id) {

    }

    // committed
    public boolean isComitted(String id) {
        return false;
    }

    public void moveToComitted(String id) {

    }

    // cancelled
    public boolean isCancelled(String id) {
        return false;
    }

    public void moveToCancelled(String id) {

    }
}
