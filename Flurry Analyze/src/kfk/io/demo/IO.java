package kfk.io.demo;

import java.io.*;
import java.util.Date;

import kfk.java.bean.ActiveUserBean;

public class IO {

	Date date = new Date();
	String fileName = "CrashRate_" + date.getTime() + ".txt";
	String dir = "D:\\BugRate\\txt\\";

	public IO(ActiveUserBean au) {
		dir = "D:\\BugRate\\txt\\" + au.getVersionName() + "\\";
	}

	public void save(String file) throws Exception {
		File filedir = new File(dir);
		if (!filedir.isDirectory())
			filedir.mkdir();
		Writer w = new FileWriter(dir + fileName, true);
		BufferedWriter bw = new BufferedWriter(w);
		bw.write(file);
		bw.newLine();
		bw.close();
	}
}