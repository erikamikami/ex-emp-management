package jp.co.sample.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.domain.SearchEmployee;
import jp.co.sample.form.SearchEmployeeForm;
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

	@ModelAttribute
	private SearchEmployeeForm setUpSearchEmployeeForm() {
		return new SearchEmployeeForm();
	}

	/**
	 * 従業員一覧の表示
	 * 
	 * @param model
	 * @return String
	 */
	@RequestMapping("/showList")
	public String showList(Model model, SearchEmployeeForm serchEmployeeForm) {
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
		Integer integerId = Integer.parseInt(id);
		Employee employee = employeeService.showDetail(integerId);
		model.addAttribute("employee", employee);
		return "/employee/detail";
	}

	/**
	 * 従業員情報の扶養人数のみ更新する.
	 *  ①. バリデーションチェック
	 *  ②. リクエストパラメータから送られてきたidをもとに、その従業員情報を確保する
	 *  ③. リクエストパラメータから送られてきた正しい扶養人数の情報を、①にセットする
	 *  ④. EmployeeServiceクラスのupdateメソッドで、更新をおこなう
	 *  ⑤. 従業員一覧にリダイレクトさせる
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
		String id = form.getId();
		Integer integerId = Integer.parseInt(id);
		Employee employee = employeeService.showDetail(integerId);

		String dependentsCount = form.getDependentsCount();
		Integer integerDependentsCount = Integer.parseInt(dependentsCount);

		employee.setDependentsCount(integerDependentsCount);

		employeeService.update(employee);

		return "redirect:/employee/showList";
	}

	/**
	 * 従業員の検索
	 * 
	 * @param searchEmployeeForm
	 * @return
	 */
	@RequestMapping("/serch")
	public String serch(SearchEmployeeForm searchEmployeeForm, Model model) {
		// あいまい検索するnameを取得する
		String name = searchEmployeeForm.getName();

		// hireDateFrom と hireDateTo を、String型からDate型に変換
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		String stringHireDateFrom = searchEmployeeForm.getHireDateFrom();
		String stringHireDateTo = searchEmployeeForm.getHireDateTo();

		try {
			Date hireDateFrom = sdFormat.parse(stringHireDateFrom);
			Date hireDateTo = sdFormat.parse(stringHireDateTo);
		} catch (ParseException e) {
			e.printStackTrace();
			model.addAttribute("serchHireDateErrorMessage", "※「yyyy-MM-dd」の形式で入力ください。");
			return "/employee/list";
		}

		// 扶養人数（dependentsCount）をString型からInteger型に変換
		Integer dependentsCount = Integer.parseInt(searchEmployeeForm.getDependentsCount());

		List<Employee> searchResultsEmployees = employeeService.search(name, stringHireDateFrom, stringHireDateTo,
				dependentsCount);
		model.addAttribute("searchResultsEmployees", searchResultsEmployees);
		return "/employee/list";
	}

}

