<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" %>
<%@ page import="java.io.*" %>
<%
  String crgType = request.getParameter("CRG_TYPE_CD"); 
  String trnKind = request.getParameter("TRN_KIND_CD"); 
  String partCd = request.getParameter("PART_CD"); 
  String autoAni = request.getParameter("AUTO"); 
  if(crgType == null)
	  return;
  if(trnKind == null)
	  return;
  
  if(autoAni == null)
	  autoAni = "0";
    
  String smgFileNm = "";

  if(crgType.equals("01"))
  {
	  smgFileNm = "TC.smg";
  }
  else if(crgType.equals("02"))
  {
	  smgFileNm = "TC.smg";
  }
  else if(crgType.equals("03"))
  {
	  smgFileNm = "M.smg";
  }
  else if(crgType.equals("04"))
  {
	  smgFileNm = "T1.smg";
  }
  else if(crgType.equals("05"))
  {
	  smgFileNm = "T2.smg";
  }
  else if(crgType.equals("P"))
  {
	  smgFileNm = partCd + ".smg";
  }
  
  String sPath = getServletContext().getRealPath("/3d/" + trnKind + "/" + smgFileNm);
  File file = new File(sPath); 
  if (!file.exists())
	  smgFileNm ="";

  %>
<html>
<head>
<title> :: RARP :: </title>

 <script event="EndLoadModel" for="SeemageEmbedded" type="text/javascript">
	    document.SeemageEmbedded.AssySelectionMode(1);
	    if ( window.parent&& window.parent.fnEndLoadModel ) {
			window.parent.fnEndLoadModel(true);
		} else {
			window.dialogArguments.fnEndLoadModel(true);
		}
	    bEndModel = true;

 </script>
 <script event="Click(selection)" for="SeemageEmbedded" language="javascript">  
  	    //fnSelectRow(selection);
</script>
<script>
var partNamePrev = "";
var selPartNamePre = "";
var bEndModel = false;
var partProperties = new Array();
function fnSetProperties(partName)
{
	var part ="<CLitSelection Name=''><CLitModifiable Name='"+partName+"'/></CLitSelection>";
	var properties = document.SeemageEmbedded.GetActorsProperties(part)
	var lenght1 = properties.indexOf("<Actor.Color");
	var substep = properties.slice(lenght1);	//cut the string
	var xposition = 0;
	var yposition = substep.indexOf("/>") ;
	var partColor = substep.substring(xposition,yposition+2);
	partProperties[partName] = partColor;

}
function fnBgParts(partArray)
{
	var partList = "";
	for(var i = 0; i < partArray.length; i++)
	{
		fnSetProperties(partArray[i]);
	    partList = partList + '<CLitModifiable Name="'+partArray[i]+'"><Actor.Color R="255" G="0" B="0"/></CLitModifiable>';
	}
	document.SeemageEmbedded.SetPropertyMap('<CLitPropertyMap>'+partList+'</CLitPropertyMap>');
}
function fnBgDefaultParts(partArray)
{

	var partList = "";
	for(var i = 0; i < partArray.length; i++)
	{
		if((typeof partProperties[partArray[i]]) == "undefined")
			continue;
		
	    partList =  '<CLitModifiable Name="'+partArray[i]+'"></CLitModifiable>';
		document.SeemageEmbedded.RestoreNeutralProperties('<CLitSelection Name="">'+partList+'</CLitSelection>','<CLitPropertySet>'+partProperties[partArray[i]]+'</CLitPropertySet>');
	}

}
function ZoomFitAll()
{
	var collab=document.SeemageEmbedded.GetAllCollaborations();
	document.SeemageEmbedded.ZoomFitAll();
	document.SeemageEmbedded.setvisibility(collab,2,3);
	document.SeemageEmbedded.RefreshScene(1);
}

function fnPartOnly(selection)
{
	document.SeemageEmbedded.setvisibility('<CLitSelection Name="'+selection+'"><CLitModifiable Name="'+selection+'"/></CLitSelection>',1,2);
	var collab=document.SeemageEmbedded.GetAllCollaborations();
	document.SeemageEmbedded.ZoomFitAll();
	document.SeemageEmbedded.setvisibility(collab,2,0);
	document.SeemageEmbedded.RefreshScene(1);
}
function fnPlay()
{
	document.SeemageEmbedded.Play();
}
function fnSelectRow(selection)
{
	var lenght1=selection.indexOf("CLitModifiable Name");
	var substep=selection.slice(lenght1+1);	//cut the string
	var xposition=substep.indexOf('=') ;
	var yposition=substep.indexOf("/>") ;
	var partname= substep.substring(xposition+2,yposition-1);
	if(partname =="")
		return;
	
	if ( window.parent && window.parent.fnSelectRow ) {
		window.parent.fnSelectRow(partname);
	} else {
		window.dialogArguments.fnSelectRow(partname);
	}

}

