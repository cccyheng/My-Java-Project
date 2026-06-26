public class CalculatorEngine {
    private Calculator calculator;
    private double num1;
    private Operation currentOp;
    private boolean isNewInput;

    public CalculatorEngine() {
        calculator = new Calculator();
        num1 = 0;
        currentOp = null;
        isNewInput = true;
    }

    public boolean isNewInput() {
        return isNewInput;
    }

    public void setNewInput(boolean isNewInput) {
        this.isNewInput = isNewInput;
    }

    public boolean hasOperator() {
        return currentOp != null;
    }

    //运算符
    public void setOperator(Operation op, double currentValue) {
        num1 = currentValue;
        currentOp = op;
        isNewInput = true;
    }

    //结果（二元运算）
    public CalcResult calculate(double num2) throws ArithmeticException {
        if (currentOp == null) {
            throw new IllegalStateException("没有选择运算符");
        }

        double result = calculator.compute(currentOp, num1, num2);
        String record = currentOp.makeRecord(num1, num2, result);
        this.num1 = result;
        this.currentOp = null;
        this.isNewInput = true;
        return new CalcResult(result, record);
    }

    //一元运算（开方、sin、cos、tan）
    public CalcResult executeUnary(Operation op, double value) throws ArithmeticException {
        double result = op.calculate(value, 0);
        String record = op.makeRecord(value, 0, result);
        this.isNewInput = true;
        return new CalcResult(result, record);
    }

    //c
    public void clear() {
        num1 = 0;
        currentOp = null;
        isNewInput = true;
    }

    public static class CalcResult {
        private final double value;
        private final String record;

        public CalcResult(double value, String record) {
            this.value = value;
            this.record = record;
        }

        public double getValue() {
            return value;
        }

        public String getRecord() {
            return record;
        }
    }
}
