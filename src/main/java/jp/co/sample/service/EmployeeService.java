package jp.co.sample.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 従業員情報を全件取得する.
	 * 
	 * @return List<Employee>
	 */
	public List<Employee> showList() {
		return employeeRepository.findAll();
	}

	/**
	 * idをもとに、従業員情報（1人分）取得する.
	 * 
	 * @param id
	 * @return Employee
	 */
	public Employee showDetail(Integer id) {
		return employeeRepository.load(id);
	}

	/**
	 * 従業員情報を更新する.
	 * 
	 * @param employee
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}

	/**
	 * 従業員一覧に検索フォームを追加 ＜仕様＞ 名前のあいまい検索 入社日 開始日～終了日（期間で検索） 扶養人数 〇人以上 で検索
	 * 
	 * 検索値がすべてブランクだった場合は全件検索する
	 * 
	 * @param name
	 * @param hireDateFrom
	 * @param hireDateTo
	 * @param dependentsCount
	 * @return List<Employee>
	 */
	public List<Employee> search(String name, String hireDateFrom, String hireDateTo, Integer dependentsCount) {
		List<Employee> employees = new ArrayList<>();

		// 検索値がすべてブランクだった場合は全件検索する
		if ("".equals(name) && "".equals(hireDateFrom) && "".equals(hireDateTo)) {
			employees = employeeRepository.findAll();
		} else {
			// 上記以外の場合、EmployeeRepositoryのsearchメソッドを呼び出す
			employees = employeeRepository.search(name, hireDateFrom, hireDateTo, dependentsCount);
		}
		return employees;

	}

}
