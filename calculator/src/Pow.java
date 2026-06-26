public class Pow extends Operation {
    @Override
    public double calculate(double a, double b) {
        return Math.pow(a, b);
    }
    @Override
    public String getSymbol() {
        return "^";
    }
}
