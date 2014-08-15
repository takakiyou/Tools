/**
 * Function:解析当天的某种Crash的数量
 * @return crashList
 */
package kfk.parse.csv;

import java.util.ArrayList;

import kfk.java.bean.CrashBean;

public class CrashCategoryParse {
	ArrayList<CrashBean> crashList = new ArrayList<CrashBean>();

	public ArrayList<?> crashList(String parURL) {
		try {
			CSVFileUtil csv = new CSVFileUtil(parURL);
			while (true) {
				String csvLine = csv.readLine();
				String[] csvBuf = null;
				if (csvLine != null)
					csvBuf = csvLine.split(",");
				else
					break;
				CrashBean cf = new CrashBean();
				cf.setCrashName(csvBuf[0]);
				cf.setCrash(csvBuf[1]);
				crashList.add(cf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return crashList;
	}

}
