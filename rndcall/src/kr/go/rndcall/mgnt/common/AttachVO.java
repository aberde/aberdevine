package kr.go.rndcall.mgnt.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

public class AttachVO implements Serializable{
	
	private String attachSN = "";
    private String boardSN = "";
    private String section = "";
    private String fileNM = "";
    private String filePath = ""; 
    private String saveFileNM = "";
    private String fileSize = "";
    
    public String toString() {
        StringBuffer tempBuffer = new StringBuffer();
        tempBuffer.append("\n [" + this.getClass().getName() + "]");
        Field[] myField = this.getClass().getDeclaredFields();

        for (int i = 0; i < myField.length; i++) {
            try {
                if (myField[i].getType().isArray()) {
                    String className = myField[i].getType().getName();
                    className = className.substring(2, className.length() - 1);

                    tempBuffer.append("\n " + className + "[] ")
                        .append(myField[i].getName())
                        .append(" = " + Arrays.asList( (Object[]) myField[i].get(this)));

                } else if (myField[i].getType().isPrimitive() ||
                           myField[i].getType() == String.class) {

                    String typeName = myField[i].getType().getName();
                    typeName = ( (myField[i].getType() == String.class) ? "String" : typeName);
                    tempBuffer.append("\n " + typeName + " ")
                        .append(myField[i].getName())
                        .append(" = [" + myField[i].get(this) + "]");
                } else if (myField[i].getType() == Class.class) {
                    // ignore
                } else {

                    String className = myField[i].getType().getName();
                    tempBuffer.append("\n " + className + " ")
                        .append(myField[i].getName())
                        .append(" = [" + myField[i].get(this) + "]");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tempBuffer.toString();
    }

	public String getAttachSN() {
		return attachSN;
	}

	public String getBoardSN() {
		return boardSN;
	}

	public String getFileNM() {
		return fileNM;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getFileSize() {
		return fileSize;
	}

	public String getSaveFileNM() {
		return saveFileNM;
	}

	public String getSection() {
		return section;
	}

	public void setAttachSN(String attachSN) {
		this.attachSN = attachSN;
	}

	public void setBoardSN(String boardSN) {
		this.boardSN = boardSN;
	}

	public void setFileNM(String fileNM) {
		this.fileNM = fileNM;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public void setSaveFileNM(String saveFileNM) {
		this.saveFileNM = saveFileNM;
	}

	public void setSection(String section) {
		this.section = section;
	}
 
}
