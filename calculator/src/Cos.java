public class Cos extends Operation {
    @Override
    public double calculate(double a, double b) {
        return Math.cos(a);
    }
    @Override
    public String getSymbol() {
        return "cos";
    }
    @Override
    public String makeRecord(double a, double b, double result) {
        return "cos(" + FormatUtil.formatResult(a) + ") = " + FormatUtil.formatResult(result);
    }
}
