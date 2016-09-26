package cal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.alee.laf.WebLookAndFeel;


public class Weblaf_calculator extends JFrame implements ActionListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5568239470063633971L;
	//15个函数功能键的名字
	private final String[] FUNC_KEYS = { "", "", "", "", "", "", "", "",
            "", "", "", "", "", "", ""};
	//15个函数功能键
    private JButton func_keys[] = new JButton[FUNC_KEYS.length];
    
    //计算器下方数字和运算符键的名字
    private final String[] KEYS = { "7", "8", "9", "*", "(", "4", "5", "6",
            "/", ")", "1", "2", "3", "+", "=", "0", ".", "-" };
    //计算器下方数字和运算符键
    private JButton keys[] = new JButton[KEYS.length];
    //文本显示区域
    JTextField reText = new JTextField("0");
    // 标志用户按的是否是整个表达式的第一个数字,或者是运算符后的第一个数字
    private boolean firstDigit = true;
    // 计算的中间结果。
    private double resultNum = 0.0;
    // 当前运算的运算符
    private String operator = "=";
    // 操作是否合法
    private boolean operateValidFlag = true;
    
	
	public Weblaf_calculator() {
        super();
        // 初始化计算器
        init();
        // 设置计算器的背景颜色
        this.setBackground(Color.LIGHT_GRAY);
        // 设置文字标题
        this.setTitle("计算器美化测试版");
        //居中显示
        this.setLocationRelativeTo(null);
        //默认退出  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗体大小
        this.setSize(435, 550);
        // 不许修改计算器的大小
        this.setResizable(false);
    }
	
	//初始化计算器
	private void init() {
        //采用TabbedPane,实现多标签切换,共有3个选项卡		
		JTabbedPane background = new JTabbedPane(JTabbedPane.NORTH);
		
		
		//初始化第一个面板
		JPanel Pane1 = new JPanel();
		Pane1.setLayout(null);//设置布局NULL 
		
		//文本显示区域
        reText.setBounds(28, 20, 370, 120);//设置文本框区域位置及大小
        reText.setHorizontalAlignment(JTextField.RIGHT);// 文本框中的内容采用右对齐方式
        reText.setEditable(false);// 不允许修改结果文本框
        //reText.setBackground(Color.WHITE);// 设置文本框背景颜色为白色
        reText.setFont(new Font("楷体",Font.BOLD,30));//设置文本字体及大小
        Pane1.add(reText);
		
		//加入3个功能键   
        JButton backspace = new JButton("←");
        Pane1.add(backspace);
        backspace.setBounds(298, 155, 100, 30);
        JButton ce = new JButton("CE");
        Pane1.add(ce);
        ce.setBounds(160, 155, 100, 30);
        JButton c = new JButton("C");
        Pane1.add(c);
        c.setBounds(28, 155, 100, 30);
        
		// 初始化计算器上15个函数功能键的按钮
        for(int i = 0; i < 15; i++) {
        	func_keys[i] = new JButton(FUNC_KEYS[i]);
        	Pane1.add(func_keys[i]);
        	func_keys[i].setBounds(i%5*(66+10)+28, i/5*(24+10)+200, 66, 25);
        }
        
        // 初始化计算器下方18个按键
        for(int i = 0; i < 14; i++) {
        	keys[i] = new JButton(KEYS[i]);
        	Pane1.add(keys[i]);
        	
        	keys[i].setBounds(i%5*(66+10)+28, i/5*(35+10)+305, 66, 35);
        }
        //等号键
        keys[14] = new JButton(KEYS[14]);
        Pane1.add(keys[14]);
        keys[14].setBounds(14%5*(66+10)+28, 14/5*(35+10)+305, 66, 80);
        //0键
        keys[15] = new JButton(KEYS[15]);
        Pane1.add(keys[15]);
        keys[15].setBounds(28, 440, 142, 35);
        //"."和"-"键
        keys[16] = new JButton(KEYS[16]);
        Pane1.add(keys[16]);
        keys[16].setBounds(180, 440, 66, 35);
        keys[17] = new JButton(KEYS[17]);
        Pane1.add(keys[17]);
        keys[17].setBounds(256, 440, 66, 35);
        
        //初始化第二个面板
        JPanel Pane2 = new JPanel();
        //初始化第三个面板
        JPanel Pane3 = new JPanel();
        //将三个面板加入tabbedpand
        background.add("Science",Pane1);
        background.add("Finance",Pane2);
        background.add("Matrix",Pane3);
        //将选项卡窗体加入到主窗体上
        getContentPane().add(background, BorderLayout.CENTER);
        
        // 为各按钮添加事件侦听器
        // 都使用同一个事件侦听器，即本对象。本类的声明中有implements ActionListener
        backspace.addActionListener(this);//为3个按钮添加监听器
        ce.addActionListener(this);
        c.addActionListener(this);
        for (int i = 0; i < FUNC_KEYS.length; i++) { //为15个函数功能键添加监听器
            func_keys[i].addActionListener(this);
        }
        for (int i = 0; i < KEYS.length; i++) { //为18个数字及运算符键添加监听器
            keys[i].addActionListener(this);
        }
    }  
	
	//处理事件(15个功能函数键，括号键未实现）
    public void actionPerformed(ActionEvent e) {
    	// 获取事件源的标签
        String label = e.getActionCommand();
        if (label.equals("←")) {
            // 用户按了"Backspace"键
            handleBackspace();
        } else if (label.equals("CE")) {
            // 用户按了"CE"键
            reText.setText("0");
        } else if (label.equals("C")) {
            // 用户按了"C"键
            handleC();
        } else if ("0123456789.".indexOf(label) >= 0) {
            // 用户按了数字键或者小数点键
            handleNumber(label);
            // handlezero(zero);
        } else {
            // 用户按了运算符键
            handleOperator(label);
        }
    }
    
    //处理backspace键被按下的事件
    private void handleBackspace() {
        String text = reText.getText();
        int i = text.length();
        if (i > 0) {
            // 退格，将文本最后一个字符去掉
            text = text.substring(0, i - 1);
            if (text.length() == 0) {
                // 如果文本没有了内容，则初始化计算器的各种值
                reText.setText("0");
                firstDigit = true;
                operator = "=";
            } else {
                // 显示新的文本
                reText.setText(text);
            }
        }
    }
    
    // 处理数字键被按下的事件
    private void handleNumber(String key) {
        if (firstDigit) {
            // 输入的第一个数字
            reText.setText(key);
        } else if ((key.equals(".")) && (reText.getText().indexOf(".") < 0)) {
            // 输入的是小数点，并且之前没有小数点，则将小数点附在结果文本框的后面
            reText.setText(reText.getText() + ".");
        } else if (!key.equals(".")) {
            // 如果输入的不是小数点，则将数字附在结果文本框的后面
            reText.setText(reText.getText() + key);
        }
        // 以后输入的肯定不是第一个数字了
        firstDigit = false;
    }
    
    // 处理C键被按下的事件
    private void handleC() {
        // 初始化计算器的各种值
        reText.setText("0");
        firstDigit = true;
        operator = "=";
    }
    
    // 处理运算符键被按下的事件
    private void handleOperator(String key) {
        if (operator.equals("/")) {
            // 除法运算
            // 如果当前结果文本框中的值等于0
            if (getNumberFromText() == 0.0) {
                // 操作不合法
                operateValidFlag = false;
                reText.setText("除数不能为零");
            } else {
                resultNum /= getNumberFromText();
            }
        }  else if (operator.equals("+")) {
            // 加法运算
            resultNum += getNumberFromText();
        } else if (operator.equals("-")) {
            // 减法运算
            resultNum -= getNumberFromText();
        } else if (operator.equals("*")) {
            // 乘法运算
            resultNum *= getNumberFromText();
        }  else if (operator.equals("=")) {
            // 赋值运算
            resultNum = getNumberFromText();
        }
        if (operateValidFlag) {
            // 双精度浮点数的运算
            long t1;
            double t2;
            t1 = (long) resultNum;
            t2 = resultNum - t1;
            if (t2 == 0) {
                reText.setText(String.valueOf(t1));
            } else {
                reText.setText(String.valueOf(resultNum));
            }
        }
        // 运算符等于用户按的按钮
        operator = key;
        firstDigit = true;
        operateValidFlag = true;
    }
    
    // 从结果文本框中获取数字
    private double getNumberFromText() {
        double result = 0;
        try {
            result = Double.valueOf(reText.getText()).doubleValue();
        } catch (NumberFormatException e) {
        }
        return result;
    }
	/*
	public static void main(String[] args) {
		new Weblaf_calculator().setVisible(true);
	}
	*/
	public static void main ( String[] args )
    {
        // You should work with UI (including installing L&F) inside Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater ( new Runnable ()
        {
            public void run ()
            {
                // Install WebLaF as application L&F
                WebLookAndFeel.install ();
                new Weblaf_calculator().setVisible(true);
            }
        } );
    }

}
