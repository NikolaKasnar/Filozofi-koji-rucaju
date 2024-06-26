public class BinarySemaphore {
    boolean value;
    BinarySemaphore(boolean initValue) {
        value = initValue;
    }
    public synchronized void P() {
        while (value == false)
            Util.myWait(this);// Ceka u redu
        value = false;
    }
    public synchronized void V() {
        value = true;
        notify();
    }
}


