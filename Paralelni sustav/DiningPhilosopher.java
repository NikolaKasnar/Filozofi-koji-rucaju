class DiningPhilosopher implements Resource {
    int n = 0;
    BinarySemaphore[] stick = null;
    public DiningPhilosopher(int initN) {
        n = initN;
        stick = new BinarySemaphore[n];
        for (int i = 0; i < n; i++) {
            stick[i] = new BinarySemaphore(true);
        }
    }
    public void acquire(int i) {
        stick[i].P();
        stick[(i + 1) % n].P();
    }
    public void release(int i) {
        stick[i].V();
        stick[(i + 1) % n].V();
    }
    public static void main(String[] args) {
        DiningPhilosopher dp = new DiningPhilosopher(5);
        for (int i = 0; i < 5; i++)
            new Philosopher(i, dp);
    }
}


