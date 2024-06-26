class Philosopher implements Runnable {
    int id = 0;
    Resource r = null;
    public Philosopher(int initId, Resource initr) {
        id = initId;
        r = initr;
        new Thread(this).start();
    }
    public void run() {
        while (true) {
            try {
                System.out.println("Filozof " + id + " thinking");
                Thread.sleep(3000);
                System.out.println("Filozof " + id + " hungry");
                r.acquire(id);
                System.out.println("Filozof " + id + " eating");
                Thread.sleep(4000);
                r.release(id);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
