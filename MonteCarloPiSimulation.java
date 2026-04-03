import java.util.Random;

// Shared data class
class SharedData {
    private int pointsInsideCircle = 0;

    // synchronized method to avoid race condition
    public synchronized void incrementInside(int count) {
        pointsInsideCircle += count;
    }

    public int getPointsInsideCircle() {
        return pointsInsideCircle;
    }
}

// Worker thread
class MonteCarloWorker extends Thread {
    private int numPoints;
    private SharedData shared;

    public MonteCarloWorker(int numPoints, SharedData shared) {
        this.numPoints = numPoints;
        this.shared = shared;
    }

    public void run() {
        Random rand = new Random();
        int localCount = 0;

        for (int i = 0; i < numPoints; i++) {
            double x = rand.nextDouble();
            double y = rand.nextDouble();

            if (x * x + y * y <= 1) {
                localCount++;
            }
        }

        // update shared data once (efficient)
        shared.incrementInside(localCount);
    }
}

public class Main {
    public static void main(String[] args) {
        int totalPoints = 1_000_000;
        int numThreads = 4;

        SharedData shared = new SharedData();
        MonteCarloWorker[] workers = new MonteCarloWorker[numThreads];

        int pointsPerThread = totalPoints / numThreads;

        // Create threads
        for (int i = 0; i < numThreads; i++) {
            workers[i] = new MonteCarloWorker(pointsPerThread, shared);
            workers[i].start();
        }

        // Wait for all threads to finish
        try {
            for (int i = 0; i < numThreads; i++) {
                workers[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int inside = shared.getPointsInsideCircle();
        double piEstimate = 4.0 * inside / totalPoints;

        System.out.println("Total Points: " + totalPoints);
        System.out.println("Points inside circle: " + inside);
        System.out.println("Estimated Pi: " + piEstimate);
    }
}
