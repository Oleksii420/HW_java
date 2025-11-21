import java.util.Random;
import java.util.concurrent.Semaphore;

public class B_09_09 {

    private static final int N_CONVEYORS = 3;
    private static final int K_THRESHOLD = 2;

    private static final int T1_ARRIVAL_MIN = 100;
    private static final int T2_ARRIVAL_MAX = 500;

    private static final int T3_PROCESS_MIN = 1000;
    private static final int T4_PROCESS_MAX = 2000;

    private static final int SIMULATION_DURATION_MS = 10000;

    public static void main(String[] args) {
        Semaphore conveyors = new Semaphore(N_CONVEYORS, true);
        QueueMonitor monitor = new QueueMonitor(K_THRESHOLD);
        System.out.printf("Конвеєрів: %d, Поріг черги K: %d%n", N_CONVEYORS, K_THRESHOLD);

        Thread generator = new Thread(() -> {
            Random random = new Random();
            int partId = 1;
            long endTime = System.currentTimeMillis() + SIMULATION_DURATION_MS;

            while (System.currentTimeMillis() < endTime) {
                PartThread part = new PartThread(partId++, conveyors, monitor);
                part.start();

                try {
                    int sleepTime = T1_ARRIVAL_MIN + random.nextInt(T2_ARRIVAL_MAX - T1_ARRIVAL_MIN + 1);
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        generator.start();

        try {
            generator.join();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Загальний час, коли в черзі було не менше %d деталей: %d мс%n",
                K_THRESHOLD, monitor.getTotalTimeAboveThreshold());

        System.exit(0);
    }

    static class PartThread extends Thread {
        private final int id;
        private final Semaphore conveyors;
        private final QueueMonitor monitor;
        private final Random random = new Random();

        public PartThread(int id, Semaphore conveyors, QueueMonitor monitor) {
            this.id = id;
            this.conveyors = conveyors;
            this.monitor = monitor;
        }

        @Override
        public void run() {
            try {
                monitor.registerArrival();
                System.out.println("Деталь " + id + " чекає на конвеєр.");

                conveyors.acquire();

                monitor.registerDepartureFromQueue();
                System.out.println("Деталь " + id + " обробляється.");

                int processTime = T3_PROCESS_MIN + random.nextInt(T4_PROCESS_MAX - T3_PROCESS_MIN + 1);
                Thread.sleep(processTime);

                System.out.println("Деталь " + id + " готова.");
                conveyors.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class QueueMonitor {
        private final int kThreshold;
        private int currentQueueSize = 0;
        private long totalTimeAboveK = 0;
        private long lastUpdateTime;

        public QueueMonitor(int k) {
            this.kThreshold = k;
            this.lastUpdateTime = System.currentTimeMillis();
        }

        public synchronized void registerArrival() {
            updateTimeStats();
            currentQueueSize++;
        }

        public synchronized void registerDepartureFromQueue() {
            updateTimeStats();
            currentQueueSize--;
        }

        private void updateTimeStats() {
            long currentTime = System.currentTimeMillis();
            if (currentQueueSize >= kThreshold) {
                totalTimeAboveK += (currentTime - lastUpdateTime);
            }
            lastUpdateTime = currentTime;
        }

        public synchronized long getTotalTimeAboveThreshold() {
            updateTimeStats();
            return totalTimeAboveK;
        }
    }
}