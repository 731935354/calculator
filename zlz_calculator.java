package cal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import java.lang.Math;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class zlz_calculator extends JFrame implements ActionListener{
	
	/**
	 * Science界面
	 */
	//AC与退格键
	JButton backspace = new JButton("←");
	JButton AC = new JButton("AC");
	//15个函数功能键的名字
	private final String[] FUNC_KEYS = {"Abs", "sin", "cos", "x^2", "x^3", "tan", "rad-deg", "deg-rad", "^", "1/x", "π", "log", "ln", "x!", "√x",  
    };
	//15个函数功能键
    private JButton func_keys[] = new JButton[FUNC_KEYS.length];
    //计算器下方数字和运算符键的名字
    private final String[] KEYS = { "7", "8", "9", "*", "(", "4", "5", "6",
            "/", ")", "1", "2", "3", "+", "=", "0", ".", "-" };
    //计算器下方数字和运算符键
    private JButton keys[] = new JButton[KEYS.length];
    //Science界面文本显示区域
    JTextField reText = new JTextField("0");
    // 变量
    private ArrayList<String> list;//记录输入字符
    
    private int statuesnumber = 0;
    private boolean vbegin = true;// 控制输入，true为重新输入，false为接着输入  
    private boolean equals_flag = true;// true为未输入=，false表示输入=  
    private boolean isContinueInput = true;// true为正确，可以继续输入，false错误，输入锁定  
  
    final int MAXLEN = 500;  
    final double  PI = 3.141592657;
    double resultNum = 0.0;//存储计算结果
    /**
	 * finance界面
	 */ 
    //finance界面文本显示区域
    JTextArea fText = new JTextArea("");
    //按键
    JButton clear = new JButton("clear");
    private final String[] F_KEYS = {"EAR", "PV", "FV", "call", "pvc", "fvc", "put", "PMT", "forward", "forwardwcs", "fpwcdy", "fpwsc", "fpwcy"};
    private JButton f_keys[] = new JButton[F_KEYS.length];
    // 变量
    static double[] num1 = null; //存放非数组形式参数
    static double[] num2 = null; //存放第一个数组内参数
	static double[] num3 = null; //存放第二个数组
    /**
	 * Matrix界面
	 */
    
    /**
	 * Matrix使用字典{'Clear':清空,'Add':加法,'Dif':减法,'Mul':乘法,'Rev':可逆矩阵,'Det':行列式值,
	 * 'Eig':矩阵特征值,'Tra':转置矩阵,'UpT':上三角，Rank':秩,'Anster'：加减乘法的等号.}
	 */
    //matrix界面文本显示区域
    JTextArea textArea = new JTextArea ( "input matrix here:"+"(example)\n"
    		 +"1 2 3;\n"+"4 5 6;\n"+"7 8 9\n"+"Press 'Clear' before input matrix");
    // 按钮
    private final String[] MATRIX_KEYS = {"Clear", "Answer", "Add", "Dif", "Mul", "Det", "Rank", "UpT", "Eig",
    		"Rev", "Tra"};
    private JButton matrix_keys[] = new JButton[MATRIX_KEYS.length];
    // 变量
    private double[][] add1;
    private double[][] dif1;
    private double[][] mul1;
    
    public zlz_calculator() {
        super();
        // 初始化计算器
        init();
        // 设置计算器的背景颜色
        this.setBackground(Color.LIGHT_GRAY);
        // 设置文字标题
        this.setTitle("计算器");
        //居中显示
        this.setLocationRelativeTo(null);
        //默认退出  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗体大小
        this.setSize(435, 575);
        // 不许修改计算器的大小
        this.setResizable(false);
    }
	
	//初始化计算器
	private void init() {
        initWindow(); //初始化界面
        initActionEvent();  //添加事件处理
	}
	
	public void initWindow() {
		//采用TabbedPane,实现多标签切换,共有3个选项卡		
		JTabbedPane background = new JTabbedPane(JTabbedPane.NORTH);//选项卡位于界面上方
		
		//初始化science面板
		JPanel Pane1 = new JPanel();
		Pane1.setLayout(null);//设置布局NULL，采用绝对布局
				
		//文本显示区域
		reText.setBounds(28, 20, 370, 120);//设置文本框区域位置及大小
		reText.setHorizontalAlignment(JTextField.RIGHT);// 文本框中的内容采用右对齐方式
	    reText.setEditable(false);// 不允许修改结果文本框
		reText.setFont(new Font("楷体",Font.BOLD,30));//设置文本字体及大小
		Pane1.add(reText); // 将文本显示区域加入面板
		list = new ArrayList<String>(); 
				
	    //加入2个功能键   
		Pane1.add(backspace);
		backspace.setBounds(298, 155, 100, 30);
		Pane1.add(AC);
		AC.setBounds(28, 155, 100, 30);
		        
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
         
        // 初始化finance面板
        JPanel Pane2 = new JPanel();
        Pane2.setLayout(null);
        // finance界面文本显示区域
        final JScrollPane scrollPane2 = new JScrollPane (fText);
        scrollPane2.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        scrollPane2.setHorizontalScrollBarPolicy ( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );
        scrollPane2.setBounds(28, 20, 370, 200);
        Pane2.add(scrollPane2);
        // 初始化clear按键
        Pane2.add(clear);
		clear.setBounds(28, 240, 100, 30);
		// 初始化EAR按钮
		f_keys[0] = new JButton(F_KEYS[0]);
		f_keys[0].setBounds(298, 240, 100, 30);
		Pane2.add(f_keys[0]);
		// 初始化后12个函数功能键的按钮
        for(int i = 0; i < 12; i++) {
        	f_keys[i+1] = new JButton(F_KEYS[i+1]);
        	Pane2.add(f_keys[i+1]);
        	f_keys[i+1].setBounds(i%3*(100+35)+28, i/3*(30+20)+290, 100, 30);
        }
		
        // 初始化matrix面板
        JPanel Pane3 = new JPanel();
        Pane3.setLayout(null);
        
        // matrix界面文本显示区域
        final JScrollPane scrollPane = new JScrollPane ( textArea );
        scrollPane.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        scrollPane.setHorizontalScrollBarPolicy ( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );
        scrollPane.setBounds(28, 20, 370, 240);
        Pane3.add(scrollPane);
        
        // 初始化按键
        for(int i = 0; i < 11; i++) {
        	matrix_keys[i] = new JButton(MATRIX_KEYS[i]);
        }
        matrix_keys[0].setBounds(28, 285, 100, 30);
        matrix_keys[1].setBounds(298, 285, 100, 30);
        matrix_keys[2].setBounds(163, 285, 100, 30);
        Pane3.add(matrix_keys[0]);
        Pane3.add(matrix_keys[1]);
        Pane3.add(matrix_keys[2]);
        for(int i = 0; i < 9; i++) {
            matrix_keys[i+2].setBounds(i%3*(100+35)+28, i/3*(30+20)+335, 100, 30);
            Pane3.add(matrix_keys[i+2]);
        }
        
        //将三个面板加入tabbedpand
        background.add("Science",Pane1);
        background.add("Finance",Pane2);
        background.add("Matrix",Pane3);
        
        //将选项卡窗体加入到主窗体上
        getContentPane().add(background, BorderLayout.CENTER);
	}
	
	public void initActionEvent() {
		/**
		 * 为各按钮添加事件侦听器
		 * 都使用同一个事件侦听器，即本对象。本类的声明中有implements ActionListener
		 */
        
		// 为science界面按钮添加监听器
		backspace.addActionListener(this);
        AC.addActionListener(this);
        for (int i = 0; i < FUNC_KEYS.length; i++) { 
            func_keys[i].addActionListener(this);
        }
        for (int i = 0; i < KEYS.length; i++) { 
            keys[i].addActionListener(this);
        }
        //为finance界面按钮添加监听器
        clear.addActionListener(this);
        for (int i = 0; i < 12; i++) {
        	f_keys[i].addActionListener(this);
        }
        //为matrix面板按钮按键添加监听器
        for (int i = 0; i < 11; i++) { 
        	matrix_keys[i].addActionListener(this);
        }
	}
	
	//处理事件
    public void actionPerformed(ActionEvent e) {
    	String label = e.getActionCommand(); // 获取事件源的标签
        if (label.equals("←")) {
            handleBackspace(); // 用户按了"Backspace"键
        } else if (label.equals("AC")) {
        	list.clear();  
            reText.setText("0");  
            vbegin = true;  
            equals_flag = true;  
        } else if (label.equals("Abs")) { //Science界面函数按钮
            handleAbs();
        } else if (label.equals("sin")) {
        	handleSin();
        } else if (label.equals("cos")) {
        	handleCos();
        } else if (label.equals("x^2")) {
        	handleSquare();
        } else if (label.equals("x^3")) {
        	handleCube();
        } else if (label.equals("rad-deg")) {
        	handleRaddeg();
        } else if (label.equals("tan")) {
        	handleTan();
        } else if (label.equals("deg-rad")) {
        	handleDegrad();
        } else if (label.equals("1/x")) {
        	handleReciprocal();
        } else if (label.equals("log")) {
        	handleLog();
        } else if (label.equals("ln")) {
        	handleLn();
        } else if (label.equals("x!")) {
        	handleFact();
        } else if (label.equals("√x")) {
        	handleRadical();
        } else if(label.equals("Det")) { //Matrix运算界面按钮
        	handleDet();
        } else if(label.equals("Rank")) {
        	handleRank();
        } else if(label.equals("Clear")) {
        	handleClear();
        } else if(label.equals("UpT")) {
        	handelUpT();
        } else if(label.equals("Rev")) {
        	handelRev();
        } else if(label.equals("Tra")) {
        	handelTra();
        } else if(label.equals("Add")) {
        	handelAdd();
        } else if(label.equals("Dif")) {
        	handelDif();
        } else if(label.equals("Mul")) {
        	handelMul();
        } else if(label.equals("Eig")) {
        	handelEig();
        } else if(label.equals("Answer")){
        	handelAnswer();
        }else if(label.equals("clear")){
        	handelclear();
        }else if(label.equals("EAR")){ //Finance界面函数按钮
        	handelEAR();
        }else if(label.equals("call")){
        	 handelcall();
        }else if(label.equals("put")){
        	 handelput();
        }else if(label.equals("pvc")){
        	handelpvc();
        }else if(label.equals("fvc")){
        	handelfvc();
        }else if(label.equals("fpwsc")){
        	handelfpwsc();
        }else if(label.equals("fpwcy")){
        	handelfpwcy();
        }else if(label.equals("FV")){
        	handelFV();
        }else if(label.equals("PV")){
        	handelPV();
        }else if(label.equals("PMT")){
        	handelPMT();
        }else if(label.equals("forward")){
        	handelforward();
        }else if(label.equals("forwardwcs")){
        	handelforwardwcs();
        }else if(label.equals("fpwcdy")){
        	handelfpwcdy();
        }else {
            handle(label); //Science界面数字键，+-*、，π，^
        }  
    }
    
    //处理Abs键被按下的事件
    private void handleAbs() {
    	resultNum = getNumberFromText();
    	if(resultNum >= 0) {
    		reText.setText(String.valueOf(resultNum));
    	}
    	else {
    		reText.setText(String.valueOf(resultNum*(-1)));
    	}
    	vbegin = true;
    }
    
    //处理sin键被按下的事件
    private void handleSin() {
    	resultNum = Math.sin(getNumberFromText()*Math.PI/180);
    	reText.setText(String.valueOf(resultNum));
    	vbegin = true;
    }
    
    //处理cos键被按下的事件
    private void handleCos() {
    	resultNum = Math.cos(getNumberFromText()*Math.PI/180);
    	reText.setText(String.valueOf(resultNum));
    	vbegin = true;
    }
    
    //处理x^2键被按下的事件
    private void handleSquare() {
    	resultNum = getNumberFromText() * getNumberFromText();
    	reText.setText(String.valueOf(resultNum));
    	vbegin = true;
    }
    
    //处理x^3键被按下的事件
    private void handleCube() {
    	resultNum = getNumberFromText()*getNumberFromText()*getNumberFromText();
    	reText.setText(String.valueOf(resultNum));
    	vbegin = true;
    }
    
    //处理Raddeg键被按下的事件
    private void handleRaddeg() {
    	resultNum = getNumberFromText()*180/Math.PI;
    	reText.setText(String.valueOf(resultNum));
    	vbegin = true;
    }
    
    //处理tan键被按下的事件
    private void handleTan() {
    	resultNum = Math.tan(getNumberFromText()*Math.PI/180);
    	reText.setText(String.valueOf(resultNum));
    	vbegin = true;
    }
    
    //处理deg-rad键被按下的事件
    private void handleDegrad() {
    	resultNum = getNumberFromText()*Math.PI/180;
    	reText.setText(String.valueOf(resultNum));
    	vbegin = true;
    }
    
    //处理1/x键被按下的事件
    private void handleReciprocal() {
    	if (resultNum == 0.0) {
            // 操作不合法
            reText.setText("零没有倒数");
        } else {
            resultNum = 1 / getNumberFromText();
        }
    	reText.setText(String.valueOf(resultNum));
    	vbegin = true;
    }
    
    //处理Log键被按下的事件
    private void handleLog() {
    	resultNum = Math.log(getNumberFromText()) / Math.log(10.0);
    	reText.setText(String.valueOf(resultNum));
    	vbegin = true;
    }
    
    //处理Ln键被按下的事件
    private void handleLn() {
    	resultNum = Math.log(getNumberFromText()) / Math.log(Math.E);
    	reText.setText(String.valueOf(resultNum));
    	vbegin = true;
    }
    
    //处理x!键被按下的事件
    private void handleFact() {
    	resultNum = 1;
        for(int i=1;i <= getNumberFromText(); i++){
	    	  resultNum *= i;
	      }
    	reText.setText(String.valueOf(resultNum));
    	vbegin = true;
    }
    
    //处理√x键被按下的事件
    private void handleRadical() {
    	resultNum = Math.sqrt(getNumberFromText());
    	reText.setText(String.valueOf(resultNum));
    	vbegin = true;
    }
    
    //处理backspace键被按下的事件
    private void handleBackspace() {  
        String text = reText.getText();  
        list.add(text);  
        int i = text.length();  
        if (i > 0 && list.size() > 0) {  
            text = text.substring(0, i - 1);  
            list.remove(list.size() - 1); // 移除栈顶的那个元素  
            if (text.length() == 0) {  
                list.clear();  
                reText.setText("0");  
                vbegin = true;  
                equals_flag = true;  
            } else {  
                reText.setText(text);  
            }  
        }  
    }
    
    /**
     * Matrix界面按钮
     */
    //处理Clear键被按下的事件
    private void handleClear(){
    	textArea.setText("");
    }
    
    //处理Answer键被按下的按钮
    private void handelAnswer(){
    	if(statuesnumber == 0){
    		double[][] answer = getMatrixfromText();
    		textArea.setText("");
    		for(int i = 0; i<answer.length; i++){
    			for(int j = 0; j<answer[0].length;j++){
    			  textArea.append(String.valueOf(answer[i][j])+"  ");	
    			}
    			textArea.append("\n");
    		}
    	}
    	if(statuesnumber == 1){
    		double[][] add2 = getMatrixfromText();
    		textArea.setText("");
    		for(int i=0;i<add1.length;i++)
    		{
    			for(int j=0;j<add1[0].length;j++)
    			{
    				 textArea.append(String.valueOf(matrixAdd(add1,add2)[i][j]) + "  ");//
    			}
    			 textArea.append("\n");
    		}
    	   statuesnumber = 0;
    	}
    	if(statuesnumber == 2){
    		double[][] dif2 = getMatrixfromText();
    		textArea.setText("");
    		for(int i=0;i<dif1.length;i++)
    		{
    			for(int j=0;j<dif1[0].length;j++)
    			{
    				 textArea.append(String.valueOf(matrixDif(dif1,dif2)[i][j])+"  ");
    			}
    			textArea.append("\n"); 
    		}
    		statuesnumber = 0; 
    	}
    	if(statuesnumber ==3){
    		double[][] mul2 = getMatrixfromText();
    		double[][] mul3 = matrixMultiply(mul1,mul2);
    		textArea.setText("");
    		for(int i=0;i<mul3.length;i++)
    		{
    			for(int j=0;j<mul3[0].length;j++)
    			{
    				 textArea.append(String.valueOf(matrixMultiply(mul1,mul2)[i][j])+"  ");
    			}
    			textArea.append("\n"); 
    		}
    		statuesnumber = 0; 
    	}
    	if(statuesnumber != 0 && statuesnumber != 1 && statuesnumber != 2 && statuesnumber != 3)
    {
    		textArea.setText("");
    	}
    }
    
    //处理Add键被按下的按钮
    private void handelAdd(){
    	 add1 = getMatrixfromText();
    	textArea.setText("");
    	statuesnumber = 1;
    }
    
    //处理Dif键被按下的按钮
    private void handelDif(){
    	dif1 = getMatrixfromText();
    	textArea.setText("");
    	statuesnumber = 2;
    }
    
    //处理Mul键被按下的按钮
    private void handelMul(){
        mul1 = getMatrixfromText();
    	textArea.setText("");
    	statuesnumber = 3;
    }
    
    //处理Det键被按下的事件
    private void handleDet(){
    	textArea.setText(String.valueOf(Det(getMatrixfromText(),getMatrixfromText().length-1)));
    }
    
    //处理Rank键被按下的事件
    private void handleRank(){
    	double[][] det = getMatrixfromText();
    	textArea.setText(String.valueOf(Rank(det)));
    }
    
    //处理UpT键被按下的事件
    private void handelUpT(){
    	double[][] upt = getMatrixfromText();
    	textArea.setText("");
    	UpT(upt);
    	String Strr=new String("");
    	for(int i=0;i<upt.length;i++)
    	{
    		for(int j=0;j<upt[0].length;j++)
    		{
    			textArea.append(String.valueOf(upt[i][j])+"  ");
    	 
    		}
    		textArea.append("\n"); 
    	}
    		 
    }
    
    //处理Eig健被按下的按钮
    private void handelEig(){
    	double[][] eig = getMatrixfromText();
    	textArea.setText("");
    	double[][] GetEig = new double[eig.length][eig.length];
        EigenValue(eig,eig.length,400,4,GetEig);
    	for(int i=0;i<eig.length;i++)
    	{
    		 textArea.append(String.valueOf(GetEig[i][0])+"  ");//输出特征值
    		 	//String str1=String.valueOf(GetEig[i][1]);
    	}
    	textArea.append("\n");
    	textArea.append("------------------------------------------\n");
    	textArea.append("Loop = 400,   error = pow(10,-4)");
    }
    
    //处理Rev健被按下的按钮
    private void handelRev(){
	    double[][] a =getMatrixfromText(); 
	    double[][] b = new double[a.length][a.length];
	    textArea.setText("");
	    Inverse(a,a.length-1,b);
        for(int i=0;i<b.length;i++){
            for(int j=0;j<b[0].length;j++)
            {
                textArea.append(String.valueOf(b[i][j])+"  "); 
            }
            textArea.append("\n");
        }
    }
    
    //处理Tra健被按下的按钮
    private void handelTra(){
    	
        double[][] tra = getMatrixfromText();
        textArea.setText("");
        double[][] tra1 = new double[tra.length][tra[0].length];
    	Tra(tra,tra1);
    	for(int i=0;i<tra.length;i++)
    	{
    		for(int j=0;j<tra[0].length;j++)
    		{
    			 textArea.append(String.valueOf(tra1[i][j])+ "  ");//
    			 //System.out.println(tra[i][j]);
    		}
    		 textArea.append("\n");
    		
    	}
    }
    
    /**
     * Finance界面按钮
     */
    //处理clear健被按下的按钮
    private void handelclear(){
    	fText.setText("");
    }
    
    //处理EAR健被按下的按钮
    private void handelEAR(){
    	String s = fText.getText();
    	fText.setText("");
	    get_1(s);
	    fText.setText(String.valueOf(EAR(num1[0],(int)num1[1])));
	    
    }
    
    //处理call健被按下的按钮
    private void handelcall(){
    	String s = fText.getText();
    	fText.setText("");
	    get_1(s);
	    fText.setText(String.valueOf(CallPrice(num1[0],num1[1],num1[2],num1[3],num1[4]))); 
    }
    
    //处理put健被按下的按钮
    private void handelput(){
    	String s = fText.getText();
    	fText.setText("");
	    get_1(s);
	    fText.setText(String.valueOf(PutPrice(num1[0],num1[1],num1[2],num1[3],num1[4])));  
    }
    
    //处理pvc健被按下的按钮
    private void handelpvc(){
    	String s = fText.getText();
    	fText.setText("");
    	get_2(s);
    	fText.setText(String.valueOf(PVC((int)num1[0],num1[1],num2)));
    }
    
    //处理fvc健被按下的按钮
    private void handelfvc(){
    	String s = fText.getText();
    	fText.setText("");
    	get_2(s);
    	fText.setText(String.valueOf(FVC((int)num1[0],num1[1],num2)));
    }
    
    //处理fpwsc健被按下的按钮
    private void handelfpwsc(){
    	String s = fText.getText();
    	fText.setText("");
	    get_1(s);
	    fText.setText(String.valueOf(fpwsc(num1[0],num1[1],(int)num1[2],num1[3])));  
    }
    
    //处理fpwcy健被按下的按钮
    private void handelfpwcy(){
    	String s = fText.getText();
    	fText.setText("");
	    get_1(s);
	    fText.setText(String.valueOf(fpwcy(num1[0],num1[1],(int)num1[2],num1[3]))); 
    }
    
    //处理FV健被按下的按钮
    private void handelFV(){
    	String s = fText.getText();
    	fText.setText("");
	    get_1(s);
	    fText.setText(String.valueOf(FV(num1[0],num1[1],(int)num1[2]))); 
    }
    
    //处理PV健被按下的按钮
    private void handelPV(){
    	String s = fText.getText();
    	fText.setText("");
	    get_1(s);
	    fText.setText(String.valueOf(PV(num1[0],num1[1],(int)num1[2]))); 
    }
    
    //处理PMT健被按下的按钮
    private void handelPMT(){
    	String s = fText.getText();
    	fText.setText("");
	    get_1(s);
	    fText.setText(String.valueOf(PMT(num1[0],num1[1],(int)num1[2]))); 
    }
    
    //处理forward健被按下的按钮
    private void handelforward(){
    	String s = fText.getText();
    	fText.setText("");
	    get_1(s);
	    fText.setText(String.valueOf(forward(num1[0],num1[1],(int)num1[2])));
    }
    
    //处理forwardwcs健被按下的按钮
    private void handelforwardwcs(){
    	String s = fText.getText();
    	fText.setText("");
	    get_1(s);
	    fText.setText(String.valueOf(forwardwcs(num1[0],num1[1],(int)num1[2],num1[3])));
    }
    
    //处理fpwcdy健被按下的按钮
    private void handelfpwcdy(){
    	String s = fText.getText();
    	fText.setText("");
	    get_1(s);
	    fText.setText(String.valueOf(fpwcdy(num1[0],num1[1],(int)num1[2]))); 
    }
    
    /** 
     * Science界面
     * π0123456789.()+-*^/
     */
    public void handle(String key) {  
        String text = reText.getText();  
        if (equals_flag == false) { //&& "π0123456789.()+-*/^".indexOf(key) != -1  
            list.add(text);  
            vbegin = false; 
        }  
  
        if (!list.isEmpty()) {  
            TipChecker(list.get(list.size() - 1), key);  
        } else {  
            TipChecker("#", key);  
        }  
        if (isContinueInput && "π0123456789.()+-*/^".indexOf(key) != -1) {  
            list.add(key);  
        }  
  
        // 若输入正确，则将输入信息显示到显示器上  
        if (isContinueInput && "π0123456789.()+-*/^".indexOf(key) != -1) {  
            if (equals_flag == false && ("+-*/^".indexOf(key) != -1)) {  
                vbegin = false;  
                equals_flag = true;  
                printText(key);  
            } else if (equals_flag == false  
                    && ("π0123456789.()".indexOf(key) != -1)) {  
                vbegin = true;  
                equals_flag = true;  
                printText(key);  
            } else {  
                printText(key);  
            }  
  
        } else if (isContinueInput && equals_flag && key.equals("=")) {  
            isContinueInput = false;// 表明不可以继续输入  
            equals_flag = false;// 表明已经输入=  
            vbegin = true;// 重新输入标志设置true  
            process(reText.getText()); // 整个程序的核心，计算表达式的值并显示  
            list.clear();  
        }  
        isContinueInput = true;  
    }  
  
    private void printText(String key) {  
        if (vbegin) {  
            reText.setText(key);// 清屏后输出  
        } else {  
            reText.setText(reText.getText() + key);  
        }  
        vbegin = false;  
    } 
    
    /** 
     * 检测函数，对str进行前后语法检测 为Tip的提示方式提供依据，与TipShow()配合使用 编号 字符 其后可以跟随的合法字符 1 （ 
     * 数字|（|-|.|函数 2 ） 算符|）|  ^ 3 . 数字|算符|）|  ^ 4 数字 .|数字|算符|）|  ^ 5 算符 
     * 数字|（|.|函数 6   ^ （ |. | 数字 7 函数 数字|（|. 
     *  
     * 小数点前后均可省略，表示0 数字第一位可以为0 
     */  
    private void TipChecker(String tipcommand1, String tipcommand2) {  
        // Tipcode1表示错误类型，Tipcode2表示名词解释类型  
        int Tipcode1 = 0, Tipcode2 = 0;  
        // 表示命令类型  
        int tiptype1 = 0, tiptype2 = 0;  
        // 括号数  
        int bracket = 0;  
        // “+-*/ ^”不能作为第一位  
        if (tipcommand1.compareTo("#") == 0  
                && (tipcommand2.compareTo("/") == 0  
                        || tipcommand2.compareTo("*") == 0  
                        || tipcommand2.compareTo("+") == 0  
                        || tipcommand2.compareTo(")") == 0 || tipcommand2  
                        .compareTo("^") == 0)) {  
            Tipcode1 = -1;  
        }  
        // 定义存储字符串中最后一位的类型  
        else if (tipcommand1.compareTo("#") != 0) {  
            if (tipcommand1.compareTo("(") == 0) {  
                tiptype1 = 1;  
            } else if (tipcommand1.compareTo(")") == 0) {  
                tiptype1 = 2;  
            } else if (tipcommand1.compareTo(".") == 0) {  
                tiptype1 = 3;  
            } else if ("0123456789".indexOf(tipcommand1) != -1) {  
                tiptype1 = 4;  
            } else if ("+-*/".indexOf(tipcommand1) != -1) {  
                tiptype1 = 5;  
            } else if ("^".indexOf(tipcommand1) != -1) {  
                tiptype1 = 6;  
            }else if ("π".indexOf(tipcommand1) != -1){  
                tiptype1 = 7;  
            }  
            // 定义欲输入的按键类型  
            if (tipcommand2.compareTo("(") == 0) {  
                tiptype2 = 1;  
            } else if (tipcommand2.compareTo(")") == 0) {  
                tiptype2 = 2;  
            } else if (tipcommand2.compareTo(".") == 0) {  
                tiptype2 = 3;  
            } else if ("0123456789".indexOf(tipcommand2) != -1) {  
                tiptype2 = 4;  
            } else if ("+-*/".indexOf(tipcommand2) != -1) {  
                tiptype2 = 5;  
            } else if ("^".indexOf(tipcommand2) != -1) {  
                tiptype2 = 6;  
            }else if ("π".indexOf(tipcommand2) != -1){  
                tiptype2 = 7;  
            }  
              
  
            switch (tiptype1) {  
            case 1:  
                // 左括号后面直接接右括号,“+*/”（负号“-”不算）,或者" ^"  
                if (tiptype2 == 2  
                        || (tiptype2 == 5 && tipcommand2.compareTo("-") != 0)  
                        || tiptype2 == 6)  
                    Tipcode1 = 1;  
                break;  
            case 2:  
                // 右括号后面接左括号，数字，“+-*/^...π”  
                if (tiptype2 == 1 || tiptype2 == 3 || tiptype2 == 4|| tiptype2 == 7)  
  
                    Tipcode1 = 2;  
                break;  
            case 3:  
                // “.”后面接左括号，π  
                if (tiptype2 == 1 || tiptype2 == 7)  
                    Tipcode1 = 3;  
                // 连续输入两个“.”  
                if (tiptype2 == 3)  
                    Tipcode1 = 8;  
                break;  
            case 4:  
                // 数字后面直接接左括号和π  
                if (tiptype2 == 1 || tiptype2 == 7)  
                    Tipcode1 = 4;  
                break;  
            case 5:  
                // “+-*/”后面直接接右括号，“+-*/ ^”  
                if (tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6)  
                    Tipcode1 = 5;  
                break;  
            case 6:  
                // “ ^”后面直接接右括号，“+-*/ ^π”  
                if (tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6 || tiptype2 == 7)  
                    Tipcode1 = 6;  
                break;  
            case 7:  
                //"π"之后只能为"+-*/^)"不能为"π(.0123456789"  
                if (tiptype2 == 1 || tiptype2 == 3 || tiptype2 == 4 || tiptype2 == 7){  
                    Tipcode1 = 7;  
                }                     
                break;  
            }  
        }  
        // 检测小数点的重复性，Tipconde1=0,表明满足前面的规则  
        if (Tipcode1 == 0 && tipcommand2.compareTo(".") == 0) {  
            int tip_point = 0;  
            for (int i = 0; i < list.size(); i++) {  
                // 若之前出现一个小数点，则小数点计数加1  
                if (list.get(i).equals(".")) {  
                    tip_point++;  
                }  
                // 若出现以下几个运算符之一，小数点计数清零  
                if (list.get(i).equals("^") || list.get(i).equals("/")  
                        || list.get(i).equals("*") || list.get(i).equals("-")  
                        || list.get(i).equals("+") || list.get(i).equals("(")  
                        || list.get(i).equals(")")) {  
                    tip_point = 0;  
                }  
            }  
            tip_point++;  
            // 若小数点计数大于1，表明小数点重复了  
            if (tip_point > 1) {  
                Tipcode1 = 8;  
            }  
        }  
        // 检测右括号是否匹配  
        if (Tipcode1 == 0 && tipcommand2.compareTo(")") == 0) {  
            int tip_right_bracket = 0;  
            for (int i = 0; i < list.size(); i++) {  
                // 如果出现一个左括号，则计数加1  
                if (list.get(i).equals("(")) {  
                    tip_right_bracket++;  
                }  
                // 如果出现一个右括号，则计数减1  
                if (list.get(i).equals(")")) {  
                    tip_right_bracket--;  
                }  
            }  
            // 如果右括号计数=0,表明没有响应的左括号与当前右括号匹配  
            if (tip_right_bracket == 0) {  
                Tipcode1 = 10;  
            }  
  
        }  
        // 检查输入=的合法性  
        if (Tipcode1 == 0 && tipcommand2.compareTo("=") == 0) {  
            // 括号匹配数  
            int tip_bracket = 0;  
            for (int i = 0; i < list.size(); i++) {  
                if (list.get(i).equals("(")) {  
                    tip_bracket++;  
                }  
                if (list.get(i).equals(")")) {  
                    tip_bracket--;  
                }  
            }  
            // 若大于0，表明左括号还有未匹配的  
            if (tip_bracket > 0) {  
                Tipcode1 = 9;  
                bracket = tip_bracket;  
            } else if (tip_bracket == 0) {  
                // 若前一个字符是以下之一，表明=号不合法  
                if ("+-*/".indexOf(tipcommand1) != -1) {  
                    Tipcode1 = 5;  
                }  
            }  
        }  
  
        if (Tipcode1 != 0) {  
            isContinueInput = false;// 表明不可以继续输入  
        }  
    } 
    
    /* 
     * 计算表达式 从左向右扫描，数字入number栈，运算符入operator栈  
     * +-基本优先级为1， 
     * ×÷基本优先级为2， 
     * √^基本优先级为4  
     * 括号内层运算符比外层同级运算符优先级高4 
     * 当前运算符优先级高于栈顶压栈， 
     * 低于栈顶弹出一个运算符与两个数进行运算 
     * 重复直到当前运算符大于栈顶 
     * 扫描完后对剩下的运算符与数字依次计算 
     */  
    public void process(String str) {  
        int weightPlus = 0, topOp = 0, topNum = 0, flag = 1, weightTemp = 0;  
        // weightPlus为同一（）下的基本优先级，weightTemp临时记录优先级的变化  
        // topOp为weight[]，operator[]的计数器；topNum为number[]的计数器  
        // flag为正负数的计数器，1为正数，-1为负数  
        int weight[]; // 保存operator栈中运算符的优先级，以topOp计数  
        double number[]; // 保存数字，以topNum计数  
        char ch, ch_gai, operator[];// operator[]保存运算符，以topOp计数  
        String num;// 记录数字，str以+-*/() ! ^分段，+-*/() ^字符之间的字符串即为数字  
        weight = new int[MAXLEN];  
        number = new double[MAXLEN];  
        operator = new char[MAXLEN];              
        String expression = str.replace("π",String.valueOf(PI));//将字符串中的π用PI  
        // 建议用split代替字符串分割  
        StringTokenizer expToken = new StringTokenizer(expression, "+-*/()^");  
        int i = 0;  
        while (i < expression.length()) {  
            ch = expression.charAt(i);  
            // 判断正负数  
            if (i == 0) {  
                if (ch == '-')  
                    flag = -1;  
            } else if (expression.charAt(i - 1) == '(' && ch == '-')  
                flag = -1;  
            // 取得数字，并将正负符号转移给数字,E是科学计数  
            if (ch <= '9' && ch >= '0' || ch == '.' || ch == 'E') {  
                num = expToken.nextToken();//分割后的StringTokenizer中的下一个索引数据  
                ch_gai = ch;  
                // 取得整个数字  
                while (i < expression.length()  
                        && (ch_gai <= '9' && ch_gai >= '0' || ch_gai == '.' || ch_gai == 'E')) {  
                    ch_gai = expression.charAt(i++);  
                }  
                // 将指针退回之前的位置，即每个数字的末尾位置  
                if (i >= expression.length())  
                    i -= 1;  
                else {  
                    i -= 2;  
                }  
                if (num.compareTo(".") == 0)  
                    number[topNum++] = 0;  
                // 将正负符号转移给数字  
                else {  
                    number[topNum++] = Double.parseDouble(num) * flag;  
                    flag = 1;  
                }  
            }  
            // 计算运算符的优先级  
            if (ch == '(')  
                weightPlus += 4;  
            if (ch == ')')  
                weightPlus -= 4;  
            if (ch == '-' && flag == 1 || ch == '+' || ch == '*' || ch == '/'  
                    || ch == '^') {  
  
                switch (ch) {  
                // +-的优先级最低，为1  
                case '+':  
                case '-':  
                    weightTemp = 1 + weightPlus;  
                    break;  
                // x/的优先级稍高，为2  
                case '*':  
                case '/':  
                    weightTemp = 2 + weightPlus;  
                    break;  
                default:  
                    weightTemp = 4 + weightPlus;  
                    break;  
                }  
                // 如果当前优先级大于堆栈顶部元素，则直接入栈  
                if (topOp == 0 || weight[topOp - 1] < weightTemp) {  
                    weight[topOp] = weightTemp;  
                    operator[topOp] = ch;  
                    topOp++;  
                    // 否则将堆栈中运算符逐个取出，直到当前堆栈顶部运算符的优先级小于当前运算符  
                } else {  
                    while (topOp > 0 && weight[topOp - 1] >= weightTemp) {  
                        switch (operator[topOp - 1]) {  
                        // 取出数字数组的相应元素进行运算  
                        case '+':  
                            number[topNum - 2] += number[topNum - 1];  
                            break;  
                        case '-':  
                            number[topNum - 2] -= number[topNum - 1];  
                            break;  
                        case '*':  
                            number[topNum - 2] *= number[topNum - 1];  
                            break;  
                        // 判断除数为0的情况  
                        case '/':  
                            if (number[topNum - 1] == 0) {  
                                // showError(1, str_old);  
                                return;  
                            }  
                            number[topNum - 2] /= number[topNum - 1];  
                            break;  
  
                        case '^':  
                            number[topNum - 2] = Math.pow(number[topNum - 2],  
                                    number[topNum - 1]);  
                            break;  
                        // 计算时进行角度弧度的判断及转换  
                        }  
                        // 继续取堆栈的下一个元素进行判断  
                        topNum--;  
                        topOp--;  
                    }  
                    // 将运算符入堆栈  
                    weight[topOp] = weightTemp;  
                    operator[topOp] = ch;  
                    topOp++;  
                }  
            }  
            i++;  
        }  
        // 依次取出堆栈的运算符进行运算  
        while (topOp > 0) {  
            // +-x直接将数组的后两位数取出运算  
            switch (operator[topOp - 1]) {  
            case '+':  
                number[topNum - 2] += number[topNum - 1];  
                break;  
            case '-':  
                number[topNum - 2] -= number[topNum - 1];  
                break;  
            case '*':  
                number[topNum - 2] *= number[topNum - 1];  
                break;  
            // 涉及到除法时要考虑除数不能为零的情况  
            case '/':  
                if (number[topNum - 1] == 0) {  
                    // showError(1, str_old);  
                    return;  
                }  
                number[topNum - 2] /= number[topNum - 1];  
                break;  
  
            case '^':  
                number[topNum - 2] = Math.pow(number[topNum - 2],  
                        number[topNum - 1]);  
                break;  
  
            }  
            // 取堆栈下一个元素计算  
            topNum--;  
            topOp--;  
        }  
        // 如果是数字太大，提示错误信息  
        if (number[0] > 7.3E306) {  
            // showError(3, str_old);  
            return;  
        }  
        // 输出最终结果  
        reText.setText(String.valueOf(FP(number[0])));  
  
    }  
  
    public double FP(double n) {  
        DecimalFormat format = new DecimalFormat("0.#############");  
        return Double.parseDouble(format.format(n));  
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
  
    /**
     * 两个矩阵乘法处理
     * @param addend
     * @param summand
     * @return
     */
  	private double[][] matrixMultiply(double[][] addend, double[][] summand) {
  	    if (addend == null || addend.length == 0) {
  	    textArea.setText("");
  	    textArea.setText("error,try again!");
  	    }
  	    if (summand == null || summand.length == 0) {
  	    textArea.setText("");
  	    textArea.setText("error,try again!");
  	    }
        //两个矩阵的乘法仅当第一个矩阵A的列数和另一个矩阵B的行数相等时才能定义。如A是m×n矩阵和B是n×p矩阵，它们的乘积C是一个m×p矩阵 
        int row = addend.length;
        int col = summand[0].length;
        if (addend[0].length != summand.length) {
            //throw new IllegalArgumentException("summand and summand not the same type!");
        	textArea.setText("");
        	textArea.setText("矩阵A列数与矩阵B行数不一致，请重新输入");
        } 
        double[][] sum = new double[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                for (int z = 0; z <col; z++) {
                    sum[i][j] += addend[i][z] * summand[z][j];
                    //System.out.println(sum[i][j]);
                }
            }
        }
        return sum;
    }
    /**
     * 处理两个矩阵相减
     * @param addend
     * @param summand
     * @return
     */
	  
	  private double[][] matrixDif(double[][] addend, double[][] summand) {
	        if (addend == null || addend.length == 0) {
	            //throw new IllegalArgumentException("addend matrix is empty!");
	        	textArea.setText("");
	        	textArea.setText("error, try again!");
	        }
	        if (summand == null || summand.length == 0) {
	            //throw new IllegalArgumentException("summand matrix is empty!");
	        	textArea.setText("");
	        	textArea.setText("error, try again!");
	        }
	        
	        //矩阵加减要求两个矩阵类型一致，即行列数相同
	        
	        int row = addend.length;
	        int col = addend[0].length;
	        if (row != summand.length || col != summand[0].length) {
	            //throw new IllegalArgumentException("summand and summand not the same type!");
	        	textArea.setText("");
	        	textArea.setText("matrix A and matrix B are not the same type!");
	        }
	        double[][] sum = new double[row][col];
	        for (int i = 0; i < row; i++) {
	            for (int j = 0; j < col; j++) {
	                //sum[i][j] = addend[i][j] + summand[i][j];
	               sum[i][j] = addend[i][j] - summand[i][j]; //减法
	            }
	        }
	        return sum;
	    }	  
    /**
     * 两个矩阵加法处理函数 
     * @param addend
     * @param summand
     * @return
     */
	  private double[][] matrixAdd(double[][] addend, double[][] summand) {
	        if (addend == null || addend.length == 0) {
	            //throw new IllegalArgumentException("addend matrix is empty!");
	        	textArea.setText("");
	        	textArea.setText("error, try again!");
	        	
	        }
	        if (summand == null || summand.length == 0) {
	            //throw new IllegalArgumentException("summand matrix is empty!");
	        	textArea.setText("");
	        	textArea.setText("error, try again!");
	        }
	        //矩阵加减要求两个矩阵类型一致，即行列数相同
	        int row = addend.length;
	        int col = addend[0].length;
	        if (row != summand.length || col != summand[0].length) {
	            //throw new IllegalArgumentException("summand and summand not the same type!");
	        	textArea.setText("");
	        	textArea.setText("两矩阵行数列数不相等，请重新输入");
	        }
	        double[][] sum = new double[row][col];
	        for (int i = 0; i < row; i++) {
	            for (int j = 0; j < col; j++) {
	                sum[i][j] = addend[i][j] + summand[i][j];
	                // sum[i][j] = addend[i][j] - summand[i][j]; //减法
	            }
	        }
	        return sum;
	    }
    /**
     * 矩阵的转置矩阵函数
     * @param Matrix
     */
    public static void Tra(double [][]Matrix,double [][] Matrix1){
    	int Line = Matrix.length;
    	int List = Matrix[0].length;
    	  for(int i=0;i<Line;i++)
    	  {
    		  for(int j=0;j<List;j++)
    		  {
    			  Matrix1[j][i]=Matrix[i][j];
    		  }
    	  }
    	}
    
    /**
     *求矩阵的可逆矩阵函数 
     * @param a
     */
  
    public static double Inverse(double[][]Matrix,int N,double[][]MatrixC){
    	int T0;
    	int T1;
    	int T2;
    	int T3;
    	double [][]B;
    	double Num=0;
    	int Chay=0;
    	int Chax=0;
    	B=new double[N][N];
    	double add;
    	add=1/Det(Matrix,N);
    	for( T0=0;T0<=N;T0++)
    	{
    		for(T3=0;T3<=N;T3++)
    		{
    			for(T1=0;T1<=N-1;T1++)
    			{
    				if(T1<T0)
    				{
    					Chax=0;
    				}
    				else
    				{
    					Chax=1;
    				}
    				for(T2=0;T2<=N-1;T2++)
    				{
    					if(T2<T3)
    					{
    						Chay=0;
    					}
    					else
    					{
    						Chay=1;
    					}
    					B[T1][T2]=Matrix[T1+Chax][T2+Chay];
    				}//T2循环
    			}//T1循环
    			Det(B,N-1);
    			MatrixC[T3][T0]=Det(B,N-1)*add*(Math.pow(-1, T0+T3));
    			
    		}
    	}
    	return 0;
    }
     
    
    /**
     * 计算矩阵特征值的函数
     * @param Matrix
     * @param n
     * @param LoopNu
     * @param Erro
     * @param Ret
     * @return
     */
  
    public static int Hessenberg(double[][] Matrix,int n,double[][]ret)
    {

    	int i;
    	int j;
    	int k;
    	double temp;
    	int MaxNu;
    	n-=1;
    	for(k=1;k<=n-1;k++)
    	{
    		i=k-1;
    		MaxNu=k;
    		temp=Math.abs(Matrix[k][i]);
    		for(j=k+1;j<=n;j++)
    		{
    			if(Math.abs(Matrix[j][i])>temp)
    			{
    				MaxNu=j;
    			}
    		}
    		ret[0][0]=Matrix[MaxNu][i];
    		i=MaxNu;
    		if(ret[0][0]!=0)
    		{
    			if(i!=k)
    			{
    				for(j=k-1;j<=n;j++)
    				{
    					temp=Matrix[i][j];
    					Matrix[i][j]=Matrix[k][j];
    					Matrix[k][j]=temp;
    				}
    				for(j=0;j<=n;j++)
    				{
    					temp=Matrix[j][i];
    					Matrix[j][i]=Matrix[j][k];
    					Matrix[j][k]=temp;
    				}
    			}
    			for(i=k+1;i<=n;i++)
    			{
    				temp=Matrix[i][k-1]/ret[0][0];
    				Matrix[i][k-1]=0;
    				for(j=k;j<=n;j++)
    				{
    					Matrix[i][j]-=temp*Matrix[k][j];
    				}
    				for(j=0;j<=n;j++)
    				{
    					Matrix[j][k]+=temp*Matrix[j][i];
    				}
    			}
    		}
    	}
    	for(i=0;i<=n;i++)
    	{
    		for(j=0;j<=n;j++)
    		{
    			ret[i][j]=Matrix[i][j];
    		}
    	}
    	return n+1;
    }
    ////////////
    public static boolean EigenValue(double[][]Matrix,int n,int LoopNu,int Erro,double[][]Ret)
    {
    	int i=Matrix.length;
    	if(i!=n)
    	{
    		return false;
    	}
    	int j;
    	int k;
    	int t;
    	int m;
    	double[][]A=new double[n][n];
    	double erro=Math.pow(0.1, Erro);
    	double b;
    	double c;
    	double d;
    	double g;
    	double xy;
    	double p;
    	double q;
    	double r;
    	double x;
    	double s;
    	double e;
    	double f;
    	double z;
    	double y;
    	int loop1=LoopNu;
    	Hessenberg(Matrix,n,A);//将方阵K1转化成上Hessenberg矩阵A
    	m=n;
    	while(m!=0)
    	{
    		t=m-1;
    		while(t>0)
    		{
    			if(Math.abs(A[t][t-1])>erro*(Math.abs(A[t-1][t-1])+Math.abs(A[t][t])))
    			{
    				t-=1;
    			}
    			else
    			{
    				break;
    			}
    		}
    		if(t==m-1)
    		{
    			Ret[m-1][0]=A[m-1][m-1];
    			Ret[m-1][1]=0;
    			m-=1;
    			loop1=LoopNu;
    			
    		}
    		else if(t==m-2)
    		{
    			b=-(A[m-1][m-1]+A[m-2][m-2]);
    			c=A[m-1][m-1]*A[m-2][m-2]-A[m-1][m-2]*A[m-2][m-1];
    			d=b*b-4*c;
    			y=Math.pow(Math.abs(d), 0.5);
    			if(d>0)
    			{
    				xy=1;
    				if(b<0)
    				{
    					xy=-1;
    				}
    				Ret[m-1][0]=-(b+xy*y)/2;
    				Ret[m-1][1]=0;
    				Ret[m-2][0]=c/Ret[m-1][0];
    				Ret[m-2][1]=0;
    			}
    			else
    			{
    				Ret[m-1][0]=-b/2;
    				Ret[m-2][0]=Ret[m-1][0];
    				Ret[m-1][1]=y/2;
    				Ret[m-2][1]=-Ret[m-1][1];
    			}
    			m-=2;
    			loop1=LoopNu;
    		}
    		else
    		{
    			if(loop1<1)
    			{
    				return false;
    			}
    			loop1-=1;
    			j=t+2;
    			while(j<m)
    			{
    				A[j][j-2]=0;
    				j+=1;
    			}
    			j=t+3;
    			while(j<m)
    			{
    				A[j][j-3]=0;
    				j+=1;
    			}
    			k=t;
    			while(k<m-1)
    			{
    				if(k!=t)
    				{
    					p=A[k][k-1];
    					q=A[k+1][k-1];
    					if(k!=m-2)
    					{
    						r=A[k+2][k-1];
    					}
    					else
    					{
    						r=0;
    					}
    				}
    				else
    				{
    					b=A[m-1][m-1];
    					c=A[m-2][m-2];
    					x=b+c;
    					y=c*b-A[m-2][m-1]*A[m-1][m-2];
    					p=A[t][t]*(A[t][t]-x)+A[t][t+1]*A[t+1][t]+y;
    					q=A[t+1][t]*(A[t][t]+A[t+1][t+1]-x);
    					r=A[t+1][t]*A[t+2][t+1];
    				}
    				if(p!=0||q!=0||r!=0)
    				{
    					if(p<0)
    					{
    						xy=-1;
    					}
    					else
    					{
    						xy=1;
    					}
    					s=xy*Math.pow(p*p+q*q+r*r, 0.5);
    					if(k!=t)
    					{
    						A[k][k-1]=-s;
    					}
    					e=-q/s;
    					f=-r/s;
    					x=-p/s;
    					y=-x-f*r/(p+s);
    					g=e*r/(p+s);
    					z=-x-e*q/(p+s);
    					for(j=k;j<=m-1;j++)
    					{
    						b=A[k][j];
    						c=A[k+1][j];
    						p=x*b+e*c;
    						q=e*b+y*c;
    						r=f*b+g*c;
    						if(k!=m-2)
    						{
    							b=A[k+2][j];
    							p+=f*b;
    							q+=g*b;
    							r+=z*b;
    							A[k+2][j]=r;
    						}
    						A[k+1][j]=q;
    						A[k][j]=p;
    						
    					}
    					j=k+3;
    					if(j>=m-1)
    					{
    						j=m-1;
    					}
    					for(i=t;i<=j;i++)
    					{
    						b=A[i][k];
    						c=A[i][k+1];
    						p=x*b+e*c;
    						q=e*b+y*c;
    						r=f*b+g*c;
    						if(k!=m-2)
    						{
    							b=A[i][k+2];
    							p+=f*b;
    							q+=g*b;
    							r+=z*b;
    							A[i][k+2]=r;
    						}
    						A[i][k+1]=q;
    						A[i][k]=p;
    					}
    				}
    				k+=1;
    			}
    			
    		}
    		
    	}
    	return true;
    }
    /**
     * 将一个矩阵转化为上三角矩阵
     * @param Matrix
     * @param n
     */
    public static void UpT(double[][]Matrix){
		int n = Matrix.length;
		int Count=1;
		while(Count<n)
		{
			for(int N=n-1;N>=Count;N--)
			{
				double z;
				if(Matrix[Count-1][Count-1]!=0){
				z=Matrix[N][Count-1]/Matrix[Count-1][Count-1];
				}else{
					for(int i=0;i<n;i++)
					{
						Matrix[Count-1][i]+=Matrix[N][i];
					}
					z=Matrix[N][Count-1]/Matrix[Count-1][Count-1];
				}
			   for(int i=0;i<n;i++)
			   {
				   Matrix[N][i]=Matrix[N][i]-Matrix[Count-1][i]*z;
			   }
			}
			Count++;
		}

	}
    /**
     * 计算矩阵秩的函数
     * @param Matrix
     * @return
     */
   
	   private int Rank(double[][] Matrix)
	   {
		   int error_ = -1;
		   int List = Matrix[0].length;
		   int n=List;
		   int m=Matrix.length ;
		   int i=0;
		   int i1;
		   int j=0;
		   int j1;
		   double temp1;
		   if(m>n)
		   {
			   i=m;
			   m=n;
			   n=i;
			   i=1;
		   }
		   m-=1;
		   n-=1;
		   double[][]temp=new double[m+1][n+1];
		   if(i==0)
		   {
			 for(i=0;i<=m;i++)
			 {
				 for(j=0;j<=n;j++)
				 {
					 temp[i][j]=Matrix[i][j];
				 }
					 
			 }
		   }
		   else
		   {
			   for(i=0;i<=m;i++)
			   {
				   for(j=0;j<=n;j++)
				   {
					   temp[i][j]=Matrix[j][i];
				   }
			   }
		   }
		   if(m==0)
		   {
			   i=0;
			   while(i<=n)
			   {
				   if(Matrix[0][i]!=0)
				   {
					   return 1;
				   }
				   i+=1;
			   }
			   return 0;
		   }
		   double error0;
		   if(error_==-1)
		   {
			   error0=Math.pow(0.1, 10);
		   }
		   else
		   {
			   error0=Math.pow(0.1, error_);
		   }
		   i=0;
		   while(i<=m)
		   {
			   j=0;
			   while(j<=n)
			   {
				   if(temp[i][j]!=0)
				   {
					   error0*=temp[i][j];
					   i=m;
					   break;
				   }
				   j+=1;
			   }
			   i+=1;
		   }
		   double error1;
		   for(i=0;i<=m;i++)
		   {
			   j=0;
			   while(j<=n)
			   {
				   if(temp[i][j]!=0)
				   {
					   break;
				   }
				   j+=1;
			   }
			   if(j<=n)
			   {
				   i1=0;
				   while(i1<=m)
				   {
					   if(temp[i1][j]!=0&&i1!=i)
					   {
						   temp1=temp[i][j]/temp[i1][j];
						   error1=Math.abs((temp[i][j]-temp[i1][j]*temp1))*100;
						   error1+=error0;
						   for(j1=0;j1<=n;j1++)
						   {
							   temp[i1][j1]=temp[i][j1]-temp[i1][j1]*temp1;
							   if(Math.abs(temp[i1][j1])<error1)
							   {
								   temp[i1][j1]=0;
							   }
						   }
						   
					   }
					   i1+=1;
				   }
			   }
		   }
		   
		   i1=0;
		   for(i=0;i<=m;i++)
		   {
			   for(j=0;j<=n;j++)
			   {
				   if(temp[i][j]!=0)
				   {
					   i1+=1;
					   break;
				   }
			   }
		   }
		   return i1;
	   }
    /**
     * 计算矩阵的行列式值函数
     * @param Matrix
     * @param N
     * @return
     */
    private static double Det(double [][]Matrix,int N)//计算n阶行列式（N=n-1）
	{
		int T0;
		int T1;
		int T2;
		double Num;
		int Cha;
		double [][] B;
		if(N>0)
		{
			Cha=0;
			B=new double[N][N];
			Num=0;
			if(N==1)
			{
				return Matrix[0][0]*Matrix[1][1]-Matrix[0][1]*Matrix[1][0];
			}
			for(T0=0;T0<=N;T0++)//T0循环
			{
				for(T1=1;T1<=N;T1++)//T1循环
				{
					for(T2=0;T2<=N-1;T2++)//T2循环
					{
						if(T2==T0)
						{
							Cha=1;
							
						}
						B[T1-1][T2]=Matrix[T1][T2+Cha];
					}//T2循环
					Cha=0;
				}//T1循环
				Num=Num+Matrix[0][T0]*Det(B,N-1)*Math.pow((-1),T0);
			}//T0循环
			return Num;
		}
		else if(N==0)
		{
			return Matrix[0][0];
		}
		
		return 0;
	}
    /**
     * 从TextArea中获取矩阵文本并将其转化为二维数组
     *
     */
    private double[][] getMatrixfromText(){
    	double[][] splitSecond = null;//初始化要返回的矩阵
    	try{
    		
    		String s = textArea.getText();//获取Matrix.Text
    		String[] spliteFirst = null;//存取第一次切开的数据
    		
    		StringTokenizer tokens=new StringTokenizer(s);//将从文本中的数据存到可分词的StringTokenizer
    		int n=tokens.countTokens();
    		String ss = ",";//用于存取第二次切开的数据
    		
    		for(int i=1;i<=n;i++){
    				String temp=tokens.nextToken();//从文本区取下一个数据
    				ss += temp+",";
    				//System.out.println(ss);
    				}
    		 
    		   spliteFirst = ss.split(";|；");
    		   splitSecond = new double[spliteFirst.length][spliteFirst.length];
    		for(int i =0;i<spliteFirst.length;i++){
    			String[] te = spliteFirst[i].split(",");
    			
    			   for(int j =0;j<te.length-1;j++){
    				splitSecond[i][j] = Double.valueOf(te[j+1]).doubleValue();
    			    }
    		   }
    		// return splitSecond;
    		  
    	 }catch (Exception ex) {//抛出矩阵处理的异常
    		   
    		 //ex.printStackTrace();
    		 textArea.setText("error,try again!");
    		  
    	 }
    	return splitSecond;
    }
    private static double EAR(double r, int m){//EAR:effective annual rate
    	double ear=Math.pow(1+r/m,m)-1;          
    	return ear;
    }
    //按钮"call"和第3个按钮"put"
    private static double d(double s, double k, double r, double d, double t){
    	double d1=(Math.log(s/k)+(r+d*d/2)*t)/(d*Math.sqrt(t));
    	return d1;
    }	
    private static double NORMSDIST(double a){
     	double p = 0.2316419;
     	double b1 = 0.31938153;
     	double b2 = -0.356563782;
     	double b3 = 1.781477937;
     	double b4 = -1.821255978;
     	double b5 = 1.330274429;
     		
     	double x = Math.abs(a);
     	double t = 1/(1+p*x);
     		
     	double val = 1 - (1/(Math.sqrt(2*Math.PI))  * Math.exp(-1*Math.pow(a, 2)/2)) * (b1*t + b2 * Math.pow(t,2) + b3*Math.pow(t,3) + b4 * Math.pow(t,4) + b5 * Math.pow(t,5) );
     		
     	if ( a < 0 )
     	{
     		val = 1- val;
     	}
     	return val;
     }
    private static double CallPrice(double s, double k, double r, double d, double t){
    	double d1=d(s, k, r, d, t);
    	double d2=d1-d*Math.sqrt(t);
    	double c = s*NORMSDIST(d1)-k*Math.pow(Math.E, -r*t)*NORMSDIST(d2);
    	return c;
    } 
    private static double PutPrice(double s, double k, double r, double d, double t){
    	double d1=d(s, k, r, d, t);
    	double d2=d1-d*Math.sqrt(t);
    	double p = k*Math.pow(Math.E, -r*t)*NORMSDIST(-d2)-s*NORMSDIST(-d1);
    	return p;
    }

    //pvc
    private static double PVC(int n, double r, double cash[]){
    	int i;
    	double total = 0;
    		
    	for(i=0; i<n; i++){
    		total+=cash[i]/Math.pow(1+r, 1+i);
    	}
    	return total;
    }

    //fvc
    private static double FVC(int n, double r, double cash[]){
    	int i;
    	double total = 0;
    		
    	for(i=0; i<n; i++){
    		total+=cash[i]*Math.pow(1+r, 1+i);
    	}
    	return total;
    }
    //fpwsc
    private static double fpwsc(double s, double r, int t, double u){
    	double fpwsc = s*Math.pow(Math.E, (r+u)*t);
    	return fpwsc;
    }
    //fpwcy
    private static double fpwcy(double s, double r, int t, double c){
    	double fpwcy = s*Math.pow(Math.E, (r-c)*t);
    	return fpwcy;
    }

    //FV
    private static double FV(double p, double r, int t){
    	int i;
    	for(i=1; i<=t; i++){
    		p*=(1+r);
    	}
    	return p;
    }
    
    //PV
    private static double PV(double p, double r, int t){
    	int i;
    	for(i=1; i<=t; i++){
    		p/=(1+r);
    	}
    	return p;
    }
    
    //PMT
    private static double PMT(double p, double r, int t){
    	double pmt = p*(r/12)*Math.pow(1+r/12, 12*t)/(Math.pow(1+r/12, 180)-1);
    	return pmt;
    }
    
    //forward
    private static double forward(double s, double r, int t){
    	double f = s*Math.pow(Math.E, r*t);
    	return f;
    }
    
    //forwardwcs
    private static double forwardwcs(double s, double r, int t, double i){
    	double fwcs = (s-i)*Math.pow(Math.E, r*t);
    	return fwcs;
    }
    //fpwcdy
    private static double fpwcdy(double s, double q, int t){
    	double fpwcdy = s*Math.pow(Math.E, (t-q)*t);
    	return fpwcdy;
    }
	
    /**
     * 将finance界面功能按钮函数所需参数存储到数组num1,num2中
     * num1中存储非数组参数，num2中存储数组内参数
     * @param s
     */
    private void get_2(String s) {
		String[] split = s.split(";|；"); //将字符串以分号分割并存入数组
		// 将非数组参数存入num1
		String[] split_1 = split[0].split(",|，"); 
		int length_1 = split_1.length; 
		num1 = new double[length_1];
		for(int i = 0; i < length_1; i++) { //将非数组以double参数存入数组
			num1[i] = Double.valueOf(split_1[i]).doubleValue();
		}
		// 将第一数组参数存入num2
		String[] split_2 = split[1].split(",|，"); 
		int length_2 = split_2.length;
		num2 = new double[length_2];
		for(int i = 0; i < length_2; i++) {
			num2[i] = Double.valueOf(split_2[i]).doubleValue();
		}
	}
	 private void get_1(String s) {
			String[] split = s.split(",|，"); //将字符串以","分割并存入数组
			// 将非数组参数存入num1
			int length = split.length; // 记录数组长度
			num1 = new double[length]; // 创建double数组以存放参数
			for(int i = 0; i < length; i++) {  
				num1[i] = Double.valueOf(split[i]).doubleValue(); //将参数以double存入数组
			}
	 }
	
	public static void main(String[] args)
	{
	    try
	    {
	    	// 设置皮肤
	    	BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	        //取消显示“设置”选项
	        UIManager.put("RootPane.setupButtonVisible", false);
	        //控制JTabbedPane左缩进
	        UIManager.put("TabbedPane.tabAreaInsets"
	        	    , new javax.swing.plaf.InsetsUIResource(3,20,2,20));
	    }
	    catch(Exception e)
	    {
	        
	    }
	    new zlz_calculator().setVisible(true);
	}

}
