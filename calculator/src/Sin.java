public class Sin extends Operation {
    @Override
    public double calculate(double a, double b) {
        return Math.sin(a);
    }
    @Override
    public String getSymbol() {
        return "sin";
    }
    @Override
    public String makeRecord(double a, double b, double result) {
        return "sin(" + FormatUtil.formatResult(a) + ") = " + FormatUtil.formatResult(result);
    }
}
