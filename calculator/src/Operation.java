public abstract class Operation {
    public abstract double calculate(double a, double b);
    public abstract String getSymbol();

    //生成计算记录（二元运算）
    public String makeRecord(double a, double b, double result) {
        return FormatUtil.formatResult(a) + " " + getSymbol() + " "
                + FormatUtil.formatResult(b) + " = " + FormatUtil.formatResult(result);
    }
}