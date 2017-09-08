package com.example.v1.main;

import com.example.v1.ui.UI;

public class FileRename {
	public static FileRename fileRename;
	public UI ui;
	
	public static void main(String[] args) {
		fileRename=new FileRename();
		fileRename.ui=new UI();
	}
}
