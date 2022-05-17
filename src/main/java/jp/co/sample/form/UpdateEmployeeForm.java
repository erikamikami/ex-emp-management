package jp.co.sample.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class UpdateEmployeeForm {
	/** 従業員ID （DBの方はInteger型） */
	private String id;

	/** 氏名 */
	@NotBlank(message = "※氏名は必須です")
	private String name;

	/** 性別 */
	@NotBlank(message = "※性別は必須です")
	private String gender;

	/** 入社日 */
	@NotBlank(message = "※入社日は必須です")
	@Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}+$", message = "※「YYYY-MM-DD」の形式で入力ください。（例）2007年4月1日入社の場合：2007-04-01")
	private String hireDate;

	/** メールアドレス */
	@NotBlank(message = "※メールアドレスは必須です")
	@Email(message = "※Eメール形式で入力ください")
	private String mailAddress;

	/** 郵便番号 */
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}+$", message = "※郵便番号の形式で入力ください。（例） 160-0000")
	private String zipCode;

	/** 住所 */
	@NotBlank(message = "※住所は必須です")
	private String address;

	/** 電話番号 */
	@NotBlank(message = "※電話番号は必須です")
	@Pattern(regexp = "^0\\d{1,4}-\\d{1,4}-\\d{3,4}$", message = "※ハイフンも含めた電話番号の形式で入力ください。（例） 090-1234-5678, 04-1234-5678")
	private String telephone;

	/** 給料 */
	@NotNull(message = "※給料は必須です")
	private Integer salary;

	/** 特性 */
	@NotBlank(message = "※特性は必須です")
	private String characteristics;

	/** 扶養人数 */
	@NotBlank(message = "※扶養人数は必須です")
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

