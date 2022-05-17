package jp.co.sample.repository;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;


/**
 * administratorsテーブルを操作するリポジトリ（Dao）
 * 
 * @author erika
 *
 */

@Repository
public class AdministratorRepository {

	/**
	 * Administratorオブジェクトを生成するRowMapper
	 *
	 */
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 管理者情報を挿入する.
	 * 
	 * @return void
	 */
	public void insert(Administrator administrator) throws SQLException {
		String sql = "INSERT INTO administrators(name, mail_address, password) VALUES(:name, :mailAddress, :password)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		template.update(sql, param);
	}

	/**
	 * メールアドレスとパスワードから、管理者情報を取得する. 1件も存在しない場合は、nullを返す
	 * 
	 * @param mailAddress
	 * @param password
	 * @return Administrator
	 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		String sql = "SELECT id, name, mail_address, password FROM administrators WHERE mail_address=:mailAddress AND password=:password";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress).addValue("password",
				password);
		try {
			return template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);
		} catch (Exception e) {
			return null;
		}
	}

}

