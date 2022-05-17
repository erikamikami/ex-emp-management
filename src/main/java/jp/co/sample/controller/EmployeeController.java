package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
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
		Integer integerId = Integer.parseInt(id);
		Employee employee = employeeService.showDetail(integerId);
		model.addAttribute("employee", employee);
		return "/employee/detail";
	}

	/**
	 * 従業員情報の扶養人数のみ更新する.
	 *  ①. リクエストパラメータから送られてきたidをもとに、その従業員情報を確保する
	 *  ②. リクエストパラメータから送られてきた正しい扶養人数の情報を、①にセットする
	 *  ③. EmployeeServiceクラスのupdateメソッドで、更新をおこなう
	 *  ④. 従業員一覧にリダイレクトさせる
	 * @param form
	 * @return String
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		String id = form.getId();
		Integer integerId = Integer.parseInt(id);
		Employee employee = employeeService.showDetail(integerId);

		String dependentsCount = form.getDependentsCount();
		Integer integerDependentsCount = Integer.parseInt(dependentsCount);

		System.out.println("integerDependentsCountは、" + integerDependentsCount);

		employee.setDependentsCount(integerDependentsCount);

		employeeService.update(employee);

		return "redirect:/employee/showList";
	}

}

