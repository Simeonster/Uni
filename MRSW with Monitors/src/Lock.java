public class Lock {
    private static boolean DEBUG_PRINT = false;

    private boolean taken;
    private String name;

    public Lock(String name) {
        this.taken = false;
        this.name = name;
    }

    public Lock(boolean taken, String name) {
        this.taken = taken;
        this.name = name;
    }

    private void debugPrint(String message) {
        if (DEBUG_PRINT)
            System.out.println(message);
    }

    public synchronized void take(String who) {
        debugPrint(String.format("%s wants lock %s", who, name));

        while (taken) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        taken = true;
        debugPrint(String.format("%s receives lock %s", who, name));
    }

    public synchronized void release(String who) {
        debugPrint(String.format("%s releases lock %s", who, name));

        if (!taken)
            throw new LockNotTakenException();

        taken = false;
        this.notifyAll();
    }
}
