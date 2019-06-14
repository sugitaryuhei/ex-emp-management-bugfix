package jp.co.sample.emp_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * 従業員情報を全件取得します.
	 * 
	 * @return　従業員情報一覧
	 */
	public List<Employee> showList() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}
	
	/**
	 * 曖昧検索から従業員一覧情報を入社日順で取得します.
	 * 
	 * @param 　曖昧検索用の文字
	 * @return 全従業員一覧 従業員が存在しない場合はサイズ0件の従業員一覧を返します
	 */
	public List<Employee> showListOfNameForSearch(String nameForSearch) {
		List<Employee> employeeList = employeeRepository.findAllOfNameForSearch(nameForSearch);
		return employeeList;
	}
	
	/**
	 * 従業員情報を取得します.
	 * 
	 * @param id ID
	 * @return 従業員情報
	 * @throws 検索されない場合は例外が発生します
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}
	
	/**
	 * 従業員情報を更新します.
	 * 
	 * @param employee　更新した従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}
	
	/**
	 * メールアドレスが使われているか判断します.
	 * 
	 * @param mailAddress 検索したい従業員のメールアドレス
	 * @return　存在する場合はtrue
	 * 　　　　　　　　存在しない場合はfalse
	 */
	public boolean isUsedMailAddress(String mailAddress) {
		Employee employee = employeeRepository.findByMailAddress(mailAddress);
		if (employee == null) {
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * 従業員情報を追加します.
	 * 
	 * @param employee 入力された従業員情報
	 */
	synchronized public void insert(Employee employee) {
		int maxID = employeeRepository.maxID();
		employee.setId(maxID + 1);
		employeeRepository.insert(employee);
	}
}
