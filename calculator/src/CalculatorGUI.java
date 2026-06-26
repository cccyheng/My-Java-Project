import javax.swing.*;
import java.awt.*;
import java.io.*;

public class CalculatorGUI extends JFrame {

    // 组件
    private JTextField displayField;
    private JButton[] numberButtons;
    private JButton addBtn, subBtn, mulBtn, divBtn, modBtn, sqrtBtn;
    private JButton equalsBtn, clearBtn, backBtn, dotBtn;
    private JButton showhistoryBtn;
    private JButton downloadhistoryBtn;
    private JButton sinBtn, cosBtn, tanBtn, powBtn;
    private JButton openParenBtn, closeParenBtn;
    // 逻辑
    private CalculatorEngine engine;
    private HistoryManager historyManager;

    public CalculatorGUI() {
        engine = new CalculatorEngine();
        historyManager = new HistoryManager();

        // 主窗口
        setTitle("Java GUI 计算器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null); // 居中显示

        // 间距
        setLayout(new BorderLayout(10, 10));

        // 显示面板
        JPanel displayPanel = new JPanel(new BorderLayout());
        displayField = new JTextField();
        displayField.setFont(new Font("微软雅黑", Font.BOLD, 36));
        displayField.setPreferredSize(new Dimension(400, 100));
        displayField.setHorizontalAlignment(JTextField.RIGHT);//向右对齐
        displayField.setEditable(false);//点击该区域无效
        displayField.setText("0");//初始为0
        displayPanel.add(displayField, BorderLayout.CENTER);//显示面板会充满整个显示区域

        // 按钮大小
        JPanel buttonPanel = new JPanel(new GridLayout(6, 5, 5, 5));

        // 初始化按钮组件
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("微软雅黑", Font.BOLD, 18));
        }

        // 运算符按钮
        addBtn = new JButton("+");
        subBtn = new JButton("-");
        mulBtn = new JButton("×");
        divBtn = new JButton("÷");
        modBtn = new JButton("%");
        sqrtBtn = new JButton("√");
        equalsBtn = new JButton("=");
        clearBtn = new JButton("C");
        backBtn = new JButton("del");
        dotBtn = new JButton(".");
        showhistoryBtn = new JButton("显示历史");
        downloadhistoryBtn = new JButton("保存/加载");
        sinBtn = new JButton("sin");
        cosBtn = new JButton("cos");
        tanBtn = new JButton("tan");
        powBtn = new JButton("x^y");
        openParenBtn = new JButton("(");
        closeParenBtn = new JButton(")");

        // 统一设置字体
        JButton[] allButtons = {addBtn, subBtn, mulBtn, divBtn, modBtn, sqrtBtn,
                                equalsBtn, clearBtn, backBtn, dotBtn, showhistoryBtn,
                                downloadhistoryBtn, sinBtn, cosBtn, tanBtn, powBtn,
                                openParenBtn, closeParenBtn};
        for (JButton btn : allButtons) {
            btn.setFont(new Font("微软雅黑", Font.BOLD, 18));
        }

