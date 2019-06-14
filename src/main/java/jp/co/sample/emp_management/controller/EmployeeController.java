package jp.co.sample.emp_management.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.emp_management.SaveFileUtil;
import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.form.InsertEmployeeForm;
import jp.co.sample.emp_management.form.UpdateEmployeeForm;
import jp.co.sample.emp_management.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
	}

	@ModelAttribute
	public InsertEmployeeForm insertEmployeeForm() {
		return new InsertEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧画面を出力します.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}

	/**
	 * 曖昧検索を行い従業員一覧画面を出力します.
	 * 
	 * @param nameForSearch 曖昧検索用の文字
	 * @param model         モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/like-search")
	public String likeSearch(String nameForSearch, Model model) {
		List<Employee> employeeList = employeeService.showListOfNameForSearch(nameForSearch);
		if (employeeList.size() == 0) {
			model.addAttribute("message", nameForSearch + "を含む名前の従業員は存在しませんでした");
		}
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id    リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 * 
	 * @param form 従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@RequestMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = new Employee();
		employee.setId(form.getIntId());
		employee.setDependentsCount(form.getIntDependentsCount());
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員情報を追加する
	/////////////////////////////////////////////////////
	@RequestMapping("/toInsert")
	public String toInsert() {
		System.out.println(1);
		return "employee/insert";
	}

	@RequestMapping("/insert")
	public String insert(@Validated InsertEmployeeForm form, BindingResult result, Model model) {
		
		String strDate = form.getFormDate();
		if (strDate.equals("")) {
			result.rejectValue("formDate", null, "入社日を選択してください");
		}

		if (form.getTelephone1().equals("") || form.getTelephone2().equals("") || form.getTelephone3().equals("")) {
			result.reject("telephone1", null, "電話番号を入力してください");
		}
		
		if (form.getFormImage().isEmpty()) {
			result.rejectValue("formImage", null, "画像を選択してください");
		}
		
		if(form.getZipCode1().equals("") || form.getZipCode2().equals("")) {
			result.reject("zipCode1", null, "郵便番号を入力してください");
		}
		
		if(employeeService.isUsedMailAddress(form.getMailAddress())) {
			result.rejectValue("mailAddress", null, form.getMailAddress() + "は既に使われています");
		}

		if (result.hasErrors()) {
			return toInsert();
		}
		
		
		SaveFileUtil save = new SaveFileUtil();
		save.savefile(form.getFormImage());
		String filename = form.getFormImage().getOriginalFilename();
		
		Employee employee = new Employee();
		BeanUtils.copyProperties(form, employee);
		employee.setImage(filename);
		employee.setHireDate(Date.valueOf(strDate));
		String telephone = form.getTelephone1() + "-" + form.getTelephone2() + "-" + form.getTelephone3();
		employee.setTelephone(telephone);
		employee.setZipCode(form.getZipCode1() +"-"+ form.getZipCode2());
		employee.setSalary(Integer.parseInt(form.getSalary()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));

		employeeService.insert(employee);
		return showList(model);
	}
}
