import java.util.LinkedList;

public class Car extends Thread {
    String name;
    LinkedList<Car> buffer;
    Semaphore empty;
    Semaphore notEmpty;
    Semaphore mutex;
    int bufferCapacity;
    ServiceStationGUI gui;

    Car(String name, LinkedList<Car> buffer, Semaphore empty, Semaphore notEmpty, Semaphore mutex,
            int bufferCapacity, ServiceStationGUI gui) {
        this.name = name;
        this.buffer = buffer;
        this.empty = empty;
        this.notEmpty = notEmpty;
        this.mutex = mutex;
        this.bufferCapacity = bufferCapacity;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " arrived");
            gui.addLog(name + " arrived at the station");
            // Semaphore replacement
            boolean gotPermit = notEmpty.tryAcquire();
            if (!gotPermit) {
                System.out.println(name + " arrived and waiting");
                gui.addLog(name + " waiting (station at capacity)");
                // Semaphore replacement
                notEmpty.acquire();
            }

            // Semaphore replacement
            mutex.acquire();
            buffer.add(this);
            gui.addLog(name + " entered waiting area");
            gui.updateWaitingCars(buffer);
            // Semaphore replacement
            mutex.release();

            // Semaphore replacement
            empty.release();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}