package com.hanjin.controller;

import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanjin.bean.ApprovalCountBean;
import com.hanjin.bean.BulletinBoardCountBean;
import com.hanjin.bean.EncryptPasswordBean;
import com.hanjin.bean.GwMailLoginBean;
import com.hanjin.bean.GwMailMailCountBean;
import com.hanjin.bean.MemberInfoBean;
import com.hanjin.bean.SsoLoginBean;
import com.hanjin.dao.ApprovalCountDAO;
import com.hanjin.dao.BulletinBoardCountDAO;
import com.hanjin.dao.EncryptPasswordDAO;
import com.hanjin.dao.GwMailLoginDAO;
import com.hanjin.dao.GwMailMailCountDAO;
import com.hanjin.dao.SsoLoginDAO;

@Controller
@RequestMapping("/memberInfo")
public class MemberInfoController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public MemberInfoBean getMemberInfo(String GlobalID, String paswword, String pasword2, HttpServletResponse response) {
		System.out.println("Creating Member Info");

		MemberInfoBean bean = new MemberInfoBean();

		try {
			// 0단계
			// http://localhost.hanjin.com/hanjin/memberInfo?GlobalID=2004620&paswword=~ksk0070&pasword2=e3xgbicjMic=
			bean.setFailDepth("0단계");
			
			// 1단계 비밀번호 암호화
			// http://localhost.hanjin.com/hanjin/encryptPassword?pwd=~ksk0070
			bean.setFailDepth("1단계 비밀번호 암호화");
			EncryptPasswordDAO encryptPasswordDAO = new EncryptPasswordDAO();
			EncryptPasswordBean encryptPasswordBean = encryptPasswordDAO.getEncryptPassword(paswword);
			String encryptPassword = encryptPasswordBean.getResult().getPwd();
			System.out.println("password : " + paswword);
			System.out.println("encryptPassword : " + encryptPassword);
			
			// 2단계 SSO 로그인
			// http://localhost.hanjin.com/hanjin/ssoLogin?user=2004620&password=L1YJ%2BEZzEORgsgsk0ibw3Q%3D%3D
			bean.setFailDepth("2단계 SSO 로그인");
			SsoLoginDAO ssoLoginDAO = new SsoLoginDAO();
			SsoLoginBean ssoLoginBean = ssoLoginDAO.getSsoLogin(GlobalID, encryptPassword);
			System.out.println("ssoLoginBean.getSMSESSION() : " + ssoLoginBean.getSMSESSION());
			System.out.println("ssoLoginBean.getLuid() : " + ssoLoginBean.getLuid());
			System.out.println("ssoLoginBean.getGuid() : " + ssoLoginBean.getGuid());
			System.out.println("ssoLoginBean.getEmail() : " + ssoLoginBean.getEmail());
			
			// 3단계 GW Mail 로그인
			// http://localhost.hanjin.com/hanjin/gwMailLogin?MailId=2004620&MailPassword=e3xgbicjMic=
			bean.setFailDepth("3단계 GW Mail 로그인");
			GwMailLoginDAO gwMailLoginDAO = new GwMailLoginDAO();
			GwMailLoginBean gwMailLoginBean = gwMailLoginDAO.getGwMailLogin(ssoLoginBean.getLuid(), pasword2);
			String status = gwMailLoginBean.getResult().getStatus();
			System.out.println("status : " + status);
			if ( !"SUCCESS".equals(status) ) {
				throw new Exception();
			}

			// 4단계 GW Mail Mail Count
			// http://localhost.hanjin.com/hanjin/gwMailMailCount?MailId=2004620&MailPassword=e3xgbicjMic=
			bean.setFailDepth("4단계 GW Mail Mail Count");
			GwMailMailCountDAO gwMailMailCountDAO = new GwMailMailCountDAO();
			GwMailMailCountBean gwMailMailCountBean = gwMailMailCountDAO.getGwMailMailCount(ssoLoginBean.getLuid(), pasword2);
			String unReadCount = gwMailMailCountBean.getResult().getBox().getFoldersAll().getUnReadCount();
			System.out.println("unReadCount : " + unReadCount);
			
			
			// 5단계 EMAIL LOGIN
			// http://localhost.hanjin.com/hanjin/emailLogin?userid=barbarkl@hanjin.com
			// TODO 성공/실패 확인.
//			bean.setFailDepth("5단계 EMAIL LOGIN");
//			EmailLoginDAO emailLoginDAO = new EmailLoginDAO();
//			EmailLoginBean emailLoginBean = emailLoginDAO.getEmailLogin(ssoLoginBean.getEmail());
//			String JSESSIONID = emailLoginBean.getResult().getJSESSIONID();
//			System.out.println("JSESSIONID : " + JSESSIONID);
//			if ( !"0".equals(emailLoginBean.getResult().getFail()) ) {
//				throw new Exception(emailLoginBean.getResult().getFail());
//			}
			
			// 6단계 Email Mail Count
			// http://localhost.hanjin.com/hanjin/emailMailCount?dirkey=Inbox_barbarkl&JSESSIONID=???
			// TODO 성공/실패 확인.
//			bean.setFailDepth("6단계 Email Mail Count");
//			String dirkey = "Inbox_" + ssoLoginBean.getEmail().substring(0, ssoLoginBean.getEmail().indexOf("@"));
//			EmailMailCountDAO emailMailCountDAO = new EmailMailCountDAO();
//			EmailMailCountBean emailMailCountBean = emailMailCountDAO.getEmailMailCount(dirkey, JSESSIONID);
//			String newcnt = emailMailCountBean.getResult().getNewcnt();
//			System.out.println("newcnt : " + newcnt);
			
			// 7단계 Approval Count
			// http://localhost.hanjin.com/hanjin/approvalCount?pUserID=2004620
			bean.setFailDepth("7단계 Approval Count");
			ApprovalCountDAO approvalCountDAO = new ApprovalCountDAO();
			ApprovalCountBean approvalCountBean = approvalCountDAO.getApprovalCount(ssoLoginBean.getLuid());
			String approvalCount = approvalCountBean.getRoot().getData().getCNT().getRESULT();
			System.out.println("approvalCount : " + approvalCount);
			
			// 8단계 BulletinBoard Count
			// http://localhost.hanjin.com/hanjin/bulletinBoardCount?pUserID=2004620
			bean.setFailDepth("8단계 BulletinBoard Count");
			BulletinBoardCountDAO bulletinBoardCountDAO = new BulletinBoardCountDAO();
			BulletinBoardCountBean bulletinBoardCountBean = bulletinBoardCountDAO.getBulletinBoardCount(ssoLoginBean.getLuid());
			String bulletinBoardCount = bulletinBoardCountBean.getRoot().getRESULT();
			System.out.println("bulletinBoardCount : " + bulletinBoardCount);
			
			// 9단계 Message Count
			// http://localhost.hanjin.com/hanjin/messageCount?luid=2004620
//			bean.setFailDepth("9단계 Message Count");
//			MessageCountDAO messageCountDAO = new MessageCountDAO();
//			MessageCountBean messageCountBean = messageCountDAO.getMessageCount(ssoLoginBean.getLuid());
//			System.out.println("messageCount : " + messageCountBean.getResult().getMessageCount());

			bean.setFailDepth(null);
			bean.setResult("success");
			bean.setLuid(ssoLoginBean.getLuid());
			bean.setGuid(ssoLoginBean.getGuid());
			bean.setEmail(ssoLoginBean.getEmail());
//			bean.setJSESSIONID(emailLoginBean.getResult().getJSESSIONID());
			bean.setGwMailCount(gwMailMailCountBean.getResult().getBox().getFoldersAll().getUnReadCount());
//			bean.setEmailCount(emailMailCountBean.getResult().getNewcnt());
			bean.setApprovalCount(approvalCountBean.getRoot().getData().getCNT().getRESULT());
			bean.setBulletinBoradCount(bulletinBoardCountBean.getRoot().getRESULT());
//			bean.setMessageCount(messageCountBean.getResult().getMessageCount());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			bean.setResult("fail");
		} finally {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(MemberInfoBean.class);
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				Writer writer = new StringWriter();
				marshaller.marshal(bean, writer);
			
				response.setContentLength(writer.toString().replaceAll("\\n\\s*", "").getBytes().length);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return bean;
	}
}

