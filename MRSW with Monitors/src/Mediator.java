public class Mediator {
    public static boolean ACT_STUPID = false;

    private AtomicCounter readersCount = new AtomicCounter();
    private Lock writeLock = new Lock("WriterLock");

    // This lock reduces starvation on the writer
    private Lock turnLock = new Lock("TurnLock");

    public void beginRead(String who) {
        if (ACT_STUPID) {
            return;
        }

        turnLock.take(who);

        if (readersCount.increase() == 1) {
            writeLock.take(who);
        }

        turnLock.release(who);
    }

    public void endRead(String who) {
        if (ACT_STUPID) {
            return;
        }

        if (readersCount.decrease() == 0) {
            writeLock.release(who);
        }
    }

    public void beginWrite(String who) {
        if (ACT_STUPID) {
            return;
        }

        turnLock.take(who);
        writeLock.take(who);
        turnLock.release(who);
    }

    public void endWrite(String who) {
        if (ACT_STUPID) {
            return;
        }

        writeLock.release(who);
    }
}
