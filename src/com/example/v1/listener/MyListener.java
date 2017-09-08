package com.example.v1.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.v1.main.FileRename;
import com.example.v1.ui.UI;

public class MyListener implements ActionListener {
	private String filesDir;
	private String matchFieldStr;
	private String targetFieldStr;
	private String newFileName;
	private UI ui;

	@Override
	public void actionPerformed(ActionEvent e) {
		ui = FileRename.fileRename.ui;
		filesDir = ui.filePathField.getText().toString();
		matchFieldStr = ui.matchField.getText().toString();
		targetFieldStr = ui.targetField.getText().toString();
		rename();
	}

	private void rename() {
		if (filesDir.equals("") || matchFieldStr.equals("") || targetFieldStr.equals("")) {
			ui.setMsg("文本框内容不能为空");
			return;
		}
		File fileDir = new File(filesDir);
		if (!fileDir.exists()) {
			ui.setMsg("路径不存在");
			return;
		}

		try {
			Pattern.compile(matchFieldStr);
		} catch (Exception e) {
			ui.setMsg("正则式错误");
			return;
		}
		File[] files = fileDir.listFiles();
		int successCount=0,failureCount=0;
		for (File f : files) {
			newFileName = targetFieldStr;
			String newName = createNewFileName(f);
			if (newName == null) { //不符合正则表达式的文件或出现异常的情况
				continue;
			}
			if(f.renameTo(new File(filesDir, newName))){
				successCount++;
			}else{
				failureCount++;
			}
		}
		ui.setMsg("成功:"+successCount+";失败:"+failureCount+";未匹配:"+(files.length-(successCount+failureCount))+";总:"+files.length);
	}

	public String createNewFileName(File file) {
		Pattern r = Pattern.compile(matchFieldStr);
		Matcher m = r.matcher(file.getName());
		if (m.find()) {
			String[] strArr = new String[m.groupCount() + 1];
			for (int i = 0; i <= m.groupCount(); i++) {
				strArr[i] = m.group(i); // 将匹配的数据保存到数组
			}
			Matcher m2 = Pattern.compile("\\((\\d+)\\)").matcher(targetFieldStr);
			while (m2.find()) {
				try {
					newFileName = newFileName.replace("(" + m2.group(1) + ")", strArr[Integer.parseInt(m2.group(1))]);
				} catch (ArrayIndexOutOfBoundsException e) {
					ui.setMsg("索引越界");
					return null;
				}
			}
			return newFileName;
		}
		return null;
	}

}
