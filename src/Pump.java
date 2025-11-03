import java.util.LinkedList;

public class Pump extends Thread {
    int pumpId;
    LinkedList<Car> buffer;
    Semaphore empty;
    Semaphore notEmpty;
    Semaphore mutex;

    Pump(int pumpId, LinkedList<Car> buffer, Semaphore empty, Semaphore notEmpty, Semaphore mutex) {
        this.pumpId = pumpId;
        this.buffer = buffer;
        this.empty = empty;
        this.notEmpty = notEmpty;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Semaphore replacement
                empty.acquire();

                // Semaphore replacement
                mutex.acquire();
                if (buffer.isEmpty()) {
                    // Semaphore replacement
                    mutex.release();
                    continue;
                }
                Car car = buffer.removeFirst();
                System.out.println("Pump " + pumpId + ": " + car.name + " Occupied");
                // Semaphore replacement
                mutex.release();

                // Semaphore replacement
                notEmpty.release();

                System.out.println("Pump " + pumpId + ": " + car.name + " login");
                System.out.println("Pump " + pumpId + ": " + car.name + " begins service at Bay " + pumpId);

                Thread.sleep(2000);

                System.out.println("Pump " + pumpId + ": " + car.name + " finishes service");
                System.out.println("Pump " + pumpId + ": Bay " + pumpId + " is now free");

                App.carProcessed();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
