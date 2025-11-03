public class Semaphore {
    protected int value = 0;
    
    protected Semaphore(){
        value = 0;
    }

    protected Semaphore(int initial){
        value = initial;
    }

    public synchronized void acquire() throws InterruptedException{
        value--;
        if (value < 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean tryAcquire() {
        synchronized (this) {
            if (value > 0) {
                value--;
                return true;
            }
            return false;
        }
    }

    public synchronized void release() {
        value++;
        if (value <= 0) {
            notify();
        }
    }
}
