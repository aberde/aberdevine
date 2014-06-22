<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
### 경로   : <%= request.getRequestURI() %>
### 화면명 : 의견 수정
-->
<%@ page contentType="text/html; charset=utf-8"%>

<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/pager-taglib" prefix="pg"%>
<%@ taglib uri="/tags/taglibs-log" prefix="log"%>
<div>
	<textarea name="vo.dis_comment" id="<bean:write name='discussionForm' property='vo.discuss_op_id'/>" cols="67" rows="0" style="overflow-y:auto;text-align:left;height:60px; padding:5px; line-height:20px; color:#666; border:1px #6b84ac solid; vertical-align:top;margin-bottom:3px;"><bean:write name="discussionForm" property="vo.dis_comment"/></textarea>
	<a href="javascript:fncCommentSave('<bean:write name="discussionForm" property="vo.discuss_op_id"/>','<bean:write name="discussionForm" property="vo.p_discuss_op_id"/>')"><img src="../images/btn/btn_Com_save.gif" border="0" style="vertical-align:top;"/></a>
	<logic:notEmpty name="discussionForm" property="voList">
	  <ul style="font-size:12px;font-weight:normal;list-style:none;margin-left:0px;margin-bottom:0px">
	  <logic:iterate name="discussionForm" property="voList" id="repFileVo" indexId="commRowNum1">
	    <li>
		  <a href="javascript:downLoad_Des('<bean:write name="repFileVo" property="file_nm" />','<bean:write name="repFileVo" property="file_path" />','Y')"><img src="/images/icon/disk.gif"/> <span style="font-size:12px;"><bean:write name="repFileVo" property="file_nm" /> (<bean:write name="repFileVo" property="file_size" /> bytes)</span></a>					            	
	 	  <input type="checkbox" name="file_del" value="<bean:write name="repFileVo" property="file_id"/>"/>기존파일삭제<br>
		</li>
	  </logic:iterate>				            
	  </ul>
	</logic:notEmpty>
	<br/>
	<a href="javascript:funcFileAdd('contFileUpdate', '60')" class="btn_TFile"><strong>파일추가</strong></a>
	<a href="javascript:funcFileDel('contFileUpdate')" class="btn_TDel"><strong>파일삭제</strong></a>					
	<div id="contFileUpdate"></div>
</div>