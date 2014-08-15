package kfk.java.bean;

public class ActiveUserBean {
	
	private String endDate;
	private String metric;
	private String startDate;
	private String versionName;
	private String generatedDate;
	private String date;
	private String value;
	private String activeUser;
	
	public String getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	public String getDate() {
		// TODO Auto-generated method stub
		return date;
	}
	public void setValue(String value) {
		// TODO Auto-generated method stub
		this.value = value;
	}

	public void setDate(String date) {
		// TODO Auto-generated method stub
		this.date = date;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(String activeUser) {
		this.activeUser = activeUser;
	}

}
