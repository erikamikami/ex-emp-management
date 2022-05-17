package jp.co.sample.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import jp.co.sample.form.SearchEmployeeForm;
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
	public String showList(SearchEmployeeForm serchEmployeeForm, Model model) {
		if (session.getAttribute("administratorName") == null) {
			return "administrator/login";
		}
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
		if (session.getAttribute("administratorName") == null) {
			return "administrator/login";
		}
		Integer integerId = Integer.parseInt(id);
		Employee employee = employeeService.showDetail(integerId);
		model.addAttribute("employee", employee);
		return "/employee/detail";
	}

	/**
	 * 従業員情報を更新する. ①. バリデーションチェック ②. idをもとに、その従業員情報を確保する ③.
	 * EmployeeServiceクラスのupdateメソッドで、更新をおこなう ④. 従業員一覧にリダイレクトさせる
	 * 
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
		// インポートはjava.sql.Date. java.util.Dateを使う際は、FQCNで指定する
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		String oldHireDate = form.getHireDate();
		try {
			java.util.Date hireDate = sdFormat.parse(oldHireDate);
			employee.setHireDate(hireDate);
		} catch (ParseException e) {
			e.printStackTrace();
			model.addAttribute("hireDateErrorMessage", "※「yyyy-MM-dd」の形式で入力ください。\n（例）2007年04月01日");
			return "/employee/detail";
		}

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

		// 扶養人数（dependentsCount）をString型からInteger型に変換
		Integer dependentsCount = null;
		if (!(searchEmployeeForm.getDependentsCount().equals(""))) {
			dependentsCount = Integer.parseInt(searchEmployeeForm.getDependentsCount());
		}

		// hireDateFrom と hireDateTo を、String型からjava.sql.Date型に変換
		Date hireDateFrom = null;
		Date hireDateTo = null;

		try {
			hireDateFrom = Date.valueOf(searchEmployeeForm.getHireDateFrom());
		} catch (IllegalArgumentException e) {
			hireDateFrom = Date.valueOf("1900-01-01"); // 検索フォームからのパラメータが空でもnullで扱いたくないため、1900年1月1日をセットする（1900年1月1日以前に入社している人は存在しないため）
		}

		try {
			hireDateTo = Date.valueOf(searchEmployeeForm.getHireDateTo());
		} catch (IllegalArgumentException e) {
			hireDateTo = Date.valueOf("9999-12-30"); // 検索フォームからのパラメータが空でもnullで扱いたくないため、9999年12月30日をセットする（9999年12月30日以降まで入社している人はおそらくこれからも存在しないため）
		}

		List<Employee> searchResultsEmployees = employeeService.search(name, hireDateFrom, hireDateTo, dependentsCount);
		model.addAttribute("searchResultsEmployees", searchResultsEmployees);

		// 検索結果が0だった場合、その旨表示
		if (searchResultsEmployees.size() == 0) {
			model.addAttribute("searchResults", "※検索結果は0です");
			return "/employee/list";
		}

		return "/employee/list";

	}

}
