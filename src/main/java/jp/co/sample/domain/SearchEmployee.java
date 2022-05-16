package jp.co.sample.domain;

import java.util.Date;

public class SearchEmployee {
	/** 名前 */
	private String name;

	/** 入社日（開始日） */
	private Date hireDateFrom;

	/** 入社日（終了日） */
	private Date hireDateTo;

	/** 扶養人数 */
	private Integer dependentsCount;

	@Override
	public String toString() {
		return "SearchEmployee [name=" + name + ", hireDateFrom=" + hireDateFrom + ", hireDateTo=" + hireDateTo
				+ ", dependentsCount=" + dependentsCount + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getHireDateFrom() {
		return hireDateFrom;
	}

	public void setHireDateFrom(Date hireDateFrom) {
		this.hireDateFrom = hireDateFrom;
	}

	public Date getHireDateTo() {
		return hireDateTo;
	}

	public void setHireDateTo(Date hireDateTo) {
		this.hireDateTo = hireDateTo;
	}

	public Integer getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(Integer dependentsCount) {
		this.dependentsCount = dependentsCount;
	}
}
