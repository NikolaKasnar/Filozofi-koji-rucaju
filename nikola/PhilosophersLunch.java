import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PhilosophersLunch {
    public static void main(String[] args) {
        int numberOfPhilosophers = 5;
        Lock[] locks = new ReentrantLock[numberOfPhilosophers];
        for (int i = 0; i < numberOfPhilosophers; i++) {
            locks[i] = new ReentrantLock();
        }

        Thread[] philosophers = new Thread[numberOfPhilosophers];
        for (int i = 0; i < numberOfPhilosophers; i++) {
            philosophers[i] = new Thread(new Philosopher(i, locks));
            philosophers[i].start();
        }

        // Let philosophers run for a certain period, then terminate
        try {
            Thread.sleep(20000); // Run for 10 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Interrupt all philosopher threads
        for (Thread philosopher : philosophers) {
            philosopher.interrupt();
        }

        // Wait for all philosopher threads to finish
        for (Thread philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}