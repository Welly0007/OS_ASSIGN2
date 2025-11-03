import java.util.LinkedList;

public class Car extends Thread {
    String name;
    LinkedList<Car> buffer;
    CustomSemaphore empty;
    CustomSemaphore notEmpty;
    CustomSemaphore mutex;
    int bufferCapacity;

    Car(String name, LinkedList<Car> buffer, CustomSemaphore empty, CustomSemaphore notEmpty, CustomSemaphore mutex,
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

            // CustomSemaphore replacement
            boolean gotPermit = notEmpty.tryAcquire();

            if (!gotPermit) {
                System.out.println(name + " arrived and waiting");
                // CustomSemaphore replacement
                notEmpty.acquire();
            }

            // CustomSemaphore replacement
            mutex.acquire();
            buffer.add(this);
            // CustomSemaphore replacement
            mutex.release();

            // CustomSemaphore replacement
            empty.release();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}