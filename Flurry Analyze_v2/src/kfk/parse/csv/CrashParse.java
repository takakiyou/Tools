package kfk.parse.csv;

import java.util.ArrayList;

import kfk.java.bean.CrashBean;

/**
 * CSV ½âÎöÀý×Ó
 */
public class CrashParse {

	ArrayList<CrashBean> valueList = new ArrayList<CrashBean>();

	public ArrayList<?> crashList(String parURL) {
		try {
			CSVFileUtil csv = new CSVFileUtil(parURL);
			if (csv != null) {
				while (true) {
					String csvLine = csv.readLine();
					String[] csvBuf = null;
					if (csvLine != null)
						csvBuf = csvLine.split(",");
					else
						break;
					CrashBean cf = new CrashBean();
					cf.setDate(csvBuf[0]);
					cf.setCrash(csvBuf[1]);
					valueList.add(cf);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueList;
	}

}
