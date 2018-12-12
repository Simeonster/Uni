public class Writer implements Runnable {
    private Mediator mediator;
    private Integer[] data;
    private int writeOperations;

    public Writer(Mediator mediator, Integer[] data, int writeOperations) {
        this.mediator = mediator;
        this.data = data;
        this.writeOperations = writeOperations;
    }

    private void addOneToData() {
        // This is used to simulate long write transactions with risk of exposing
        // intermediate state

        // Add one multiple times to all elements
        for (int add=0; add<writeOperations; add++) {
            for (int i = 0; i < data.length; i++) {
                data[i]++;
            }
        }

        // Subtract one multiple times (but one less) from all elements
        for (int add=0; add<writeOperations-1; add++) {
            for (int i = 0; i < data.length; i++) {
                data[i]--;
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            mediator.beginWrite("Writer");
            addOneToData();
            mediator.endWrite("Writer");

            System.out.println("------");
        }
    }
}
