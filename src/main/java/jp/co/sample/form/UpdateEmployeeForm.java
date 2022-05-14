package jp.co.sample.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class UpdateEmployeeForm {
	/** 従業員ID （DBの方はInteger型） */
	private String id;

	/** 扶養人数 */
	@Pattern(regexp = "^[0-9]+$", message = "※半角数字で入力してください")
	private String dependentsCount;

	@Override
	public String toString() {
		return "UpdateEmployeeForm [id=" + id + ", dependentsCount=" + dependentsCount + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

}

