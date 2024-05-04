import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class App {

    private static final int NUM_THREADS = 4; // number of threads

    public static void main(String[] args) throws Exception {
        
        double intervalStart  = 0.0;
        double intervalEnd = Math.PI;
        Function <Double, Double> function  = Math::sin;

        double result = computeDefiniteIntegral(function, intervalStart, intervalEnd);

        System.out.println("The definite integral of f(x) over the interval " + "[" + intervalStart + ", " + intervalEnd + "]" + " is: " + result);
    }
    private static double computeIndefiniteIntegral(Function <Double, Double> function, double x) {
        // Compute the indefinite integral F(x) at a given x using numericla approximation
        double epsilon = 0.00001; // tolerance for numerical approximation
        double h = 0.00001; // step size for numerical approximation
        double integral = 0.0;
        double previousValue = 0.0;

        for (double t=0.0; t<x; t+=h) {
            double currentValue = function.apply(t);
            integral += (currentValue + previousValue) * h/2.0;
            previousValue = currentValue;
        }

        return integral;
    }

    private static double computeDefiniteIntegral(Function<Double, Double> function, double intervalStart, double intervalEnd) throws InterruptedException {
        // Split the interval that we have in 4 to assing each subpart to each thread

        double partitionSize  = (intervalEnd-intervalStart)/NUM_THREADS;
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        AtomicReference<Double>[] partialSums = new AtomicReference[NUM_THREADS];

        for (int i=0; i<NUM_THREADS; i++) {
            partialSums[i] = new AtomicReference<>(0.0);
            int partitionNumber =i;
            executor.submit(() -> {
                System.out.println("Submitting computation of integral on partition");
                double start = intervalStart+partitionNumber*partitionSize;
                double end = start+partitionSize;
                double partitionIntegral = computeIndefiniteIntegral(function, end)-computeIndefiniteIntegral(function, start);
                partialSums[partitionNumber].updateAndGet((sum->sum+partitionIntegral));
            });
        }
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        double sum = 0.0;
        for (AtomicReference<Double> partialSum : partialSums) {
            sum += partialSum.get();
        }
        return sum;
    }
}
