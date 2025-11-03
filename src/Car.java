import java.util.LinkedList;

public class Car extends Thread {
    String name;
    LinkedList<Car> buffer;
    Semaphore empty;
    Semaphore notEmpty;
    Semaphore mutex;
    int bufferCapacity;

    Car(String name, LinkedList<Car> buffer, Semaphore empty, Semaphore notEmpty, Semaphore mutex,
            int bufferCapacity) {
        this.name = name;
        this.buffer = buffer;
        this.empty = empty;
        this.notEmpty = notEmpty;
        this.mutex = mutex;
        this.bufferCapacity = bufferCapacity;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " arrived");

            // Semaphore replacement
            boolean gotPermit = notEmpty.tryAcquire();

            if (!gotPermit) {
                System.out.println(name + " arrived and waiting");
                // Semaphore replacement
                notEmpty.acquire();
            }

            // Semaphore replacement
            mutex.acquire();
            buffer.add(this);
            // Semaphore replacement
            mutex.release();

            // Semaphore replacement
            empty.release();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}