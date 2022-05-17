package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private AdministratorService service;

	@ModelAttribute
	InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}

	@ModelAttribute
	LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	@Autowired
	private HttpSession session;

	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}

	/**
	 * 管理者情報を登録する. その後ログイン画面にリダイレクトする
	 * 
	 * @param insertAdministratorForm
	 * @return String
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm insertAdministratorForm) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(insertAdministratorForm, administrator);
		service.insert(administrator);
		return "redirect:/";
	}

	/**
	 * administrator/login.html にフォワードし、ログイン画面に遷移させる.
	 * 
	 * @return String
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}

	/**
	 * ログイン判定を行う処理. serviceのloginメソッドの結果がnullの場合：ログインエラーメッセージを表示し、ログイン画面にフォワードする
	 * serviceのloginメソッドの結果がnullでなかった場合：ログインエラーメッセージを表示し、従業員情報一覧ページにフォワードする
	 * 
	 * @param loginForm
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(LoginForm loginForm, Model model) {
		String mailAddress = loginForm.getMailAddress();
		String password = loginForm.getPassword();
		Administrator administrator = service.login(mailAddress, password);

		if (administrator == null) {
			model.addAttribute("loginErrorMessage", "メールアドレスまたはパスワードが不正です。");
			return "forward:/";
		} else {
			String administratorName = administrator.getName();
			session.setAttribute("administratorName", administratorName);
			return "forward:/employee/showList";
		}
	}

}
