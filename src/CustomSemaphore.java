public class CustomSemaphore extends Semaphore {

    public CustomSemaphore(int permits) {
        super(permits);
    }

    // // Acquire a permit (blocking). Signature keeps InterruptedException
    // // to match java.util.concurrent.Semaphore API used in the code.
    // public void acquire() throws InterruptedException {
    //     P();
    // }

    // // Try to acquire a permit without blocking. Returns true if successful.
    // public boolean tryAcquire() {
    //     synchronized (this) {
    //         if (value > 0) {
    //             value--;
    //             return true;
    //         }
    //         return false;
    //     }
    // }

    // // Release a permit
    // public void release() {
    //     V();
    // }

}
