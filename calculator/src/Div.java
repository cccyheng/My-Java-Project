public class Div extends Operation{
    @Override
    public double calculate(double a, double b) {
        if(b == 0){
            throw new ArithmeticException("除数不能为0");
        }
        return a / b;
    }
    @Override
    public String getSymbol() {
        return "/";
    }
}