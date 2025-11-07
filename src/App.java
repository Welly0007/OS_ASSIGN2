import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.SwingUtilities;
public class App {
    static int totalCars;
    static int carsProcessed = 0;
    static ServiceStationGUI gui;

    public static synchronized void carProcessed() {
        carsProcessed++;
        gui.addLog("Progress: " + carsProcessed + "/" + totalCars + " cars processed");
        if (carsProcessed == totalCars) {
            gui.addLog("=== All cars processed; simulation ends ===");
            System.out.println("All cars processed; simulation ends");

            //todo : update this field in the discussion 
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.exit(0);
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Waiting area capacity: ");
        int bufferSize = scanner.nextInt();

        System.out.print("Number of service bays (pumps): ");
        int numPumps = scanner.nextInt();

        System.out.print("Cars arriving (count): ");
        totalCars = scanner.nextInt();

        // Create and show GUI
        SwingUtilities.invokeLater(() -> {
            gui = new ServiceStationGUI(numPumps, bufferSize);
            gui.setVisible(true);
            gui.addLog("=== Gas Station Simulation Started ===");
            gui.addLog("Waiting area capacity: " + bufferSize);
            gui.addLog("Number of service bays: " + numPumps);
            gui.addLog("Total cars arriving: " + totalCars);
            gui.addLog("=====================================");
        });
        // Wait for GUI to initialize
        Thread.sleep(500);

        LinkedList<Car> buffer = new LinkedList<>();
        Semaphore notEmpty = new Semaphore(bufferSize);
        Semaphore empty = new Semaphore(0);
        Semaphore mutex = new Semaphore(1);

        for (int i = 1; i <= numPumps; i++) {
            Pump pump = new Pump(i, buffer, empty, notEmpty, mutex, gui);
            pump.start();
        }

        for (int i = 1; i <= totalCars; i++) {
            Car car = new Car("C" + i, buffer, empty, notEmpty, mutex, bufferSize, gui);
            car.start();
            Thread.sleep(200);
        }

        scanner.close();
    }
}
