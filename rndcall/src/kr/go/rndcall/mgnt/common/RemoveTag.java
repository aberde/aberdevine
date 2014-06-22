package kr.go.rndcall.mgnt.common;
/**
 * @(#)RemoveTagTest.java    2003/05/19
 *
 *   To remove tags.
 *   태그 제거하기
 *
 * @resource  http://www.javaclue.org/pub/java/htmltool/removetag/
 * @date      2003/05/19
 * @author    Pilho Kim  (phkim AT cluecom DOT co DOT kr)
 */

public class RemoveTag {
	public static String removeTag(String s) {
	
		// SJPAEK : 20060529 javascript 제거 추가 
	
		String strUpper = "";
		strUpper = s.toUpperCase();
		
		int fromIndex = 0;
		int firstPos = 0;
		int secondPos = 0;
		int nLen = 0;
		String firstData = "";
		String secondData = "";		
		String[] tagScript1 = {"<SCRIPT", "<JAVASCRIPT", "<EMBED", "<STYLE"};
		String[] tagScript2 = {"</SCRIPT>", "</JAVASCRIPT>", "</EMBED>", "</STYLE>"};	
	
		for(int idx=0; idx<tagScript1.length; idx++){
				
			fromIndex = 0;
			while((firstPos = strUpper.indexOf(tagScript1[idx], fromIndex)) != -1){
				
				fromIndex = firstPos;
				if((secondPos = strUpper.indexOf(tagScript2[idx], fromIndex)) != -1){
					
					// upper case 바뀌기
					nLen = strUpper.length();
					firstData = strUpper.substring(0, firstPos - 1);
					secondData = strUpper.substring(secondPos + tagScript2[idx].length() , nLen);	
					strUpper = firstData + secondData;
					
					// 원본 바꾸기
					nLen = s.length();				
					firstData = s.substring(0, firstPos - 1);
					secondData = s.substring(secondPos + tagScript2[idx].length() , nLen);					
					s = firstData + secondData;
					
				}
				else {
					break;
				}
			}
		}		
		// end of javascript   	
	
		// SJPAEK : 20060529 2줄 remark		
		s = s.replaceAll("&lt;","<");
		s = s.replaceAll("&gt;",">");
		s = s.replaceAll(">","> ");

	    final int NORMAL_STATE = 0;
	    final int TAG_STATE = 1;
	    final int START_TAG_STATE = 2;
	    final int END_TAG_STATE = 3;
	    final int SINGLE_QUOT_STATE = 4;
	    final int DOUBLE_QUOT_STATE = 5;
	    int state = NORMAL_STATE;
	    int oldState = NORMAL_STATE;
	    char[] chars = s.toCharArray();
	    StringBuffer sb = new StringBuffer();
	    char a;
	    for (int i = 0; i < chars.length; i++) {
	        a = chars[i];
	        switch (state) {
	            case NORMAL_STATE:
	                if (a == '<')
	                    state = TAG_STATE;
	                else
	                    sb.append(a);
	                break;
	            case TAG_STATE:
	                if (a == '>')
	                    state = NORMAL_STATE;
	                else if (a == '\"') {
	                    oldState = state;
	                    state = DOUBLE_QUOT_STATE;
	                }
	                else if (a == '\'') {
	                    oldState = state;
	                    state = SINGLE_QUOT_STATE;
	                }
	                else if (a == '/')
	                    state = END_TAG_STATE;
	                else if (a != ' ' && a != '\t' && a != '\n' && a != '\r' && a != '\f')
	                    state = START_TAG_STATE;
	                break;
	            case START_TAG_STATE:
	            case END_TAG_STATE:
	                if (a == '>')
	                    state = NORMAL_STATE;
	                else if (a == '\"') {
	                    oldState = state;
	                    state = DOUBLE_QUOT_STATE;
	                }
	                else if (a == '\'') {
	                    oldState = state;
	                    state = SINGLE_QUOT_STATE;
	                }
	                else if (a == '\"')
	                    state = DOUBLE_QUOT_STATE;
	                else if (a == '\'')
	                    state = SINGLE_QUOT_STATE;
	                break;
	            case DOUBLE_QUOT_STATE:
	                if (a == '\"')
	                    state = oldState;
	                break;
	            case SINGLE_QUOT_STATE:
	                if (a == '\'')
	                    state = oldState;
	                break;
	        }
	    }
	     
	    //return (s.toString()).replaceAll("[ ]+", " ");
	
	    String text = sb.toString().replaceAll("\n"," ");
	    text = text.replaceAll("[ ]+", " ");
	    text = text.replaceAll("\n", " "); 
		text = text.replaceAll("\t", " ");
		text = text.replaceAll("&nbsp;", " ");	// SJPAEK : 20060529 nbsp 제거 추가 
		text = text.replaceAll("~", " ");
	
	    return text; 
	}
	
	 
    public static void main(String[] args) {
        String str = "<dn><BODY>Hello <FONT FACE=\"궁서\"><B>진\"하게\"</B> 쓰>기</FONT></BODY>우리";
        System.out.println(RemoveTag.removeTag(str));
    }
    
}

