package kfk.main;

import java.util.ArrayList;

import kfk.http.inf.HttpRequester;
import kfk.http.inf.HttpResponse;
import kfk.java.bean.ActiveUserBean;
import kfk.java.bean.CrashBean;
import kfk.java.bean.DayBean;
import kfk.out.excel.ExportExcel;
import kfk.parse.csv.CrashCategoryParse;
import kfk.parse.csv.CrashParse;
import kfk.parse.json.ActiveUserPrase;

public class FADemo {
	// 4.0   KQSV5WXHB4TCMVC4D3GX
	// 4.0.3 J85WBFF7DNYH3N59RGKW
	// 4.0.5 CWV3S4TYHR93Z9TYPWR9
	 String apiKey = "KQSV5WXHB4TCMVC4D3GX";
	 String starDate = "2014-08-07";
	 String endDate = "2014-08-07";
	 String version = "4.0.3";
	 String interval = "2014_08_07-2014_08_08";
	 String[][] files = new String[100][10];
	 ActiveUserBean au;

	public static void main(String args[]) {
		FADemo fa=new FADemo();
		ExportExcel excel =new ExportExcel();
		fa.BugRate();
		fa.Crash();
		excel.makeBugRate(fa.files, fa.au);
	}

	/**
	 * HTTP 请求ActiveUser
	 * 
	 * @author kfk
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void BugRate() {
		String flurryApi = "http://api.flurry.com/appMetrics/ActiveUsers?apiAccessCode=QBV2BBZ7HBF3PJXTK97F&apiKey="
				+ apiKey
				+ "&startDate="
				+ starDate
				+ "&endDate="
				+ endDate
				+ "&versionName=" + version;
		String csvURL = "C:\\Users\\kl\\Downloads\\1MobileMarket_"+version+"-["+interval+"]-crashes.csv";
		try {
			HttpRequester request = new HttpRequester();
			HttpResponse response = request.sendGet(flurryApi);
			ActiveUserPrase activerUser = new ActiveUserPrase();
			au = new ActiveUserBean();
			ArrayList<DayBean> auList = (ArrayList<DayBean>) activerUser
					.ActiveUserList(response, au);
			/**
			 * Function:获取Bug发生率，从上到下依次的依次是： 获取Active User数， 获取Total Crashes数，
			 * 计算Bug发生率
			 * 
			 * @author kfk
			 */
			if (auList != null) {
				System.out.println("ApplicationVersion:" + au.getVersionName()
						+ " StartDate:" + au.getStartDate() + " EndDate:"
						+ au.getEndDate());
				System.out.println("Date" + "	" + "ActiveUser" + "	"
						+ "Crashes" + "	" + "BugRate");
				String temp = new String("ApplicationVersion:"
						+ au.getVersionName() + "	" + "StartDate:"
						+ au.getStartDate() + "	" + "EndDate:"
						+ au.getEndDate());
				files[0] = temp.split("	");
				temp = new String("Date" + "	" + "ActiveUser" + "	" + "Crashes"
						+ "	" + "BugRate");
				files[1] = temp.split("	");
				for (int i = 0; i < auList.size(); i++) {
					CrashParse Crash = new CrashParse();
					ArrayList<CrashBean> totalCrashList = (ArrayList<CrashBean>) Crash
							.crashList(csvURL);
					if (totalCrashList != null) {
						for (int j = 1; j < totalCrashList.size(); j++) {
							if (auList.get(i).getDate()
									.equals(totalCrashList.get(j).getDate())) {
								double BugRate = Double
										.parseDouble(totalCrashList.get(j)
												.getCrash())
										/ Double.parseDouble(auList.get(i)
												.getValue()) * 100;
								System.out.println(auList.get(i).getDate()
										+ "	" + auList.get(i).getValue() + "	"
										+ totalCrashList.get(j).getCrash()
										+ "	" + Math.round(BugRate * 1000)
										/ 1000.0);
								 temp = new String(auList.get(i).getDate()+"	"
								 +auList.get(i).getValue()+"	"
								 +totalCrashList.get(j).getCrash()+"	"
								 +Math.round(BugRate * 1000) / 1000.0);
								 files[i+2] =temp.split("	");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function：解析每天的Crash数量
	 * 
	 * @author kfk
	 */
	@SuppressWarnings("unchecked")
	public void Crash() {
		try {
			String crashDir = "C:\\Users\\kl\\Downloads\\1MobileMarket_"+version+"-["+interval+"]-topCrashesByStackFingerprint.csv";
			CrashCategoryParse crashCategory = new CrashCategoryParse();
			ArrayList<CrashBean> crashCategoryList = (ArrayList<CrashBean>) crashCategory.crashList(crashDir);
			ArrayList<String> flag = new ArrayList<String>();
			double former, latter;
			System.out.println("CrashName" + "	" + " Crashe");
			String temp = new String("CrashName" + "	" + " Crashe");
			files[3] = temp.split("	");
			for (int i = 1; i < crashCategoryList.size(); i++) {
				boolean swith = false;
				former = Double
						.parseDouble(crashCategoryList.get(i).getCrash());
				for (int j = i + 1; j < crashCategoryList.size(); j++) {
					for (int k = 0; k < flag.size(); k++) {
						if (flag.get(k).equals(
								crashCategoryList.get(i).getCrashName())) {
							swith = true;
							break;
						}
					}
					if (swith)
						break;
					if (crashCategoryList.get(i).getCrashName()
							.equals(crashCategoryList.get(j).getCrashName())) {
						latter = Double.parseDouble(crashCategoryList.get(j)
								.getCrash());
						former += latter;
					}
				}
				if (swith)
					continue;
				flag.add(crashCategoryList.get(i).getCrashName());
				System.out.println(crashCategoryList.get(i).getCrashName()
						+ "	" + former);
				temp = new String(crashCategoryList.get(i).getCrashName() + "	"
						+ former);
				files[flag.size()+3] = temp.split("	");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
