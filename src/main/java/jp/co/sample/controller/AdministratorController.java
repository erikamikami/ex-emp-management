package jp.co.sample.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

}
