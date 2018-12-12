public class Writer implements Runnable {
    private Mediator mediator;
    private Integer[] data;

    public Writer(Mediator mediator, Integer[] data) {
        this.mediator = mediator;
        this.data = data;
    }

    private void addOneToData() {
        // This is used to simulate long write transactions with risk of exposing
        // intermediate state
        int toAdd = 1000000;

        // Add one multiple times to all elements
        for (int add=0; add<toAdd; add++) {
            for (int i = 0; i < data.length; i++) {
                data[i]++;
            }
        }

        // Subtract one multiple times (but one less) from all elements
        for (int add=0; add<toAdd-1; add++) {
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
            EntryPoint.healthySleep();
        }
    }
}
