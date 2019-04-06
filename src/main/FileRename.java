package main;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import defcomp.MyContentPane;

public class FileRename extends JFrame {
	public static FileRename fileRename;
	private final String FORM_TITLE = "文件批量重命名";
	private MyContentPane mainPanel;
	private String threadId;

	/**
	 * 设置窗体样式
	 */
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // 更改窗体外观
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化窗体
	 */
	public void init() {
		this.setTitle(FORM_TITLE); // 设置窗体标题
		this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("hj.png")).getImage()); // 设置图标
		this.setContentPane(getMainPanel()); // 设置内容面板
		this.pack(); // 设置内容自适应
		this.setLocationRelativeTo(null); // 设置自动居中
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置默认关闭事件
		this.setResizable(false); // 设置不可改变大小
		this.setVisible(true);
	}

	/**
	 * 获取主面板
	 * @return
	 */
	public JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new MyContentPane();
		}
		return mainPanel;
	}

	/**
	 * 设置执行信息(修改窗体标题进行显示)
	 * @param msg
	 */
	public void setMsg(String msg) {
		threadId = new SimpleDateFormat("mmss").format(new Date());
		new Thread(threadId) {
			@Override
			public void run() {
				FileRename.this.setTitle(msg);
				try {
					Thread.sleep(5000);
					if (this.getName().equals(threadId)) {
						FileRename.this.setTitle(FORM_TITLE); // 只有最后一个启动的线程才能修改回标题
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static void main(String[] args) {
		fileRename = new FileRename();
		fileRename.init();
	}
}