        // 科学函数行: sin cos tan ( )
        buttonPanel.add(sinBtn);
        buttonPanel.add(cosBtn);
        buttonPanel.add(tanBtn);
        buttonPanel.add(openParenBtn);
        buttonPanel.add(closeParenBtn);
        // 第1行: 7 8 9 + x^y
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(addBtn);
        buttonPanel.add(powBtn);
        // 第2行: 4 5 6 -  (保留位)
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subBtn);
        buttonPanel.add(new JPanel());
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(mulBtn);
        buttonPanel.add(new JPanel());
        // 第4行: 0 . C ÷  (保留位)
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(dotBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(divBtn);
        buttonPanel.add(new JPanel());
        // 第5行: √ % del =  (保留位)
        buttonPanel.add(sqrtBtn);
        buttonPanel.add(modBtn);
        buttonPanel.add(backBtn);
        buttonPanel.add(equalsBtn);
        buttonPanel.add(new JPanel());

        // 南区：历史按钮
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        bottomPanel.add(showhistoryBtn);
        bottomPanel.add(downloadhistoryBtn);

        //把面板装进主窗口
        add(displayPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // 数字按钮事件
        for (int i = 0; i < 10; i++) {
            final int digit = i;
            numberButtons[i].addActionListener(e -> appendDigit(digit));
        }

        // 运算符事件
        addBtn.addActionListener(e -> setOperator(new Add()));
        subBtn.addActionListener(e -> setOperator(new Sub()));
        mulBtn.addActionListener(e -> setOperator(new Mul()));
        divBtn.addActionListener(e -> setOperator(new Div()));
        modBtn.addActionListener(e -> setOperator(new Mod()));
        sqrtBtn.addActionListener(e -> doUnary(new Sqrt()));
        sinBtn.addActionListener(e -> doUnary(new Sin()));
        cosBtn.addActionListener(e -> doUnary(new Cos()));
        tanBtn.addActionListener(e -> doUnary(new Tan()));
        powBtn.addActionListener(e -> setOperator(new Pow()));

        // 其他按钮事件
        equalsBtn.addActionListener(e -> calculateResult());
        clearBtn.addActionListener(e -> clearAll());
        backBtn.addActionListener(e -> backspace());
        dotBtn.addActionListener(e -> appendDot());
        openParenBtn.addActionListener(e -> appendChar("("));
        closeParenBtn.addActionListener(e -> appendChar(")"));
        showhistoryBtn.addActionListener(e -> showHistory());
        downloadhistoryBtn.addActionListener(e -> downloadHistory());

        setVisible(true);
    }

    //数字
    private void appendDigit(int digit) {
        if (engine.isNewInput()) {
            displayField.setText(String.valueOf(digit));
            engine.setNewInput(false);
        } else {
            displayField.setText(displayField.getText() + digit);
        }
    }

    //小数点
    private void appendDot() {
        String text = displayField.getText();
        if (!text.contains(".")) {
            displayField.setText(text + ".");
        }
    }

    //括号
    private void appendChar(String ch) {
        if (engine.isNewInput()) {
            displayField.setText(ch);
            engine.setNewInput(false);
        } else {
            displayField.setText(displayField.getText() + ch);
        }
    }

    //运算符
    private void setOperator(Operation op) {
        try {
            double currentValue = Double.parseDouble(displayField.getText());
            engine.setOperator(op, currentValue);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "当前输入无效，请重新输入数字", "提示", JOptionPane.WARNING_MESSAGE);
            displayField.setText("0");
            engine.setNewInput(true);
        }
    }

    //一元运算（开方、sin、cos、tan）
    private void doUnary(Operation op) {
        double value;
        try {
            value = Double.parseDouble(displayField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "当前输入无效，请重新输入数字", "提示", JOptionPane.WARNING_MESSAGE);
            displayField.setText("0");
            engine.setNewInput(true);
            return;
        }
        try {
            CalculatorEngine.CalcResult result = engine.executeUnary(op, value);
            historyManager.addRecord(result.getRecord());
            displayField.setText(FormatUtil.formatResult(result.getValue()));
        } catch (ArithmeticException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            displayField.setText("0");
            engine.setNewInput(true);
        }
    }

    //结果
    private void calculateResult() {
        if (!engine.hasOperator()) return;

        double num2;
        try {
            num2 = Double.parseDouble(displayField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "当前输入无效，请重新输入数字", "提示", JOptionPane.WARNING_MESSAGE);
            displayField.setText("0");
            engine.setNewInput(true);
            return;
        }

        try {
            CalculatorEngine.CalcResult result = engine.calculate(num2);
            historyManager.addRecord(result.getRecord());
            displayField.setText(FormatUtil.formatResult(result.getValue()));
        } catch (ArithmeticException e) {
            JOptionPane.showMessageDialog(this, "除数不能为0，请重新输入除数！", "错误", JOptionPane.ERROR_MESSAGE);
            displayField.setText("0");
            engine.setNewInput(true);
        }
    }

    //c
    private void clearAll() {
        displayField.setText("0");
        engine.clear();
    }

    //del
    private void backspace() {
        String text = displayField.getText();
        if (text.length() > 1) {
            displayField.setText(text.substring(0, text.length() - 1));
        } else {
            displayField.setText("0");
            engine.setNewInput(true);
        }
    }

    //history
    private void showHistory() {
        if (historyManager.isEmpty()) {
            JOptionPane.showMessageDialog(this, "暂无运算记录", "计算历史", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JTextArea textArea = new JTextArea(historyManager.toPlainString());
        textArea.setEditable(false);
        textArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "计算历史", JOptionPane.INFORMATION_MESSAGE);
    }

    //导出历史
    private void downloadHistory() {
        String[] options = {"保存到文件", "从文件加载", "取消"};
        int choice = JOptionPane.showOptionDialog(this, "请选择历史操作", "历史管理",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);
        if (choice == 0) {
            saveHistoryToFile();
        } else if (choice == 1) {
            loadHistoryFromFile();
        }
    }

    //保存历史（写入文件）
    private void saveHistoryToFile() {
        if (historyManager.isEmpty()) {
            JOptionPane.showMessageDialog(this, "暂无运算记录可保存", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("history.txt"))) {
            writer.write("===== 计算历史 =====\n");
            for (String record : historyManager.getAllRecords()) {
                writer.write(record + "\n");
            }
            JOptionPane.showMessageDialog(this, "历史已保存到 history.txt", "保存成功", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "保存失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    //加载历史（读取文件）
    private void loadHistoryFromFile() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "读取失败，请确认 history.txt 文件存在", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JTextArea textArea = new JTextArea(content.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "加载的历史记录", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // 使用 SwingUtilities 确保线程安全
        SwingUtilities.invokeLater(() -> new CalculatorGUI());
    }
}
