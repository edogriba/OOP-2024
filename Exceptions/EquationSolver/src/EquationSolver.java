
public class EquationSolver {

    private double a;
    private double b;
    private double c;
    public double root1;
    public double root2;

    public void setA(double a) {
        this.a = a;
    }
    public void setB(double b) {
        this.b = b;
    }
    public void setC(double c) {
        this.c = c;
    }

    public EquationSolver() {

    }

    public void solve() throws NegativeDiscriminantException{
        double discriminant = b*b-4*a*c;
        if (discriminant < 0) {
            throw new NegativeDiscriminantException();
        }
        // don't need the else becausean exception interrupts the execution of code just like a return statement
        root1 = (-b+Math.sqrt(discriminant)/(2*a));
        root2 = (-b-Math.sqrt(discriminant)/(2*a));
    }

}
