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
<ul class="GText-input">
<li class="top">
	<a href="javascript:updateContents('1','1', 'introUpdateArea0')" class="btn_TWrite"><strong>수정</strong></a>
</li>
<li>
<textarea name="vo.dis_contents" id="<bean:write name='discussionForm' property='vo.dis_cont_id'/>" cols="50"  rows="0" style="overflow-y:auto;" ><bean:write name="discussionForm" property="vo.dis_contents"/></textarea>
<a href="javascript:fncContSave3('<bean:write name="discussionForm" property="vo.dis_cont_id"/>', '<bean:write name="discussionForm" property="vo.discuss_id"/>')"><img src="../images/btn/btn_Guide_save.gif" alt=" 안내글 등록" border="0" /></a>	
</li>
<li class="File">
<logic:notEmpty name="discussionForm" property="voList">
	<logic:iterate name="discussionForm" property="voList" id="repFileVo" indexId="commRowNum1">
		<a href="javascript:downLoad_Des('<bean:write name="repFileVo" property="file_nm" />','<bean:write name="repFileVo" property="file_path" />','Y')"><img src="../images/icon/disk.gif"/><img src="../images/icon/icon_arrow.gif" alt="" /> <bean:write name="repFileVo" property="file_nm" /> (<bean:write name="repFileVo" property="file_size" /> bytes)</span></a>
		<input type="checkbox" name="file_del" value="<bean:write name="repFileVo" property="file_id"/>"/>기존파일삭제<br/>
	</logic:iterate>				            
</logic:notEmpty>    
 </li>
 <li>
<a href="javascript:funcFileAdd('contFileUpdate', '100')" class="btn_TFile"><strong>파일추가</strong></a>
<a href="javascript:funcFileDel('contFileUpdate')" class="btn_TDel"><strong>파일삭제</strong></a>
<div id="contFileUpdate"></div>
</li>
</ul>
</div>