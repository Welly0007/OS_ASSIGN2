import java.util.concurrent.Semaphore;

public class CustomSemaphore extends Semaphore {

    public CustomSemaphore(int permits) {
        super(permits);
    }

}
