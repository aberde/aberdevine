package kr.go.rndcall.mgnt.common;
/**
 * <PRE>
 * Class  : Constants
 * Comment: Application 에서 사용되는 상수를 정의한 Class
 * 
 * History: 1.0
 * 
 * </PRE>
 * Created on Aug 30, 2005
 * @author: Sungyun.kang
 *          Copyright (C) 2004 by SAMSUNG SDS co.,Ltd. All right reserved.
 */


import org.apache.struts.Globals;

/**
 * 각 항목별로 구분하여 정의하도록 함 
 */
public class Constants
{
	//1. Key Constants (ServletContext 에서 값을 가져올 때 사용하는 Key)
	//=========================================================

	// ServletContext 에 그룹별 메뉴리스트 구분용 접두사(참조용 Key) 
	// "WORK_GROUP + 그룹명" 으로 메뉴리스트를 구분한다. ex) workgroup/admin  
	public static final String WORK_GROUP = "workgroup/";
	public static final String WORK_GROUP_TASK = "workgroupTask/";
	
	// module_list Key (메뉴구성 리스트 참조용 Key))
	public static final String MODULE_LIST = "module_list";
	// task HashMap  (참조용 Key)
	public static final String TASK_MAP = "taskMap";

	// ServletContext 에서 메뉴를 관리하는 menuManager 참조용 Key
	public static final String MENU_MANAGER = "menuManager";


	// Session 에서 사용자 메뉴를 관리하는 sessionMenuManager 참조용 Key
	public static final String SESSION_MANAGER = "sessionMenuManager";
	// Session 에서 다음 Task 페이지를 설정 및 가져올 때 참조용 Key
	public static final String NEXT_TASKID = "nextTaskID";
	// Session 에서 다음 Task 객체를 설정 및 가져올 때 참조용 Key
	public static final String NEXT_TASK_OBJECT = "nextTaskObject";
	// Session 에서 사용자 정보를  가져오거나 저장할 때 사용되는 Key
	public static final String USER_INFO = "user_info";
	// Session 에서 Chart관련 데이터를 가져오거나 저장할 때 사용되는 Key
	public static final String TABLE_DATA = "tableData";
	// Session 에서 Batch관련 데이터를 가져오거나 저장할 때 사용되는 Key	

	// Session 에서 Container관련 데이터를 가져오거나 저장할 때 사용되는 Key	
	public static final String CONTAINER_DATA = "containerData";	
	public static final String PAGE_VO     = "pageVO";	
	public static final String COMMONVO = "commonVO";
	public static final String PROPERTIES = "properties";
	//=========================================================


	//2. return 값 상태 구분용 Constants
	//=========================================================
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	public static final String DBSUCCESS = "dbsuccess";
	public static final String DBDUPLICATE = "dbduplicate";
	public static final String LOGIN_START = "loginStart";
	public static final String LOGIN_FAILURE = "loginFailure";	
	public static final String RESULT_MSG = "resultMsg";
	
	// 사용자에게 해당 권한이 없는 경우  
	public static final String NO_ROLE_PAGE = "NoRolePage";
	// Session Time Out 이 된 상태
	public static final String SESSION_TIMEOUT_PAGE = "TimeOut";

	// 일반적인 조회요청의 경우
	public static final String NORMAL = "normal";
	//=========================================================	

	//3. Exception 구분용 코드 Constants
	//=========================================================
	//properties파일의 Exception출력용 Key값
	public static final String EXCEPTION_KEY = "exception.baseException.ActionException";
	//Action Exception	
	public static final String ACTION_EXCEPTION = "E-Action";
	//Factory Exception
	public static final String FACTORY_EXCEPTION = "E-Factory";
	//Service Exception
	public static final String SERVICE_EXCEPTION = "E-Service";
	//DAO Exception
	public static final String DAO_EXCEPTION = "E-DAO";
	//Custom Tag Exception
	public static final String TAG_EXCEPTION = "E-CustomTag";
	//=========================================================


	//4. File name 상수
	//=========================================================
	// 메뉴구성용 xml파일의 위치
	public static final String MENU_STRUCTURE_URL = "/WEB-INF/xml/menu-structure.xml";
	//left menu가 구성된 frameset 페이지 경로 
	public static final String LEFT_MENU_BODY_PAGE = "/FRAME/BODY/BodyFrameset.jsp";
	//=========================================================
	
	
	//5. 언어구분용
	//=========================================================
	//ServletContext에서 MessageReources를 찾기위한 Key
	public static final String MESSAGE_RESOURCE_KEY = "org.apache.struts.action.MESSAGE";
	//Custom Tag안에서(table, chart)에서 사용할 메세지 키 구분용 접두어 
	public static final String MESSAGE_KEY_PREFIX = "name.";
	//Session에서 locale객체를 얻기 위해 사용하는 Key
	public static final String LOCALE_KEY = Globals.LOCALE_KEY; // "locale_key";	
	public static final String KOREAN = "ko";	
	public static final String ENGLISH = "en";
	public static final int    iKOREAN = 1;	 //한글상태(DAO에서 각 상태별로 구분한다)
	public static final int    iENGLISH = 2; //영문상태
	//=========================================================	
	
	
	//5. FORM 파라미터
	//=========================================================
	public static final String REQUEST_MAP  = "com.rsm.template.Constatnts.REQUEST_MAP";	
	public static final String REQUEST_TYPE = "requestType";
	public static final String ACTION_GUBUN = "actionGubun";
	public static final String COPY_GUBUN   = "copyGubun";
	public static final String TARGET_DATA  = "targetData";
	public static final String CURRENT_PAGE = "currentPage";
	public static final String SELECT_KEY   = "selectKey";
	public static final String CREATE = "C";
	public static final String READ   = "R";
	public static final String UPDATE = "U";
	public static final String DELETE = "D";
	public static final String START  = "S";
	public static final String EXCEL  = "E";
	public static final String MODIFY = "MD";
	public static final String TASK_LIST       = "taskList";
	public static final String CHECK_INIT      = "checkInit";
	public static final String CHECKED         = "on";
	public static final String INIT_PASSWORD   = "12345";
	public static final String MASTER_PASSWORD = "init77";
	public static final String DEPT_SUPPLIER   = "DASUP001";
	//=========================================================
	
	//6. ...
	//=========================================================
	public static final String JNDI_SMILE = "jdbc/smileDS";   //JNDI name
	public static final String JNDI_PDORA = "jdbc/girsDS";    //JNDI name
	public static final String FIRST_TASK = "overallTask100"; //Template로그인 직후 나타나는 첫화면 
	public static final String LOGIN      = "login";
	//=========================================================	

	//7. 일반상수
	//=========================================================	
	public static final String TRUE    = "true";
	public static final String FALSE   = "false";
	
	//page하단에 나타나는 page번호가 나타나는 크기
	public static final int PAGE_SCREEN_SIZE = 10;
	//한페이지에 나타나는 행갯수
	public static final int PAGE_SIZE = 5;
	
	//Template Directory --> 자신의 Application명으로 변경할것!!!
	public static final String DIR_CONTEXT_ROOT = "/template";
	public static final String DIR_IMAGE = "/template/img";
	public static final String DIR_CSS = "/template/css";
}
