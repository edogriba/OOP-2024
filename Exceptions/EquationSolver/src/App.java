public class App {
    public static void main(String[] args) throws Exception {
        EquationSolver eq = new EquationSolver();
        eq.setA(1);
        eq.setB(-2);
        eq.setC(2);
        try {
            eq.solve();
            System.out.println("Roots of the equation: "+ eq.root1 + " " + eq.root2);
        }
        catch (NegativeDiscriminantException e) {
            System.out.println(e.getMessage());
        }
    }
}
