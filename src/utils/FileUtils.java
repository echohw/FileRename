package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 文件工具类：用于操作文件
 * @author 燃
 */
public class FileUtils {
	private FileUtils() {
	}

	/**
	 * 向文件写入内容
	 * @param content 要写入的内容
	 * @param filePath 文件路径
	 * @throws IOException
	 */
	public static void putContents(String content, String filePath) throws IOException {
		putContents(content, new File(filePath));
	}

	/**
	 * 向文件写入内容
	 * @param content 要写入的内容
	 * @param file 文件对象
	 * @throws IOException
	 */
	public static void putContents(String content, File file) throws IOException {
		putContents(content, file, false);
	}

	/**
	 * 向文件写入内容
	 * @param content 要写入的内容
	 * @param file 文件对象
	 * @param append 是否使用追加模式
	 * @throws IOException
	 */
	public static void putContents(String content, File file, boolean append) throws IOException {
		putContents(content, file, append, "utf-8");
	}

	/**
	 * 向文件写入内容
	 * @param content 要写入的内容
	 * @param file 文件对象
	 * @param append 是否使用追加模式
	 * @param charset 文件编码
	 * @throws IOException
	 */
	public static void putContents(String content, File file, boolean append, String charset) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file, append), charset);
		osw.write(content);
		osw.close();
	}
	
	/**
	 * 获取不包含"."的文件后缀名(扩展名),如果没有后缀名则返回空串
	 * @param file
	 * @return
	 */
	public static String getExtension(File file) {
		String fileName = file.getName();
		int lastIndex = fileName.lastIndexOf(".");
		return lastIndex == -1 ? "" : fileName.substring(lastIndex + 1);
	}
}
