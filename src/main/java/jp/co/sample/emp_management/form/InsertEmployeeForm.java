package jp.co.sample.emp_management.form;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

public class InsertEmployeeForm {
	
	/** 従業員名 */
	@NotBlank(message="名前を入力してください")
	private String name;
	/** 画像 */
	private MultipartFile formImage;
	/** 性別 */
	private String gender;
	/** 入社日 */
	private String formDate;
	/** メールアドレス */
	@NotBlank(message="メールアドレスを入力してください")
	private String mailAddress;
	/** 郵便番号 */
	@NotBlank(message="郵便番号を入力してください")
	private String zipCode1;
	@NotBlank(message="郵便番号を入力してください")
	private String zipCode2;
	/** 住所 */
	@NotBlank(message="住所を入力してください")
	private String address;
	/** 電話番号 */
	@NotBlank(message="電話番号を入力してください")
	private String telephone1;
	/** 電話番号 */
	@NotBlank
	private String telephone2;
	/** 電話番号 */
	@NotBlank
	private String telephone3;
	/** 給料 */
	@NotBlank(message="給料を入力してください")    
	private String salary;
	/** 特性 */
	@NotBlank(message="特性を入力してください")
	private String characteristics;
	/** 扶養人数 */
	@NotBlank(message="扶養人数を入力してください")
	private String dependentsCount;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MultipartFile getFormImage() {
		return formImage;
	}
	public void setFormImage(MultipartFile formImage) {
		this.formImage = formImage;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getFormDate() {
		return formDate;
	}
	public void setFormDate(String formDate) {
		this.formDate = formDate;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getZipCode1() {
		return zipCode1;
	}
	public void setZipCode1(String zipCode1) {
		this.zipCode1 = zipCode1;
	}
	public String getZipCode2() {
		return zipCode2;
	}
	public void setZipCode2(String zipCode2) {
		this.zipCode2 = zipCode2;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone1() {
		return telephone1;
	}
	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}
	public String getTelephone2() {
		return telephone2;
	}
	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}
	public String getTelephone3() {
		return telephone3;
	}
	public void setTelephone3(String telephone3) {
		this.telephone3 = telephone3;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getCharacteristics() {
		return characteristics;
	}
	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}
	public String getDependentsCount() {
		return dependentsCount;
	}
	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}
}
