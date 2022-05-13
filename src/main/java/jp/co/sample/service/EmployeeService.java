package jp.co.sample.service;

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

}

