package kr.co.gitech.storyz.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.gitech.storyz.common.message.MessageResolver;
import kr.co.gitech.storyz.common.message.errorcode.ErrorCode;
import kr.co.gitech.storyz.dto.common.ExceptionDTO;
import kr.co.gitech.storyz.exception.StoryZException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ControllerAdvice
public class ExceptionHandlingController {

	@ExceptionHandler(value = Exception.class)
	@RequestMapping(value = "/exception")
	public ModelAndView storyZExceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) {
		if ( e instanceof StoryZException ) {
			StoryZException se = (StoryZException)e;
			ExceptionDTO exceptionDTO = new ExceptionDTO();
			exceptionDTO.setStatus(se.getResult().value());
			exceptionDTO.setMessage(se.getLocalizedMessage());
			
			return new ModelAndView().addObject("data", exceptionDTO);
		} else {
			ExceptionDTO exceptionDTO = new ExceptionDTO();
			exceptionDTO.setStatus(ErrorCode.INTERNAL_ERROR.value());
			exceptionDTO.setMessage(MessageResolver.getMessage(ErrorCode.INTERNAL_ERROR.message()));

			return new ModelAndView().addObject("data", exceptionDTO);
		}
	}
}
