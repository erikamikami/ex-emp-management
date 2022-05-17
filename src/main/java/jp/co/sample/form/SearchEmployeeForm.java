package jp.co.sample.form;

public class SearchEmployeeForm {

	/** 氏名（あいまい検索） */
	private String name;

	/** 入社日（開始日） */
	private String hireDateFrom;

	/** 入社日（終了日） */
	private String hireDateTo;

	/** 扶養人数 */
	private String dependentsCount;

	@Override
	public String toString() {
		return "SearchEmployeeForm [name=" + name + ", hireDateFrom=" + hireDateFrom + ", hireDateTo=" + hireDateTo
				+ ", dependentsCount=" + dependentsCount + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHireDateFrom() {
		return hireDateFrom;
	}

	public void setHireDateFrom(String hireDateFrom) {
		this.hireDateFrom = hireDateFrom;
	}

	public String getHireDateTo() {
		return hireDateTo;
	}

	public void setHireDateTo(String hireDateTo) {
		this.hireDateTo = hireDateTo;
	}

	public String getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}
}