function fnSelectPart(partName)
{
	
	if((typeof partName) == "undefined")
	{
		return;
	}
	
    if(partNamePrev != partName)
    {
		partNamePrev = partName;
    }
	else
	{
		return;
	}
	    
	var part ='<CLitModifiable Name="'+partName+'"/>' ;

    document.SeemageEmbedded.HighlightedObject ='<CLitSelection Name="">'+part+'</CLitSelection>';
	document.SeemageEmbedded.Selection ='<CLitSelection Name="">'+part+'</CLitSelection>' ;
	
}
function fnShowAll()
{
    document.SeemageEmbedded.UseGUID(1);
	var selection=document.SeemageEmbedded.GetAllActors(0);
	document.SeemageEmbedded.setvisibility(selection,1,1);
	document.SeemageEmbedded.ZoomFitAll();
	var collab=document.SeemageEmbedded.GetAllCollaborations()
	document.SeemageEmbedded.setvisibility(collab,2,0);
	document.SeemageEmbedded.RefreshScene(1);
}

function fnFocusParts(partArray)
{
	var partList = "";
	for(var i = 0; i < partArray.length; i++)
	{
	    partList = partList + '<CLitModifiable Name="'+partArray[i]+'"/>';
	}

	document.SeemageEmbedded.setvisibility('<CLitSelection Name="">'+partList+'</CLitSelection>',1,3);
	var collab=document.SeemageEmbedded.GetAllCollaborations()
	document.SeemageEmbedded.setvisibility(collab,2,0);
	document.SeemageEmbedded.ZoomFitAll();
	document.SeemageEmbedded.RefreshScene(1);
}

function fnFocusPart(partname)
{
	var partList = '<CLitModifiable Name="'+partname+'"/>';

	document.SeemageEmbedded.setvisibility('<CLitSelection Name="">'+partList+'</CLitSelection>',1,3);
	var collab=document.SeemageEmbedded.GetAllCollaborations();
	document.SeemageEmbedded.setvisibility(collab,2,0);
	document.SeemageEmbedded.ZoomFitAll();
	document.SeemageEmbedded.RefreshScene(1);
}
var cabFile = "";
function fnOnload()
{
	if ( window.parent && window.parent.fnEndLoadModel ) {
		window.parent.fnEndLoadModel(false);
	} else {
		window.dialogArguments.fnEndLoadModel(false);
	}
	
	if (navigator.userAgent.indexOf("MSIE") != -1 && navigator.userAgent.indexOf("Win64") != -1 && navigator.userAgent.indexOf("x64") != -1){ 
		cabFile = "3DVIAPlayerActiveX64.cab#version=6,10,1,2085";
	} else { 
		cabFile = "3DVIAPlayerActiveX86.cab#version=6,10,1,2085";
	}
}

</script>
</head>
<body onLoad="javascript:fnOnload();">
<% 
if(!smgFileNm.equals(""))
 {
%>
	<object id="SeemageEmbedded" style="" height="100%" width="100%" classid="CLSID:410B702D-FCFC-46B7-A954-E876C84AE4C0" codebase="<script>document.write(cabFile)</script>">
		<param name="FileName" value="<%=trnKind %>/<%=smgFileNm %>"/>
		<param name="RenderGroundShadow" value="0"/>
		<param name="RenderMode" value="0"/>
		<param name="CameraPlayMode" value="0"/>
		<param name="AntiAliasingOnIdle" value="0"/>
		<param name="GroundGrid" value="0"/>
		<param name="AutoPlay" value="<%=autoAni%>"/>
		<param name="ShowViewBar" value="0"/>
		<param name="ShowDiapoBar" value="0"/>
		<param name="ShowMarkerBar" value="0"/>
		<param name="ShowStandardToolBar" value="0"/>
		<param name="ShowTimeLineBar" value="0"/>
		<param name="ShowMain3DToolBar" value="0"/>
		<param name="ShowCollabToolBar" value="0"/>  
	</object>
<%
 }
else
{
%>
 <img id="viewerImag" src="img.jpg"/>
<%
 }
%>
</body>
</html>
