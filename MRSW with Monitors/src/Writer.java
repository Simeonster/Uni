public class Writer implements Runnable {
    private Mediator mediator;
    private Integer[] data;

    public Writer(Mediator mediator, Integer[] data) {
        this.mediator = mediator;
        this.data = data;
    }

    private void addOneToData() {
        // This is used to simulate long write transactions
        int toAdd = 100000000;

        for (int add=0; add<toAdd; add++) {
            for (int i = 0; i < data.length; i++) {
                data[i]++;
            }
        }

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
