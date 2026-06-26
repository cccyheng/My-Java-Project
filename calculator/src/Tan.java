public class Tan extends Operation {
    @Override
    public double calculate(double a, double b) {
        return Math.tan(a);
    }
    @Override
    public String getSymbol() {
        return "tan";
    }
    @Override
    public String makeRecord(double a, double b, double result) {
        return "tan(" + FormatUtil.formatResult(a) + ") = " + FormatUtil.formatResult(result);
    }
}
