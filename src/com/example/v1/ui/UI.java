package com.example.v1.ui;

import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.example.v1.listener.MyListener;

public class UI extends JFrame{
	public JTextField filePathField,matchField,targetField;
	public JButton renameBtn;
	public final String formTitle="文件批量重命名";
	private String threadId;
	
	public UI(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //更改窗体外观
		}catch (Exception e) {
			e.printStackTrace();
		}
		initComponents();
	}
	
	//初始化组件
	public void initComponents(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(formTitle);
		this.setIconImage(new ImageIcon(this.getClass().getResource("/resource/hj.png")).getImage());
		
		JPanel mainPanel=new JPanel();
		this.add(mainPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JPanel topPanel=new JPanel();
		topPanel.setLayout(new FlowLayout());
		
		JPanel bottomPanel=new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);
		
		JLabel filePathLabel=new JLabel("工作路径:");
		filePathField=new JTextField(44);
		filePathField.setText("D:\\");
		topPanel.add(filePathLabel);
		topPanel.add(filePathField);
		
		matchField=new JTextField(20);
		matchField.setText("(.*?)_(.*?)\\.mp3");
		targetField=new JTextField(20);
		targetField.setText("(1)_(2).mp3");
		renameBtn=new JButton("重命名");
		
		bottomPanel.add(matchField);
		bottomPanel.add(targetField);
		bottomPanel.add(renameBtn);
		
		renameBtn.addActionListener(new MyListener());
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void setMsg(String msg){
		threadId=new SimpleDateFormat("mmss").format(new Date());
		new Thread(threadId){
			@Override
			public void run() {
				UI.this.setTitle(msg);
				try {
					Thread.sleep(5000);
					if(this.getName().equals(threadId)){
						UI.this.setTitle(formTitle); //只有最后一个启动的线程才能修改回标题
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
