package com.aps.rarp.co.util;

import org.springframework.stereotype.Component;

@Component
public class SendMailUtil {

//    private static final Logger LOGGER = Logger.getLogger(DspCoSendMailUtil.class);
//
//    protected static EgovPropertyService propertiesService;
//    protected static DspCoLoginSessionManager loginSessionManager;
//    protected static CommonService commonService;
//    
//    @Autowired(required = true)
//    public void setLoginSessionManager(DspCoLoginSessionManager loginSessionManager) {
//        this.loginSessionManager = loginSessionManager;
//    }
//    
//    @Autowired(required = true)
//    public void setPropertiesService(EgovPropertyService propertiesService) {
//        this.propertiesService = propertiesService;
//    }
//    
//    @Autowired(required = true)
//    public void setCommonService(CommonService commonService) {
//        this.commonService = commonService;
//    }
//
//
//    private static Properties makeSMTPProperties() {
//        String host= "";
//        int port;
//
//        host = propertiesService.getString("email_server_host");
//        port = propertiesService.getInt("email_server_port");
//
//        Properties props = new Properties();
//
//        props.put("mail.smtp.starttls.enable", true);         
//        props.put("mail.smtp.host", host);         
//        props.put("mail.smtp.auth", false);  
//        props.put("mail.smtp.user", "");  
//        props.put("mail.smtp.password", "");  
//        props.put("mail.smtp.port", port);  
//
//        return props;
//    }
//  
//    /**
//     * 질문 답변 이메일 HTML 본문 작성 (Type A)
//     * @param String name, Map<String, Object> dataMap
//     * @return html 
//     * @throws Exception
//     */
//    private static String makeHtmlBodyA(Map<String, Object> dataMap) throws Exception{
//        StringBuffer html = new StringBuffer(100000);                   
//        String htmlBodyA = "          <td background=\"http://efamily.scourt.go.kr/images/email/bg_02.gif\" style=\"padding:20px 30px;\" ><ul style=\"list-style: none; margin:0; padding:0;color: #656565; \">"+
//                "            <li style=\"font-weight:bold; color:#0072bc; height:30px;\">"+dataMap.get("name")+"님 안녕하세요. </li>"+
//                "            <li>회원님의 회원가입 시 등록하신 비밀번호찾기 질문/답변은 아래와 같습니다. </li>"+
//                "            <li>"+
//                "              <table width=\"100%\" style=\"border:1px #e2e2e2 solid; margin:15px 0; padding:10px; \">"+
//                "                <tr>"+
//                "                  <th scope=\"col\" style=\"color:#0072bc; font-weight:bold; text-align:left; width:10%; font-size: 12px;  \">질문 :</th>"+
//                "                  <td scope=\"col\" style=\"text-align:left; width:90%;color:#656565; \">"+dataMap.get("ques")+"</td>"+
//                "                </tr>"+
//                "                <tr>"+
//                "                  <th style=\"color:#0072bc; font-weight:bold; text-align:left;  font-size: 12px;\">답변 :</th>"+
//                "                  <td style=\"text-align:left; width:90%; color:#656565;\">"+dataMap.get("ans")+"</td>"+
//                "                </tr>"+
//                "              </table>";
//        html.append(htmlBodyA);
//        
//        return html.toString();
//    }
//    
//    
//    /**
//     * 질문 답변 이메일 HTML 본문 작성 (Type A)
//     * @param String name, Map<String, Object> dataMap
//     * @return html 
//     * @throws Exception
//     */
//    private static String makeHtmlTypeA(String emailCts) throws Exception{
//        String htmlTypeA = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
//                            +"<html xmlns=\"http://www.w3.org/1999/xhtml\" style=\"height: 100%; margin: 0; padding: 0;   background-color:transparent; font-family: Dotum, Arial, AppleGothic,sans-serif; font-size: 12px;\">"
//                            +"<head>"
//                            +"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\" />"
//                            +"<title>대법원 전자가족관계등록시스템 비밀번호 찾기 안내</title>"
//                            +"</head>"
//                            +"<div id=\"email_box\" style=\"margin: 0 auto; width: 600px; border:2px #dddddd solid; color:#656565;\">"
//                            +"  <ul style=\"list-style: none; margin:0; padding:0; \">"
//                            +"    <li style=\"list-style: none; margin:0; \"><img src=\"http://efamily.scourt.go.kr/images/email/email_top.gif\" style=\"margin: 0; padding: 0;\" /></li>"
//                            +"    <li><br />"
//                            +"      <table width=\"550\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">"
//                            +"        <tr>"
//                            +"          <td><img src=\"http://efamily.scourt.go.kr/images/email/bg_01.gif\" width=\"550\" height=\"25\" /></td>"
//                            +"        </tr>"
//                            +"        <tr>"
//                            +emailCts
//                            +"        <li>대법원 전자가족관계등록시스템 홈페이지에서 위의 정보를 입력하시고 <br />"
//                            +"          비밀번호를 변경하시기 바랍니다. <br />"
//                            +"          여러분의 신속하고 편리한 민원업무를 위해 최선을 다하겠습니다.<br />"
//                            +"          감사합니다. </li>"
//                            +"        <li style=\"text-align:center; margin:15px 0 0 0;\"><a href=\"http://efamily.scourt.go.kr\" style=\"cursor: pointer;\"><img src=\"http://efamily.scourt.go.kr/images/email/btn.gif\" border=\"0\" /></a></li>"
//                            +"      </ul></td>"
//                            +"    </tr>"
//                            +"    <tr>"
//                            +"      <td><img src=\"http://efamily.scourt.go.kr/images/email/bg_03.gif\" width=\"550\" height=\"24\" /></td>"
//                            +"    </tr>"
//                            +"    </table>"
//                            +"    </li>"
//                            +"    <li style=\"clear:both; line-height:20px; color:#878787; padding:0 0 20px 35px; margin-top:10px; \">※ 본 메일은 발신전용 메일입니다. <br />"
//                            +"     &nbsp;&nbsp;&nbsp;&nbsp;필요한 사항은 <a href=\"http://efamily.scourt.go.kr\" style=\"color: #3a3a3a; text-decoration: none; cursor: pointer;\">대법원 전자가족관계등록시스템</a>에 접속하여 확인하시기 바랍니다. </li>"
//                            +"  </ul>"
//                            +"</div>"
//                            +"</body>";
//        return htmlTypeA;
//    }
//    
//
//    /**
//     * 신고사건 처리현황 이메일 내용 작성
//     * @param contents
//     * @return
//     * @throws Exception
//     */
//    private static String makeHtmlTypeB(String emailCts) throws Exception{
//        String htmlTypeB = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
//                        +"<html xmlns=\"http://www.w3.org/1999/xhtml\" style=\"height: 100%; margin: 0; padding: 0;   background-color:transparent; font-family: Dotum, Arial, AppleGothic,sans-serif; font-size: 12px;\">"
//                        +"<head>"
//                        +"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\" />"
//                        +"<title>대법원 전자가족관계등록시스템 신고사건 처리현황 안내</title>"
//                        +"</head>"
//                        +"<body style=\"height: 100%; margin: 0; padding: 0;    background-color:transparent; font-family: Dotum, Arial, AppleGothic,sans-serif; font-size: 12px;\">"
//                        +"<div id=\"email_box\" style=\"margin: 0 auto; width: 600px; border:2px #dddddd solid; color:#656565;\">"
//                        +"  <ul style=\"list-style: none; margin:0; padding:0; \">"
//                        +"    <li style=\"list-style: none; margin:0; \"><img src=\"http://efamily.scourt.go.kr/images/email/email_top.gif\" style=\"margin: 0; padding: 0;\" /></li>"
//                        +"    <li><br />"
//                        +"      <table width=\"550\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">"
//                        +"        <tr>"
//                        +"          <td><img src=\"http://efamily.scourt.go.kr/images/email/bg_01.gif\" width=\"550\" height=\"25\" /></td>"
//                        +"        </tr>"
//                        +"        <tr>"
//                        +"          <td background=\"http://efamily.scourt.go.kr/images/email/bg_02.gif\" style=\"padding:20px 30px;\" ><ul style=\"list-style: none; margin:0; padding:0;color: #656565; \">"
//                        +"        <li>"+emailCts+"</li>"
//                        +"      </ul></td>"
//                        +"        </tr>"
//                        +"        <tr>" 
//                        +"            <td background=\"http://efamily.scourt.go.kr/images/email/bg_02.gif\" align=\"center\" style=\"padding:20px 30px;\" ><a href=\"http://efamily.scourt.go.kr\" style=\"cursor: pointer;\"><img src=\"http://efamily.scourt.go.kr/images/email/btn.gif\" border=\"0\" /></a></td>" 
//                        +"        </tr>"
//                        +"        <tr>"
//                        +"      <td><img src=\"http://efamily.scourt.go.kr/images/email/bg_03.gif\" width=\"550\" height=\"24\" /></td>"
//                        +"    </tr>"
//                        +"    </table>"
//                        +"    </li>"
//                        +"    <li style=\"clear:both; line-height:20px; color:#878787; padding:0 0 20px 35px; margin-top:10px; \">※ 본 메일은 발신전용 메일입니다. <br />"
//                        +"     &nbsp;&nbsp;&nbsp;&nbsp;필요한 사항은 <a href=\"http://efamily.scourt.go.kr\" style=\"color: #3a3a3a; text-decoration: none; cursor: pointer;\">대법원 전자가족관계등록시스템</a>에 접속하여 확인하시기 바랍니다. </li>"
//                        +"  </ul>"
//                        +"</div>"
//                        +"</body>"
//                        +"</html>";
//        return htmlTypeB;
//    }
//    
//    
//    /**
//     * 이메일 문서 본문 INSERT
//     * @param char mailType, String address, Map<String, Object> dataMap
//     * @return
//     * @throws Exception
//     */
//    public static boolean makeEmailCts(char mailType, String address, Map<String, Object> dataMap) throws Exception{
//
//        String htmlBody = "";
//        String rgtrId = "";
//        boolean rtnValue = false;
//        Map<String, Object> map = new HashMap<String, Object>();
//        
//        try{
//            rgtrId = loginSessionManager.getSmLoginVO().getUsrId();
//
//            if(rgtrId == null)
//                rgtrId = "system";
//        }catch(Exception e){
//            rgtrId = "system";
//        }
//
//       
//        switch (mailType) {
//        case 'A':
//            if(chkMap(mailType, dataMap))
//                htmlBody = makeHtmlBodyA(dataMap);     // 질문 답변 확인 이메일 내용 작성
//
//            break;
//        case 'B':
//            break;
//        case 'C':
//            break;
//        case 'D':
//            break;
//        default :
//            
//        }
//
//        int htmlBodyLength = htmlBody.length();
//        //insert data
//        map.put("email", address);
//        map.put("emailCts", htmlBody);
//        map.put("emailFg", Character.toString(mailType));
//        map.put("rgtrId", rgtrId);
//        
//        if(htmlBodyLength != 0 && htmlBodyLength < 4000){
//            try{
//                
//                //Email Body Content INSERT
//                commonService.insertEmail(map);
//                rtnValue = true;
//                
//            }catch(Exception e){
//                LOGGER.error(e);
//            }
//        }else{
//            if(htmlBodyLength >= 4000){
//                LOGGER.error("==============================================================");
//                LOGGER.error("Check HTML String length( max : 4000) - " + htmlBody.length());
//                LOGGER.error("==============================================================");
//            }
//            else{
//                LOGGER.error("==============================================================");
//                LOGGER.error("Check HashMap data !!!");
//                LOGGER.error("Key Set : " + dataMap.keySet());
//                LOGGER.error("==============================================================");
//            }
//        }
//        
//        return rtnValue;
//                
//    }
//    
//    
//    /**
//     * 메일전송
//     * @param char, String, String, String
//     * @return
//     * @throws Exception
//     */
//    public static void sendMail(char mailType, String name, String mailAddr2, String emailCts) throws Exception{
//        String mailAddr = mailAddr2.replaceAll("&#64;","@");
//
//        Properties props = makeSMTPProperties();        
//        Session session = Session.getDefaultInstance(props, null);
//
//        InternetAddress address = new InternetAddress(mailAddr, name);
//        
//        
//        MimeMessage message = getMimeMessage(session, address, mailType, emailCts);
//        try {
//            Transport.send(message, message.getAllRecipients());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//    
//    /**
//     * MimeMessage 생성
//     * @param Session, String, InternetAddress, char, String
//     * @return
//     * @throws Exception
//     */
//    private static MimeMessage getMimeMessage(Session session, InternetAddress to, char mailType, String emailCts) throws Exception {
//
//        String title = "";
//        String htmlContents = "";
//        String senderAddr = propertiesService.getString("email_sender_addr");
//        String senderNm   = propertiesService.getString("email_sender_nm");
//        String encoding   = propertiesService.getString("email_encoding");  
//        MimeMessage message = new MimeMessage(session);
//
//        LOGGER.debug(senderNm);
//        LOGGER.debug(senderAddr);
//        LOGGER.debug(encoding);
//
//
//        switch (mailType) {
//        case 'A':
//            title = propertiesService.getString("email_type1_subject");
//            htmlContents = makeHtmlTypeA(emailCts);
//            break;
//        case 'B':
//            title = propertiesService.getString("email_type2_subject");
//            htmlContents = makeHtmlTypeB(emailCts);
//            break;
//        case 'C':
//            title = propertiesService.getString("email_type3_subject");
//            break;
//        case 'D':
//            title = propertiesService.getString("email_type4_subject");
//        default:
//            title = "default message";
//        }
//        
//        
//        
//        try {
//            message.addHeader("Content-Transfer-Encoding","base64");  
//            message.setRecipient(Message.RecipientType.TO, to);
//            message.setFrom(new InternetAddress(senderAddr, senderNm, encoding) );            
//            message.setSubject(title, encoding);
//            message.setContent(htmlContents, "text/html; charset=utf-8"); 
//            message.setSentDate(new Date());
//            message.saveChanges();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return message;
//    }
//    
//    /**
//     * Map에 필요한 Key가 존재하는지 체크
//     * @param char , Map<String, Object>
//     * @return boolean
//     */
//    private static boolean chkMap(char mailType, Map<String, Object> dataMap){
//
//        boolean rtnValue = false;
//        switch(mailType) {
//        case 'A':
//            if(dataMap.containsKey("name") && dataMap.containsKey("ques") && dataMap.containsKey("ans"))
//                rtnValue = true;
//            break;
//        case 'B':
//            break;
//        case 'C':
//            break;
//        case 'D':
//            break;
//        default :
//
//        }
//
//        return rtnValue;
//
//    }

}
