public class Calculator{
    private double result;
    public Calculator(){
        this.result=0.0;
    }
    public double compute(Operation operation, double a, double b) {
        this.result = operation.calculate(a, b);
        return this.result;
    }
    public double getResult() { return result; }
    public void setResult(double result) { this.result = result; }
}
