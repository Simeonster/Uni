import java.util.Arrays;

public class Reader implements Runnable {
    private Mediator mediator;
    private Integer[] data;
    private int id;

    public Reader(Mediator mediator, Integer[] data, int id) {
        this.mediator = mediator;
        this.data = data;
        this.id = id;
    }

    private void readData() {
        System.out.println("Reader " + id + " : " + Arrays.toString(data));
    }

    @Override
    public void run() {
        while (true) {
            mediator.beginRead("Reader " + id);
            readData();
            mediator.endRead("Reader " + id);

            EntryPoint.healthySleep();
        }
    }
}
