package kfk.parse.json;

import java.util.ArrayList;

import kfk.http.inf.HttpResponse;
import kfk.java.bean.ActiveUserBean;
import kfk.java.bean.DayBean;
import org.json.*;

public class ActiveUserPrase {
	ArrayList<DayBean> dayList = new ArrayList<DayBean>();

	/**
	 * Ω‚ŒˆJSON
	 * 
	 * @throws JSONException
	 */
	public ArrayList<?> ActiveUserList(HttpResponse response, ActiveUserBean au)
			throws JSONException {
		JSONObject json = new JSONObject(response.contentCollection.get(0));
		au.setVersionName(json.optString("@versionName"));
		if (json.optString("@versionName").isEmpty())
			au.setVersionName("AllVersion");
		au.setStartDate(json.optString("@startDate"));
		au.setEndDate(json.optString("@endDate"));
		JSONArray jsonAry = json.optJSONArray("day");
		if (jsonAry != null) {
			for (int i = 0; i < jsonAry.length(); i++) {
				JSONObject dayJson = jsonAry.optJSONObject(i);
				DayBean day = new DayBean();
				day.setDate(dayJson.optString("@date"));
				day.setValue(dayJson.optString("@value"));
				dayList.add(day);
			}
		} else {
			JSONObject onedayjson = json.getJSONObject("day");
			DayBean day = new DayBean();
			day.setDate(onedayjson.optString("@date"));
			day.setValue(onedayjson.optString("@value"));
			dayList.add(day);
		}
		return dayList;
	}

}