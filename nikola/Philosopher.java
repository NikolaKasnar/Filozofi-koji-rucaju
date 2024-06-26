import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher implements Runnable {
    private int id;
    private Lock[] locks;
    private final long thinkTime = 5000; // 5 seconds
    private final long eatTime = 3000; // 3 seconds

    public Philosopher(int id, Lock[] locks) {
        this.id = id;
        this.locks = locks;
    }

    private void think() {
        System.out.println("Philosopher " + id + " is thinking.");
        try {
            Thread.sleep(thinkTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void eat() {
        System.out.println("Philosopher " + id + " is eating.");
        try {
            Thread.sleep(eatTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        while (true) {
            think();
            // Try to acquire both locks (resources)
            locks[id].lock();
            locks[(id + 1) % locks.length].lock();

            try {
                eat();
            } finally {
                locks[id].unlock();
                locks[(id + 1) % locks.length].unlock();
            }
        }
    }
}
