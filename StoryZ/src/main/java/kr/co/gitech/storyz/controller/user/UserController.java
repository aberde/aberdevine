package kr.co.gitech.storyz.controller.user;

import java.util.HashMap;

import kr.co.gitech.storyz.common.message.errorcode.ErrorCode;
import kr.co.gitech.storyz.dto.user.FindIdDTO;
import kr.co.gitech.storyz.dto.user.UserDTO;
import kr.co.gitech.storyz.exception.StoryZException;
import kr.co.gitech.storyz.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 사용자 정보
 */
@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private UserService userService = null;

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}
	
	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 아이디 찾기
	 *   parame : user_email(회원가입 시 등록한 email)
	 *            mobp_no(회원가입 시 등록한 전화번호)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = UserUrl.FIND_ID)
	public void findId(ModelMap model) throws Exception {
		HashMap<String, String> map = userService.findId(model);
		
		if (map == null) {  // 아이디 미존재시
			throw new StoryZException(ErrorCode.USER_NOT_EXIST_ID);
		}

		FindIdDTO dto = new FindIdDTO();
		dto.setStatus(0);
		dto.setMessage("성공");
		UserDTO userDTO = new UserDTO();
		userDTO.setUser_id(map.get("ID"));
		userDTO.setUser_name(map.get("NAME"));
		dto.setUser(userDTO);
		model.addAttribute("data", dto);
	}
}
