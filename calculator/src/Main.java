import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    private static char getOperator(Scanner input, ArrayList<String> historyList) {
        while (true) {
            System.out.print("请选择运算 (+ - * / % s),按q退出,按h查看历史：");
            char ch = input.next().charAt(0);
            if (ch == 'q') {
                System.out.println("退出计算器");
                System.exit(0);
            }
            if (ch == 'h') {
                System.out.println("\n===== 运算历史 =====");
                if (historyList.isEmpty()) {
                    System.out.println("暂无运算记录\n");
                } else {
                    for (String record : historyList) {
                        System.out.println(record);
                    }
                    System.out.println();
                }
                continue;
            }
            if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == 's') {
                return ch;
            } else {
                System.out.println("输入运算符错误，请重新输入：");
            }
        }
    }
    private static double getNumber(Scanner input) {
        while (true) {
            try {
                return input.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("输入格式错误，请输入有效的数字！");
                input.next();
            }
        }
    }

    public static void main(String[] args) {
        Calculator cal = new Calculator();
        Scanner input = new Scanner(System.in);
        double num1 = 0;
        double num2 = 0;
        char ch = ' ';
        double result = 0;
        ArrayList<String> historyList = new ArrayList<>();

        ch = getOperator(input, historyList);

        System.out.print("Enter First number: ");
        num1 = getNumber(input);
        System.out.print("Enter Second number: ");
        num2 = getNumber(input);

        while (true) {
            Operation operation = null;
            switch (ch) {
                case '+': operation = new Add(); break;
                case '-': operation = new Sub(); break;
                case '*': operation = new Mul(); break;
                case '/': operation = new Div(); break;
                case '%': operation = new Mod(); break;
                case 's': operation = new Sqrt(); break;
                default: throw new IllegalArgumentException("错误运算符" + ch);
            }
            try {
                result = cal.compute(operation, num1, num2);
                System.out.println("结果：" + result);
                String record;
                if (ch == 's') {
                    double sqrtnum = (num2 == 0) ? num1 : num2;
                    record = "sqrt(" + sqrtnum + ") = " + result;
                } else {
                    record = num1 + " " + ch + " " + num2 + " = " + result;
                }
                historyList.add(record);
                num1 = result;
            } catch (ArithmeticException e) {
                System.out.println("除数不能为0，请重新输入除数！");
                System.out.print("Enter Second number: ");
                num2 = getNumber(input);
                continue;
            }

            ch = getOperator(input, historyList);

            System.out.print("Enter Second number: ");
            num2 = getNumber(input);
        }
    }
}