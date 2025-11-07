import java.util.LinkedList;

public class Pump extends Thread {
    int pumpId;
    LinkedList<Car> buffer;
    Semaphore empty;
    Semaphore notEmpty;
    Semaphore mutex;
    ServiceStationGUI gui;

    Pump(int pumpId, LinkedList<Car> buffer, Semaphore empty, Semaphore notEmpty, Semaphore mutex, ServiceStationGUI gui) {
        this.pumpId = pumpId;
        this.buffer = buffer;
        this.empty = empty;
        this.notEmpty = notEmpty;
        this.mutex = mutex;
        this.gui = gui;
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
                gui.addLog("Bay " + pumpId + ": " + car.name + " occupied");
                gui.setPumpStatus(pumpId, true, car.name);
                gui.updateWaitingCars(buffer);
                // Semaphore replacement
                mutex.release();

                // Semaphore replacement
                notEmpty.release();

                System.out.println("Pump " + pumpId + ": " + car.name + " login");
                gui.addLog("Bay " + pumpId + ": " + car.name + " logged in");
                
                System.out.println("Pump " + pumpId + ": " + car.name + " begins service at Bay " + pumpId);
                gui.addLog("Bay " + pumpId + ": " + car.name + " begins service");

                Thread.sleep(2000);

                System.out.println("Pump " + pumpId + ": " + car.name + " finishes service");
                gui.addLog("Bay " + pumpId + ": " + car.name + " finishes service");
                
                System.out.println("Pump " + pumpId + ": Bay " + pumpId + " is now free");
                gui.addLog("Bay " + pumpId + ": now free");
                gui.setPumpStatus(pumpId, false, "");

                App.carProcessed();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
