package jp.co.sample.emp_management.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.emp_management.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author igamasayuki
 * 
 */
@Repository
public class EmployeeRepository {

	/**
	 * Employeeオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
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

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 従業員一覧情報を入社日順で取得します.
	 * 
	 * @return 全従業員一覧 従業員が存在しない場合はサイズ0件の従業員一覧を返します
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees order by hire_date ";

		List<Employee> developmentList = template.query(sql, EMPLOYEE_ROW_MAPPER);

		return developmentList;
	}
	
	/**
	 * ページの従業員一覧情報を入社日順で取得します.
	 * 
	 * @return 全従業員一覧 従業員が存在しない場合はサイズ0件の従業員一覧を返します
	 */
	public List<Employee> findAllOfPageNumber(Integer page, Integer pageNumber) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees "
				+ " order by hire_date,id limit :limit offset :offset ";
		
		SqlParameterSource param = new MapSqlParameterSource()
				                                 .addValue("limit", pageNumber)
				                                 .addValue("offset", pageNumber*(page-1));
		List<Employee> developmentList = template.query(sql, param, EMPLOYEE_ROW_MAPPER);
		
		return developmentList;
	}

	/**
	 * 曖昧検索から従業員一覧情報を入社日順で取得します.
	 * 
	 * @param 曖昧検索用の文字
	 * @return 全従業員一覧 従業員が存在しない場合はサイズ0件の従業員一覧を返します
	 */
	public List<Employee> findAllOfNameForSearch(String nameForSearch) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees "
				+ " where name like :nameForSearch order by hire_date ;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("nameForSearch", "%" + nameForSearch + "%");
		List<Employee> developmentList = new ArrayList<Employee>();
		developmentList = template.query(sql, param, EMPLOYEE_ROW_MAPPER);
		return developmentList;
	}

	/**
	 * 主キーから従業員情報を取得します.
	 * 
	 * @param id 検索したい従業員ID
	 * @return 検索された従業員情報
	 * @exception 従業員が存在しない場合は例外を発生します
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees WHERE id=:id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Employee development = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

		return development;
	}

	/**
	 * 従業員情報を変更します.
	 */
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

		String updateSql = "UPDATE employees SET dependents_count=:dependentsCount WHERE id=:id";
		template.update(updateSql, param);
	}
	
	public int maxID() {
		System.out.println("maxID");
		String sql = "select max(id) from employees";
		SqlParameterSource param = new MapSqlParameterSource();
		int maxID  =  template.queryForObject(sql, param, Integer.class);
		return maxID;
	}
	
	/**
	 * 従業員情報を追加します.
	 * 
	 * @param employee 入力された従業員情報
	 */
	public void insert(Employee employee) {
		System.out.println("3");
		System.out.println(employee);
		String sql = "insert into employees (id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count)"
			         	+ " values(:id,:name,:image,:gender,:hireDate,:mailAddress,:zipCode,:address,:telephone,:salary,:characteristics,:dependentsCount);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		System.out.println("5");
		template.update(sql, param);
		System.out.println("4");
	}
	
	/**
	 * メールアドレスから従業員情報を取得します.
	 * 
	 * @param mailAddress 検索したい従業員のメールアドレス
	 * @return 検索された従業員情報
	 * @exception 従業員が存在しない場合はnullを返します
	 */
	public Employee  findByMailAddress(String mailAddress) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees WHERE mail_address=:mailAddress";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
		
		List<Employee> employees = template.query(sql, param,EMPLOYEE_ROW_MAPPER);
		if(employees.size()==0) {
			return null;
		}else {
			return employees.get(0);
		}
		
		//Employee development = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
		//return development;
	}
	
}
