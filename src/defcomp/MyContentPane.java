package defcomp;

import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyContentPane extends JPanel {
	private JTextField filePathField;
	private MyHintTextField matchField;
	private JTextField targetField;
	private JButton execBtn;
	private JCheckBox ck_dg;
	private JCheckBox ck_pre;

	{
		init();
	}

	public MyContentPane() {
		super();
	}

	public MyContentPane(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public MyContentPane(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public MyContentPane(LayoutManager layout) {
		super(layout);
	}

	// 初始化组件
	public void init() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 设置面板的布局方式为盒布局,且控件的排列方式为竖直排列

		JPanel topPanel = new JPanel(); // 创建上面的面板,并设置为流式布局
		topPanel.setLayout(new FlowLayout());

		JPanel bottomPanel = new JPanel(); // 创建下面的面板
		bottomPanel.setLayout(new FlowLayout());

		this.add(topPanel);
		this.add(bottomPanel); // 添加面板到当前面板上

		filePathField = new JTextField(44); // 创建路径输入文本框
		filePathField.setText("D:\\");

		ck_dg = new JCheckBox("递归检索", false);
		topPanel.add(new JLabel("工作路径:"));
		topPanel.add(filePathField);
		topPanel.add(ck_dg); // 上面的面板添加控件

		matchField = new MyHintTextField(22);
		matchField.setText("(.*?)_(.*?)\\.mp3");
		targetField = new JTextField(22);
		targetField.setText("(1)_(2).mp3");
		ck_pre = new JCheckBox("预执行", true);
		execBtn = new JButton("执行");

		bottomPanel.add(matchField);
		bottomPanel.add(targetField);
		bottomPanel.add(ck_pre);
		bottomPanel.add(execBtn); // 下面的 面板添加控件

		execBtn.addActionListener(new MyActionListener(filePathField, matchField, targetField, ck_dg, ck_pre));
	}
}
