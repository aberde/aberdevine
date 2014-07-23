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
	 * 아이디 찾기
	 *   parame : user_email(회원가입 시 등록한 email)
	 *            mobp_no(회원가입 시 등록한 전화번호)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = UserUrl.FIND_ID)
	public void findId(ModelMap model) throws Exception {
		try {
			UserDTO userDTO = userService.findId(model);
			
			if ( userDTO.getUser_id() == null ) {  // 아이디 미존재시
				throw new StoryZException(ErrorCode.USER_NOT_EXIST_ID);
			}
			
			FindIdDTO dto = new FindIdDTO();
			dto.setUser(userDTO);
			
			model.addAttribute("data", dto);
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 아이디 찾기
	 *   parame : user_email(회원가입 시 등록한 email)
	 *            mobp_no(회원가입 시 등록한 전화번호)
	 * 
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "test")
	public void test(ModelMap model) throws Exception {
		List<MessageEntry> entries = new ArrayList<MessageEntry>();
		MessageEntry messageEntry = new MessageEntryImpl();
		messageEntry.setPlatform_kind(StoryZConstants.PLATFORM_KIND_IPHONE);
		messageEntry.setPns_token("cfbb400dc337b20ea84c13df40dbf661928dd53558aa68a7286801187c9487a0");
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
