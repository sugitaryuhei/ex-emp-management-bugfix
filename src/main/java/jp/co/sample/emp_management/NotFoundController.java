package jp.co.sample.emp_management;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NotFoundController implements ErrorController {
	
	private static final String PATH = "/error";
			
	@Override
	@RequestMapping(PATH)
	public String getErrorPath() {
		System.out.println("404 not found");
		return "not-found";
	}

}
