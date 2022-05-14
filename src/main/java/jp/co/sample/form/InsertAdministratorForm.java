package jp.co.sample.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class InsertAdministratorForm {
	/** 名前 */
	@NotBlank(message = "※氏名は必須です")
	private String name;

	/** メールアドレス */
	@Email(message = "※Eメール形式で入力ください")
	@NotBlank(message = "※メールアドレスは必須です")
	private String mailAddress;

	/** パスワード */
	@NotBlank(message = "※パスワードは必須です")
	@Pattern(regexp = "/^[a-zA-Z0-9]+$/", message = "※パスワードは半角英数字で入力ください")
	@Size(min = 8, message = "※パスワードは8文字以上で設定してください")
	private String password;

	@Override
	public String toString() {
		return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
