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

	public FADemo(ActiveUserBean au) {
		String[][] container = new String[100][10];
		String apiKey = au.getApiKey();
		String starDate = au.getStartDate();
		String endDate = au.getEndDate();
		String version = au.getVersionName();
		String interval = au.getInterval();
		String inputDir = au.getInputDir();
		BugRate(container, au, apiKey, starDate, endDate, version, interval, inputDir);
		Crash(container, version, interval, inputDir);
		ExportExcel excel = new ExportExcel();
		excel.makeBugRate(container, au);
	}

	/**
	 * 
	 * @param container
	 * @param activeUserBean
	 * @param apiKey
	 * @param starDate
	 * @param endDate
	 * @param version
	 * @param interval
	 * @param inputDir
	 */
	@SuppressWarnings("unchecked")
	public void BugRate(String[][] container, ActiveUserBean activeUserBean,
			String apiKey, String starDate, String endDate, String version,
			String interval, String inputDir) {
		String flurryApi = "http://api.flurry.com/appMetrics/ActiveUsers?apiAccessCode=QBV2BBZ7HBF3PJXTK97F&apiKey="
				+ apiKey
				+ "&startDate="
				+ starDate
				+ "&endDate="
				+ endDate
				+ "&versionName=";
		String csvURL = inputDir+"1MobileMarket_" + version
				+ "-[" + interval + "]-crashes.csv";
		try {
			HttpRequester request = new HttpRequester();
			HttpResponse response = request.sendGet(flurryApi);
			ActiveUserPrase activerUser = new ActiveUserPrase();
			ArrayList<DayBean> auList = (ArrayList<DayBean>) activerUser
					.ActiveUserList(response, activeUserBean);
			/**
			 * Function:获取Bug发生率，从上到下依次的依次是： 获取Active User数， 获取Total Crashes数，
			 * 计算Bug发生率
			 * 
			 * @author kfk
			 */
			if (auList != null) {
				System.out.println("ApplicationVersion:"
						+ activeUserBean.getVersionName() + " StartDate:"
						+ activeUserBean.getStartDate() + " EndDate:"
						+ activeUserBean.getEndDate());
				System.out.println("Date" + "	" + "ActiveUser" + "	"
						+ "Crashes" + "	" + "BugRate");
				String temp = new String("ApplicationVersion:"
						+ activeUserBean.getVersionName() + "	" + "StartDate:"
						+ activeUserBean.getStartDate() + "	" + "EndDate:"
						+ activeUserBean.getEndDate());
				container[0] = temp.split("	");
				temp = new String("Date" + "	" + "ActiveUser" + "	" + "Crashes"
						+ "	" + "BugRate");
				container[1] = temp.split("	");
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
								temp = new String(auList.get(i).getDate() + "	"
										+ auList.get(i).getValue() + "	"
										+ totalCrashList.get(j).getCrash()
										+ "	" + Math.round(BugRate * 1000)
										/ 1000.0);
								container[i + 2] = temp.split("	");
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
	 * @param container
	 * @param version
	 * @param interval
	 */
	@SuppressWarnings("unchecked")
	public void Crash(String[][] container, String version, String interval, String inputDir) {
		try {
			String crashDir = inputDir+"1MobileMarket_"
					+ version + "-[" + interval
					+ "]-topCrashesByStackFingerprint.csv";
			CrashCategoryParse crashCategory = new CrashCategoryParse();
			ArrayList<CrashBean> crashCategoryList = (ArrayList<CrashBean>) crashCategory
					.crashList(crashDir);
			ArrayList<String> crashContainer = new ArrayList<String>();
			double former, latter;
			System.out.println("CrashName" + "	" + " Crashe");
			String temp = new String("CrashName" + "	" + " Crashe");
			container[3] = temp.split("	");
			for (int i = 1; i < crashCategoryList.size(); i++) {
				boolean swith = false;
				former = Double
						.parseDouble(crashCategoryList.get(i).getCrash());
				for (int j = i + 1; j <= crashCategoryList.size(); j++) {
					for (int k = 0; k < crashContainer.size(); k++) {
						if (crashContainer.get(k).equals(
								crashCategoryList.get(i).getCrashName())) {
							swith = true;
							break;
						}

					}
					if (swith)
						break;
					if (j < crashCategoryList.size()
							&& crashCategoryList
									.get(i)
									.getCrashName()
									.equals(crashCategoryList.get(j)
											.getCrashName())) {
						latter = Double.parseDouble(crashCategoryList.get(j)
								.getCrash());
						former += latter;
					}
				}
				if (swith)
					continue;
				crashContainer.add(crashCategoryList.get(i).getCrashName());
				System.out.println(crashCategoryList.get(i).getCrashName()
						+ "	" + former);
				temp = new String(crashCategoryList.get(i).getCrashName() + "	"
						+ former);
				container[crashContainer.size() + 3] = temp.split("	");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
