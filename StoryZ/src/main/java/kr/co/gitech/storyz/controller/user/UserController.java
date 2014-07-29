package kr.co.gitech.storyz.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.gitech.storyz.common.StoryZConstants;
import kr.co.gitech.storyz.common.message.errorcode.ErrorCode;
import kr.co.gitech.storyz.common.push.MessageContext;
import kr.co.gitech.storyz.common.push.MessageEntry;
import kr.co.gitech.storyz.common.push.MessageEntryImpl;
import kr.co.gitech.storyz.common.push.PushMessage;
import kr.co.gitech.storyz.common.push.PushMessageType;
import kr.co.gitech.storyz.common.push.StoryZMessageService;
import kr.co.gitech.storyz.common.util.CommonUtil;
import kr.co.gitech.storyz.dto.user.CheckEmailDTO;
import kr.co.gitech.storyz.dto.user.CheckIdDTO;
import kr.co.gitech.storyz.dto.user.FindIdDTO;
import kr.co.gitech.storyz.dto.user.UserDTO;
import kr.co.gitech.storyz.exception.StoryZException;
import kr.co.gitech.storyz.service.user.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 사용자 정보
 */
@Controller
@RequestMapping("/user/")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService = null;

	@Autowired
	private StoryZMessageService storyZMessageService = null;

	/**
	 * 아이디 중복 체크(URL : /user/check/id)
	 * @param map
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value=UserUrl.CHECK_ID)
	public void checkId(@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		try {
			// ################################################
			// ## 필수 입력 확인.
			// ################################################
			String[] params = {"user_id"};
			CommonUtil.isRequire(map, params);
			// ################################################
			
			String result = userService.checkId(map);
			
			CheckIdDTO dto = new CheckIdDTO();
			dto.setResult(result);
			
			model.addAttribute("data", dto);
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 이메일 중복 체크(URL : /user/check/email)
	 * @param map
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value=UserUrl.CHECK_EMAIL)
	public void checkEmail(@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		try {
			// ################################################
			// ## 필수 입력 확인.
			// ################################################
			String[] params = {"user_email"};
			CommonUtil.isRequire(map, params);
			// ################################################
			
			String register_yn = userService.checkEmail(map);
			
			CheckEmailDTO dto = new CheckEmailDTO();
			dto.setRegister_yn(register_yn);
			
			model.addAttribute("data", dto);
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 회원 가입(URL : /user/create/profile)
	 */
	@RequestMapping(value = UserUrl.CREATE_PROFILE)
	public void createProfile(@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		try {
			// ################################################
			// ## 필수 입력 확인.
			// ################################################
			String[] params = {"user_email"};
			CommonUtil.isRequire(map, params);
			// ################################################
			
//			String user_key = userService.createProfile(map);
			
//			CheckEmailDTO dto = new CheckEmailDTO();
//			dto.setRegister_yn(user_key);
			
//			model.addAttribute("data", dto);
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 아이디 찾기(URL : /user/find/id)
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value=UserUrl.FIND_ID)
	public void findId(@RequestParam Map<String, Object> map, ModelMap model) throws Exception {
		UserDTO userDTO = userService.findId(map);
		
		if ( userDTO.getUser_id() == null ) {  // 아이디 미존재시
			throw new StoryZException(ErrorCode.USER_NOT_EXIST_ID);
		}
		
		FindIdDTO dto = new FindIdDTO();
		dto.setUser(userDTO);
		
		model.addAttribute("data", dto);
	}
	
	/**
	 * Test
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value="test")
	public void test(ModelMap model) throws Exception {
		List<MessageEntry> entries = new ArrayList<MessageEntry>();
		MessageEntry messageEntry = new MessageEntryImpl();
		messageEntry.setPlatform_kind(StoryZConstants.PLATFORM_KIND_IPHONE);
		messageEntry.setPns_token("5d1cac1bbf3baeb38f116497b79fb55118c5f3aeea36e29577823826e4faaaa2");
		messageEntry.setAlert("Test");
		messageEntry.setBadge(1);
		entries.add(messageEntry);
		
		Map<String, Object> customProperty = new HashMap<String, Object>();
		customProperty.put("chat_room_seq", "1");

		MessageContext context = new MessageContext(entries, customProperty);
		
		PushMessage message = new PushMessage();
		message.setType(PushMessageType.CREATE_CHAT);
		message.setUseBadge(true);
		
		storyZMessageService.send(context, message);
	}
}
