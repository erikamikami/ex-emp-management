package jp.co.sample.form;

import javax.validation.constraints.Pattern;

public class UpdateEmployeeForm {
	/** 従業員ID （DBの方はInteger型） */
	private String id;

	/** 氏名 */
	private String name;

	/** 性別 */
	private String gender;

	/** 雇用開始日 */
	private String hireDate;

	/** メールアドレス */
	private String mailAddress;

	/** 郵便番号 */
	private String zipCode;

	/** 住所 */
	private String address;

	/** 電話番号 */
	private String telephone;

	/** 給料 */
	private Integer salary;

	/** 特性 */
	private String characteristics;

	/** 扶養人数 */
	@Pattern(regexp = "^[0-9]+$", message = "※半角数字で入力してください")
	private String dependentsCount;

	@Override
	public String toString() {
		return "UpdateEmployeeForm [id=" + id + ", name=" + name + ", gender=" + gender + ", hireDate=" + hireDate
				+ ", mailAddress=" + mailAddress + ", zipCode=" + zipCode + ", address=" + address + ", telephone="
				+ telephone + ", salary=" + salary + ", characteristics=" + characteristics + ", dependentsCount="
				+ dependentsCount + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public String getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}


}

