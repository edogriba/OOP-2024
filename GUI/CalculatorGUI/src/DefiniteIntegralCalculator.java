import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;


public class DefiniteIntegralCalculator extends JFrame {
    private  JComboBox<String> functionComboBox;
    private JLabel intervalLabel;
    private JTextField lowerBoundField;
    private JTextField upperBoundField;
    private JButton calculateButton;
    private JLabel resultLabel;

    private static final int NUM_THREADS = 4; // number of threads


    public DefiniteIntegralCalculator() {
        setTitle("Integral Calculator"); // Extends JFrame method 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Main panel with GridLayout

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 1));
        mainPanel.setBackground(new Color(173, 216, 230));

        // Title LABEL

        JLabel titleLabel = new JLabel("Integral Calculator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment((SwingConstants.CENTER));
        mainPanel.add(titleLabel);

        // Function panel

        JPanel functionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel functionLabel = new JLabel("Function:");
        functionPanel.setBackground(new Color(173, 216, 230));
        functionComboBox = new JComboBox<>();
        functionComboBox.addItem("f(x) = x^2");
        functionComboBox.addItem("f(x) = sin(x)");
        functionComboBox.addItem("f(x) = cos(x)");
        functionPanel.add(functionLabel);
        functionPanel.add(functionComboBox);
        mainPanel.add(functionPanel);

        // Interval Label
        intervalLabel = new JLabel("Interval: ");
        intervalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(intervalLabel);

        // LOwer bound and upper bound panel
        JPanel boundsPanel = new JPanel(new GridLayout(1,2));
        boundsPanel.setBackground(new Color(173, 216, 230));
        lowerBoundField = new JTextField(10);
        upperBoundField = new JTextField(10);
        boundsPanel.add(lowerBoundField);
        boundsPanel.add(upperBoundField);
        mainPanel.add(boundsPanel);

        //Calculate button
        calculateButton = new JButton("CALCULATE");
        calculateButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) { // associate an event to a listened action
                try {
                    calculateDefiniteIntegral();
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainPanel.add(calculateButton);

        //Result label

        resultLabel = new JLabel();
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(resultLabel);

        setContentPane(mainPanel);
        pack(); // calculate the most suitable tìdimension of the window to place all the listed elements
        setLocationRelativeTo(null);

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
        System.out.println(sum);
        return sum;
    }

    private void calculateDefiniteIntegral() throws InterruptedException{
        //Get the selected function

        String selectedFunction = (String) functionComboBox.getSelectedItem();
        // Let's suppose that the user input ios correct (lower bound and upper bound are correct)
        double lowerBound = Double.parseDouble((lowerBoundField.getText()));
        double upperBound = Double.parseDouble((lowerBoundField.getText()));

        double result = computeIntegral(selectedFunction, lowerBound, upperBound);
        System.out.println(result);
        //display result 
        resultLabel.setText(String.format("%.2f", result));
        
    }
    private double computeIntegral(String function, double lowerbound, double upperbound) throws InterruptedException { // this is the method that will call the impolementation with threads to compute the integral
        Function <Double, Double> selectedFunction;
        switch(function) {
            case "f(x) = x^2":
                selectedFunction = x->Math.pow(x, 2);
                break;
            case "f(x) = sin(x)":
                selectedFunction = Math::sin;
                break;
            case "f(x) = cos(x)":
                selectedFunction= Math::cos;
                break;
            default:
                throw new IllegalArgumentException();
        }

        return computeDefiniteIntegral(selectedFunction, lowerbound, upperbound);

    }

    
}
