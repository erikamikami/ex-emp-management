package jp.co.sample.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

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
	 * 従業員情報を変更する. idカラムを除いた従業員情報全てのカラムを更新する
	 * 
	 * @param employee
	 */
	public void update(Employee employee) {
		String sql = "UPDATE employees SET " + "name=:name" + ",image=:image" + ",gender=:gender"
				+ ",hire_date=:hireDate" + ",mail_address=:mailAddress" + ",zip_code=:zipCode" + ",address=:address"
				+ ",telephone=:telephone" + ",salary=:salary" + ",characteristics=:characteristics"
				+ ",dependents_count=:dependentsCount WHERE id=:id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		template.update(sql, param);
	}

	/**
	 * 従業員一覧に検索フォームを追加 ＜仕様＞ 名前のあいまい検索 入社日 開始日～終了日（期間で検索） 扶養人数 〇人以上 で検索
	 * 
	 * @param name
	 * @param hireDateFrom
	 * @param hireDateTo
	 * @param dependentsCount
	 * @return List<Employee>
	 */
	public List<Employee> search(String name, Date hireDateFrom, Date hireDateTo, Integer dependentsCount) {
		// StringBuilderでSQL文を連結する
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT id, name, image, gender, hire_date, mail_address, zip_code, address, telephone, salary, characteristics, dependents_count FROM employees WHERE ");

		// ブランクかどうか判断するためのフラグ
		boolean nameFlg = false;
		boolean hireDateFromFlg = false;
		boolean hireDateToFlg = false;
		boolean dependentsCountFlg = false;
		boolean andFlg = false;

		SqlParameterSource param = new MapSqlParameterSource();

		// nameがブランクではなかった場合、sql変数にappendする
		// フラグをtrueに変更
		if (!"".equals(name)) {
			sql.append("name LIKE :name");
			nameFlg = true;
			andFlg = true;
			((MapSqlParameterSource) param).addValue("name", "%" + name + "%");
		}

		// hireDateFromを、sql変数にappendする
		// フラグをtrueに変更
			if (andFlg) {
				sql.append(" AND ");
			}
			sql.append("hire_date >= :hireDateFrom");
			hireDateFromFlg = true;
			andFlg = true;
			((MapSqlParameterSource) param).addValue("hireDateFrom", hireDateFrom);


			// hireDateToを、sql変数にappendする
		// フラグをtrueに変更
			if (andFlg) {
				sql.append(" AND ");
			}
			sql.append("hire_date <= :hireDateTo");
			hireDateToFlg = true;
			andFlg = true;
			((MapSqlParameterSource) param).addValue("hireDateTo", hireDateTo);


		// dependentsCountがブランクではなかった場合、sql変数にappendする
		// フラグをtrueに変更
		if (dependentsCount != null) {
			if (andFlg) {
				sql.append(" AND ");
			}
			sql.append("dependents_count >= :dependentsCount");
			dependentsCountFlg = true;
			andFlg = true;
			((MapSqlParameterSource) param).addValue("dependentsCount", dependentsCount);
		}

		String stringSql = sql.toString();
		List<Employee> employees = template.query(stringSql, param, EMPLOYEE_ROW_MAPPER);
		return employees;

	}

}
