public class AtomicCounter {
    private int value;

    public synchronized int increase() {
        return ++value;
    }

    public synchronized int decrease() {
        return --value;
    }
}
