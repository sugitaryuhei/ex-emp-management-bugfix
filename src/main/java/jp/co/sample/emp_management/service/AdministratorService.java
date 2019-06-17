package jp.co.sample.emp_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository administratorRepository;
	
	@Bean
	BCryptPasswordEncoder BCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/**
	 * 管理者情報を登録します. すでにメールアドレスが登録されている場合は0を返します.
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		String encodedPassword = BCryptPasswordEncoder().encode(administrator.getPassword());
		 administrator.setPassword(encodedPassword);
		administratorRepository.insert(administrator);
	}

	public boolean isUsedMailAddress(String mailAddress) {
		Administrator administrator = administratorRepository.findByMailAddress(mailAddress);
		if (administrator == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ログインをします.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return 管理者情報 存在しない場合はnullが返ります
	 */
	public Administrator login(String mailAddress, String passward) {
		Administrator administrator = administratorRepository.findByMailAddress(mailAddress);
		if (BCryptPasswordEncoder().matches(passward, administrator.getPassword())) {
			return administrator;
		}
		return null;
	}
}
