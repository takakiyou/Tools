package com.chris.log.analytics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HomeLoadFilter implements TypeFilter {

	public static void main(String[] args) {
		try {
			File dstFile = new File("res/20140820.log");
			if (dstFile.exists()) {
				dstFile.delete();
			}
			dstFile.createNewFile();

			File logFile = new File("log/statapp_raw_20140820.log");
			BufferedReader br = new BufferedReader(new FileReader(logFile));
			BufferedWriter bw = new BufferedWriter(new FileWriter(dstFile));

			int totalCount = 0;
			int availableCount = 0;
			String line = null;

			Set<String> uniqueSet = new HashSet<String>();

			String uid = "";
			while ((line = br.readLine()) != null) {
				++totalCount;

				if (line != null && line.length() > 0 && line.indexOf("home_load") >= 0) {
					final String[] columns = line.split("\\t");
					final String paramsStr = columns[3];
					String[] params = paramsStr.split("&");

					for (String param : params) {
						if (param.indexOf("uid") >= 0) {
							String[] uidPair = param.split("=");
							uid = uidPair[1];
							break;
						}
					}

					String json = columns[4];
					
					if (uniqueSet.contains(line)) {
						continue;
					} else {
						uniqueSet.add(line);
						
						JSONObject jObject = JSONObject.fromObject(json);
						JSONArray jArray = jObject.optJSONArray("analyticsData");
						
						for (int i = 0; i < jArray.size(); i++) {
							String log = jArray.optString(i);
							if (log.indexOf("home_load") >= 0) {
								++availableCount;
								
								String[] logs = log.split("&");
								bw.append("uid=" + uid + "&" + log + "\n");
								bw.flush();
								continue;
							}
						}
					}
				}
			}
			br.close();
			bw.close();
			System.out.println("totalCount : " + totalCount + " , availableCount : " + availableCount);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
