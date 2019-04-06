package defcomp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import main.FileRename;
import utils.FileUtils;

public class MyActionListener implements ActionListener {

	private JTextField filePathField;
	private JTextField matchField;
	private JTextField targetField;
	private JCheckBox ck_dg;
	private JCheckBox ck_pre;

	private String filePathFieldText;
	private String matchFieldText;
	private String targetFieldText;
	private boolean isSelected_dg;
	private boolean isSelected_pre;

	private String newFileName;

	private int successCount;
	private int failureCount;
	private int totalCount;

	public MyActionListener(JTextField filePathField, JTextField matchField, JTextField targetField, JCheckBox ck_dg,
			JCheckBox ck_pre) {
		this.filePathField = filePathField;
		this.matchField = matchField;
		this.targetField = targetField;
		this.ck_dg = ck_dg;
		this.ck_pre = ck_pre;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		filePathFieldText = filePathField.getText().trim(); // 获取输入框内容
		matchFieldText = matchField.getText().trim();
		targetFieldText = targetField.getText().trim();
		isSelected_dg = ck_dg.isSelected(); // 判断递归复选框是否选中
		isSelected_pre = ck_pre.isSelected();
		JButton btn = (JButton) e.getSource();
		btn.setEnabled(false); // 设置按钮不可编辑
		try {
			handler();
		} catch (Exception error) {
			error.printStackTrace();
		}
		btn.setEnabled(true); // 执行完后重新启用按钮
	}

	private void handler() {
		if ("".equals(filePathFieldText) || "".equals(matchFieldText) || "".equals(targetFieldText)) {
			FileRename.fileRename.setMsg("文本框内容不能为空");
			return;
		}
		File rootDir = new File(filePathFieldText);
		if (!rootDir.exists()) {
			FileRename.fileRename.setMsg("路径不存在");
			return;
		}
		try {
			Pattern.compile(matchFieldText);
		} catch (Exception e) {
			FileRename.fileRename.setMsg("正则式错误");
			return;
		}

		successCount = 0;
		failureCount = 0;
		totalCount = 0;

		if (isSelected_pre) { // 如果要进行预执行,则先删除之前生成的文件
			new File("FileRenamePre.txt").delete();
		}

		rename(rootDir);
		String msg = "成功:" + successCount + ";失败:" + failureCount + ";未匹配:"
				+ (totalCount - (successCount + failureCount)) + ";总:" + totalCount;
		FileRename.fileRename.setMsg(msg);
	}

	private void rename(File rootDir) {
		File[] files = rootDir.listFiles();
		HashMap<String, Integer> map = new HashMap<>();
		for (File file : files) {
			totalCount++;
			if (isSelected_dg) { // 进行递归操作
				if (file.isDirectory()) {
					rename(file);
				}
			}
			newFileName = targetFieldText; // 定义变量用于保存目标文件格式,在创建新文件名时会进行替换操作
			String newName = createNewFileName(file);
			if (newName != null) { // 创建新文件名,如果文件与正则表达式不匹配则返回null
				if (map.keySet().contains(newName)) { // 检验新文件名
					map.put(newName, map.get(newName) + 1);
					String extension = FileUtils.getExtension(new File(newName));
					if ("".equals(extension)) {
						newName = newName + "_" + map.get(newName);
					} else {
						newName = newName.replace("." + extension, "") + "_" + map.get(newName) + "." + extension;
					}
				} else {
					map.put(newName, 1);
				}
				String filePath = file.getAbsolutePath().replace(file.getName(), "");
				if (isSelected_pre) { // 是否进行预处理
					successCount++;
					String content = filePath + "  :  " + file.getName() + "    -->    " + newName + "\r\n";
					try {
						FileUtils.putContents(content, new File("FileRenamePre.txt"), true, "utf-8");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					if (file.renameTo(new File(filePath, newName))) {
						successCount++;
					} else {
						failureCount++;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	public String createNewFileName(File file) {
		Pattern pattern = Pattern.compile(matchFieldText); // 编译正则表达式
		Matcher matcher = pattern.matcher(file.getName()); // 匹配指定的字符串
		if (matcher.find()) {
			String[] strArr = new String[matcher.groupCount() + 1];
			for (int i = 0; i <= matcher.groupCount(); i++) {
				strArr[i] = matcher.group(i); // 将匹配的数据保存到数组
			}
			Matcher m2 = Pattern.compile("\\((\\d+)\\)").matcher(targetFieldText);
			while (m2.find()) {
				try {
					newFileName = newFileName.replace("(" + m2.group(1) + ")", strArr[Integer.parseInt(m2.group(1))]);
				} catch (ArrayIndexOutOfBoundsException e) {
					FileRename.fileRename.setMsg("索引越界");
					return null;
				}
			}
			return newFileName;
		}
		return null;
	}

}
