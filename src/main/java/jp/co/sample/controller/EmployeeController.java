package jp.co.sample.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private HttpSession session;

	@Autowired
	private AdministratorController administratorController;

	@ModelAttribute
	private UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}

	/**
	 * 従業員一覧の表示
	 * 
	 * @param model
	 * @return String
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		if (session.getAttribute("administratorName") == null) {
			return "administrator/login";
		}
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		return "/employee/list";
	}

	/**
	 * 従業員詳細情報の表示.
	 * 
	 * @param id
	 * @param model
	 * @return String
	 */
	@RequestMapping("/showDetail")
	public String showDertail(String id, Model model) {
		if (session.getAttribute("administratorName") == null) {
			return "administrator/login";
		}
		Integer integerId = Integer.parseInt(id);
		Employee employee = employeeService.showDetail(integerId);
		model.addAttribute("employee", employee);
		return "/employee/detail";
	}

	/**
	 * 従業員情報を更新する.
	 *  ①. バリデーションチェック
	 *  ②. idをもとに、その従業員情報を確保する
	 *  ③. EmployeeServiceクラスのupdateメソッドで、更新をおこなう
	 *  ④. 従業員一覧にリダイレクトさせる
	 * @param form
	 * @return String
	 */
	@RequestMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			String id = form.getId();
			Integer integerId = Integer.parseInt(id);
			Employee employee = employeeService.showDetail(integerId);
			model.addAttribute("employee", employee);
			return "/employee/detail";
		}

		// リクエストパラメータから送られてきたidをもとに、その従業員情報を確保する
		String id = form.getId();
		Integer integerId = Integer.parseInt(id);
		Employee employee = employeeService.showDetail(integerId);

		BeanUtils.copyProperties(form, employee);

		// idを、String型からInteger型に変換
		employee.setId(integerId);

		// hireDateを、String型からjava.util.Date型に変換
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		String oldHireDate = form.getHireDate();
		try {
			Date hireDate = sdFormat.parse(oldHireDate);
			employee.setHireDate(hireDate);
		} catch (ParseException e) {
			e.printStackTrace();
			model.addAttribute("hireDateErrorMessage", "※「yyyy-MM-dd」の形式で入力ください。\n（例）2007年04月01日");
			return "/employee/detail";
		}

		employeeService.update(employee);

		return "redirect:/employee/showList";
	}

}

