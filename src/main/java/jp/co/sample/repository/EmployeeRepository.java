package jp.co.sample.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * @author erika
 *
 */
@Repository
public class EmployeeRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * Employeeオブジェクトを生成するRowMapper
	 *
	 */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		// HireDateの取り方がこれであっているのか気になる...
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};

	/**
	 * 従業員の一覧情報を入社日順（降順）で取得する. 従業員が存在しない場合は、サイズ0の従業員一覧を返す
	 * 
	 * @return List<Employee>
	 */
	public List<Employee> findAll() {
		String sql = "SELECT * FROM employees ORDER BY hire_date DESC";
		List<Employee> employees = template.query(sql, EMPLOYEE_ROW_MAPPER);
		return employees;
	}

	/**
	 * 主キーから従業員情報を取得する. 従業員が存在しない場合は、NullPointerExceptionの例外が発生する
	 * 
	 * @param id
	 * @return Employee
	 */
	public Employee load(Integer id) {
		String sql = "SELECT * FROM employees WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
	}
	
	/**
	 * 従業員情報を変更する.
	 * idカラムを除いた従業員情報全てのカラムを更新する
	 * @param employee
	 */
	public void update(Employee employee) {
		String sql = "UPDATE employees SET "
				+ "name=:name"
				+ ",image=:image"
				+ ",gender=:gender"
				+ ",hire_date=:hireDate"
				+ ",mail_address=:mailAddress"
				+ ",zip_code=:zipCode"
				+ ",address=:address"
				+ ",telephone=:telephone"
				+ ",salary=:salary"
				+ ",characteristics=:characteristics"
				+ ",dependents_count=:dependentsCount"
				+ "WHERE id=:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		template.update(sql, param);
	}
}
