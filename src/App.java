import java.util.LinkedList;
import java.util.Scanner;

public class App {
    static int totalCars;
    static int carsProcessed = 0;

    public static synchronized void carProcessed() {
        carsProcessed++;
        if (carsProcessed == totalCars) {
            System.out.println("All cars processed; simulation ends");
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

        LinkedList<Car> buffer = new LinkedList<>();
        Semaphore notEmpty = new Semaphore(bufferSize);
        Semaphore empty = new Semaphore(0);
        Semaphore mutex = new Semaphore(1);

        for (int i = 1; i <= numPumps; i++) {
            Pump pump = new Pump(i, buffer, empty, notEmpty, mutex);
            pump.start();
        }

        for (int i = 1; i <= totalCars; i++) {
            Car car = new Car("C" + i, buffer, empty, notEmpty, mutex, bufferSize);
            car.start();
            Thread.sleep(200);
        }

        scanner.close();
    }
}
