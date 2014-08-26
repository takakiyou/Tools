package kfk.parse.json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.*;

public class JasonPrase {

//	public static String getString(String json) throws JSONException {
//		JSONObject root = new JSONObject(json);
//		String log = root.getString("analyticsData");
//		return log;
//	}
	public static void main(String[] args) throws IOException, JSONException {
//		File dstFile = new File("20140820.log");
		File dstFile = new File("20140820_home_load.log");
		System.out.println("Go!");
		if (!dstFile.exists()) {
			dstFile.createNewFile();
		}
//		File logFile = new File("/Users/ThomasKe/Desktop/statapp_raw_20140820.log");
		File logFile = new File("20140820.log");
		BufferedReader br = new BufferedReader(new FileReader(logFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(dstFile));

		String line = null;
		while ((line = br.readLine()) != null) {
			if (line != null && line.length() > 0 && line.indexOf("home_load") > 0) {
				String tmp1[] = line.split("\\{");
				String tmp3[] = tmp1[1].split("\\[");
				String tmp2[] = tmp3[1].split(",");
				for(int i=0; i< tmp2.length; i++) {
					if(tmp2[i].indexOf("home_load")>0)
//						for(int j=0; j<tmp2[i].length();j++)
//							if()
						tmp1[0] += tmp2[i]+"	";
				}
				line = tmp1[0];
				bw.append(line + "\n");
				bw.flush();
			}
		}
		br.close();
		bw.close();
		System.out.println("completed!");
	}
}