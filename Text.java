package numbercruncher.matrix;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
//import javax.swing.JTextField;

public class Text extends JFrame implements ActionListener{
   JTextArea txt = new JTextArea("",10,20);
   JButton but=new JButton("保存");
   public Text(){
       setLayout(new FlowLayout());
       add(txt);
       add(but);
       but.addActionListener(this);
       setSize(300,300);
       setVisible(true);
   }
    public static void main(String[] args) {
     new Text();
 }
    @Override
     public void actionPerformed(ActionEvent e) {
   if(e.getSource()==but){
   //FileWriter fw =null;
   try{
	
	String s = txt.getText();//获取Matrix.Text
	String[] spliteFirst = null;//存取第一次切开的数据
	StringTokenizer tokens=new StringTokenizer(s);//将从文本中的数据存到可分词的StringTokenizer
	int n=tokens.countTokens(),sum=0;
	String ss = ",";//用于存取第二次切开的数据
	for(int i=1;i<=n;i++){
			String temp=tokens.nextToken();//从文本区取下一个数据
			//ss[i] = temp;
			ss += temp+",";
			System.out.println(ss);
			//sum+=Integer.parseInt(temp);//测试从文本中获得的数据
			//System.out.println(sum);
			}
	spliteFirst = ss.split(";|；");
	for(int i = 0;i<spliteFirst.length;i++){
		//spliteFirst[i] = Integer.parseInt(spliteFirst[i]);
		System.out.println(spliteFirst[i]);
	  }
	   double[][] splitSecond = null;
	  
	   for(int i =0;i<spliteFirst.length;i++){
		 String[] te = spliteFirst[i].split(",");
		 splitSecond = new double[spliteFirst.length][te.length];
		//System.out.println(te);
		for(int j =0;j<te.length-1;j++){
			splitSecond[i][j] = splitSecond[i][j] = Double.valueOf(te[j+1]).doubleValue();
		System.out.println("矩阵的第"+(i+1)+"行,第"+(j+1)+"列"+(splitSecond[i][j]+1));
		}
	}
	//System.out.println(spliteFirst);
	//System.out.println(tokens);
	//textF.setText(""+sum);
	}
   /**
    *保存文件查看从TextArea中获取的数据
//fw=new FileWriter("F:/ss.txt");
//fw.write(txt.getText());
//fw.flush();
//fw.close();
//JOptionPane.showMessageDialog(this, "保存成功");
*/
catch (Exception ex) {//抛出矩阵处理的异常
   ex.printStackTrace();
   System.out.println("error, please have a check!");
     }
    }
   }
 }
