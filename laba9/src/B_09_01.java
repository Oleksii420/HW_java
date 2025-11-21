import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class B_09_01 {

    private static final int T1_READ_DELAY = 100;
    private static final int T2_PROCESS_DELAY = 200;
    private static final int T3_PROCESS_DELAY = 300;
    private static final String POISON_PILL = new String("POISON_PILL");

    public static void main(String[] args) {
        createDummyFile("F.txt");

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        Thread readerThread = new Thread(new FileReaderTask("F.txt", queue, T1_READ_DELAY));
        readerThread.start();

        Thread worker1 = new Thread(new FileProcessorTask(queue, "out1.txt", T2_PROCESS_DELAY));
        Thread worker2 = new Thread(new FileProcessorTask(queue, "out2.txt", T3_PROCESS_DELAY));

        worker1.start();
        worker2.start();
    }

    static class FileReaderTask implements Runnable {
        private final String filename;
        private final BlockingQueue<String> queue;
        private final int delay;

        public FileReaderTask(String filename, BlockingQueue<String> queue, int delay) {
            this.filename = filename;
            this.queue = queue;
            this.delay = delay;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = br.readLine()) != null) {
                    queue.put(line);
                    Thread.sleep(delay);
                }
                queue.put(POISON_PILL);
                queue.put(POISON_PILL);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class FileProcessorTask implements Runnable {
        private final BlockingQueue<String> queue;
        private final String outputFilename;
        private final int processingTime;

        public FileProcessorTask(BlockingQueue<String> queue, String outputFilename, int processingTime) {
            this.queue = queue;
            this.outputFilename = outputFilename;
            this.processingTime = processingTime;
        }

        @Override
        public void run() {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilename))) {
                while (true) {
                    String line = queue.take();

                    if (line == POISON_PILL) {
                        break;
                    }

                    Thread.sleep(processingTime);

                    bw.write(line);
                    bw.newLine();
                    bw.flush();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createDummyFile(String name) {
        try (PrintWriter writer = new PrintWriter(name)) {
            for (int i = 1; i <= 20; i++) {
                writer.println("Data Line " + i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}