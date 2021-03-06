import java.util.Arrays;

/**
 * This project simulates the Multiple Readers Single Writer scenario with Monitors by
 * creating a single {@link Writer} and multiple {@link Reader} instances.
 * Both operate on an array of shared integers- the writer increments all elements,
 * and the readers print them.
 *
 * If you need more information on how the locks are taken, enable DEBUG_PRINT in {@link Lock}
 */
public class EntryPoint {
    public static void main(String[] args) {
        Mediator mediator = new Mediator();
        Integer[] data = new Integer[30];

        for (int i=0; i<data.length; i++) {
            data[i] = 0;
        }

        Thread writeThread = new Thread(new Writer(mediator, data, 5000000));
        Thread[] readerThreads = new Thread[5];

        for (int i=0; i<readerThreads.length; i++) {
            readerThreads[i] = new Thread(new Reader(mediator, data, i));
        }

        writeThread.start();
        Arrays.stream(readerThreads).forEach(Thread::start);
    }
}
