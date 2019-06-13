package jp.co.sample.emp_management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class GloabalExceptionHandler implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(GloabalExceptionHandler.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object, Exception e) {
		logger.error("システムエラーが発生しました！",e);
		return new ModelAndView("redirect:/maintenance");
	}
}
