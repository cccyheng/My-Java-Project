public class Sqrt extends Operation {
    @Override
    public double calculate(double a, double b) {
        if (b == 0){
            a = a;
        }
        else {
            a = b;
        }
        if (a < 0) {
            throw new ArithmeticException("负数不能开平方");
        }
        return Math.sqrt(a);
    }
    @Override
    public String getSymbol() {
        return "√";
    }
    @Override
    public String makeRecord(double a, double b, double result) {
        return "√(" + FormatUtil.formatResult(a) + ") = " + FormatUtil.formatResult(result);
    }
}