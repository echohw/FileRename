package defcomp;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class MyHintTextField extends JTextField {
	private String hintText = "正则表达式...";

	/**
	 * 构造代码块,用于给JTextField设置监听事件
	 */
	{
		// 添加焦点监听事件
		this.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				String content = MyHintTextField.super.getText(); // 直接调用父类中的getText()方法
				if ("".equals(content.trim())) {
					MyHintTextField.this.setText(hintText); // 显示提示文字
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				String content = MyHintTextField.super.getText(); // 直接调用父类中的getText()方法
				if (hintText.equals(content)) {
					MyHintTextField.this.setText(""); // 去除提示文字
				}
			}
		});
	}

	public MyHintTextField() {
		super();
	}

	public MyHintTextField(Document doc, String text, int columns) {
		super(doc, text, columns);
	}

	public MyHintTextField(int columns) {
		super(columns);
	}

	public MyHintTextField(String text, int columns) {
		super(text, columns);
	}

	public MyHintTextField(String text) {
		super(text);
	}

	/**
	 * 设置提示文字
	 * @param hintText
	 */
	public void setHintText(String hintText) {
		this.hintText = hintText;
	}

	/**
	 * 重写父类的getText()方法,外部无法接触到真正的数据,获取的是经过处理后的数据(在这里进行了去除提示文字的操作)
	 */
	@Override
	public String getText() {
		String content = super.getText();
		if (hintText.equals(content)) {
			return "";
		}
		return content.trim();
	}
}
