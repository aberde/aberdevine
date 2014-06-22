package kr.go.rndcall.mgnt.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ControllerConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import javax.servlet.ServletContext;

public class FileUpload {
    protected String maxSize; //업로드 최대 크기
    protected String contextPath; //컨텍스트의 절대 경로
    protected String filePath; //해당 컨텍스트로 부터 상대경로
    protected String username; //사용자아이디
    protected int uploadCount; //업로드된 파일 개수

    protected FileVO[] fileInfo; //파일업로드에 대한 모든 정보

    protected ActionMapping actionMapping;
    protected ActionForm actionForm;

    public FileUpload() {
    	// Configuration 클래스를 통해서 읽어오도록 수정 (2007-10-26 김종송)
        setContextPath();
    }

    public FileUpload(ActionMapping actionMapping, ActionForm actionForm,
                      HttpServletRequest request) {
        this.actionMapping = actionMapping;
        this.actionForm = actionForm;
        ControllerConfig config = ( (ModuleConfig) actionMapping.
                                   getModuleConfig()).
            getControllerConfig();
        maxSize = config.getMaxFileSize();
        // Configuration 클래스를 통해서 읽어오도록 수정 (2007-10-26 김종송)
        setContextPath();
//        ServletContext context = request.getSession().getServletContext();
//        contextPath = context.getInitParameter("uploadPath");
        
        username = "USER";
    }

    public void service() throws FileNotFoundException, IOException {
        uploadCount = 0;
        MultipartRequestHandler handler = actionForm.getMultipartRequestHandler();
        Hashtable allElements = handler.getAllElements();
        if (allElements.isEmpty()) {
            throw new IOException();
        }
        
        System.out.println("upload service start~~~~~~~~~~~~~~~~~~~~~~~~~");
        
        Hashtable fileElements = handler.getFileElements();
        Enumeration filekeys = fileElements.keys();
        ArrayList filenames = new ArrayList();
        String[] fileExt = {"jsp", "php", "asp", "exe"};
        int exetCount = 0;

        while (filekeys.hasMoreElements()) {
            filenames.add(filekeys.nextElement());
        }

        fileInfo = new FileVO[filenames.size()];

        for (int i = 0; i < filenames.size(); i++) {
            String fileBoxname = (String) filenames.get(i);
            FormFile file = (FormFile) fileElements.get(fileBoxname);
            InputStream fis = null;
            OutputStream fos = null;  
            System.out.println("file.getFileName()::"+file.getFileName());
            try {

                String inputFilename = file.getFileName();                
                String exet = inputFilename.substring(inputFilename.indexOf(".")+1);
                exetCount = 0;
                
                for(int j=0; j < fileExt.length; j++) {
                	if(exet.equals(fileExt[j])) {
                		exetCount++;
                	}
                }
                
                if(exetCount <= 0) { 
	                if (! (inputFilename == null || inputFilename.equals(""))) {
	                    String outputFilename = GenerateFileName.getFileName(inputFilename, username) + "_" + i;
	                    File newFile = new File(contextPath + filePath);                   
	                    if(!newFile.isDirectory()){
	                        newFile.mkdirs();
	                    }
	                    fis = file.getInputStream();
	                    fos = new FileOutputStream(contextPath + filePath + outputFilename);
	                    int bytesRead = 0;
	                    byte[] buffer = new byte[8192];
	                    while ( (bytesRead = fis.read(buffer, 0, 8192)) != -1) {
	                        fos.write(buffer, 0, bytesRead);
	                    }
	                    fileInfo[uploadCount] = new FileVO();
	                    fileInfo[uploadCount].setFileBoxName(fileBoxname);
	                    fileInfo[uploadCount].setInputFileName(inputFilename);
	                    fileInfo[uploadCount].setOutputFileName(outputFilename);
	                    fileInfo[uploadCount].setSize(String.valueOf(file.getFileSize()));
	                    fileInfo[uploadCount].setRelativePath(filePath);
	                    fileInfo[uploadCount].setAbsolutePath(contextPath + filePath);
	                    uploadCount++;
	                }
                }
            } catch (FileNotFoundException ex) {
                // System.out.println(ex.getMessage());
                throw ex;
            } catch (IOException ex) {
                throw ex;
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                    fos = null;
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                    fis = null;
                }
                file.destroy();
            }
        }
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath() {
    	setContextPath("");
    }
    public void setContextPath(String contextPath) {
    	// Configuration 클래스를 통해서 읽어오도록 수정 (2007-10-26 김종송)
    	if (contextPath == null || "".equals(contextPath)) {
        	try {
        		this.contextPath = Configuration.getInstance().get("conf.upload.path");			
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			this.contextPath = contextPath;
    		}    		
    	} else {
    		this.contextPath = contextPath;
    	}
    }

    public FileVO[] getFileInfo() {
        return fileInfo;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMaxSize() {
        return maxSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public int getUploadCount() {
        return uploadCount;
    }
    public void setUploadCount(int uploadCount) {
        this.uploadCount = uploadCount;
    }
    
    public FileVO saveUploadFile(FormFile file, String fileBoxname, int Index) throws FileNotFoundException, IOException {
    	InputStream fis = null;
        OutputStream fos = null;   
        FileVO fileInfo = null;
        String[] fileExt = {"jsp", "php", "asp", "exe"};
        try {

            String inputFilename = file.getFileName();                
            String exet = inputFilename.substring(inputFilename.indexOf(".")+1);
            int exetCount = 0;
            
            for(int j=0; j < fileExt.length; j++) {
            	if(exet.equals(fileExt[j])) {
            		exetCount++;
            	}
            }
            
            if(exetCount <= 0) { 
                if (! (inputFilename == null || inputFilename.equals(""))) {
                    String outputFilename = GenerateFileName.getFileName(inputFilename, username) + "_" + Index;
                    File newFile = new File(contextPath + filePath);                   
                    if(!newFile.isDirectory()){
                        newFile.mkdirs();
                    }
                    fis = file.getInputStream();
                    fos = new FileOutputStream(contextPath + filePath + outputFilename);
                    int bytesRead = 0;
                    byte[] buffer = new byte[8192];
                    while ( (bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                    fileInfo = new FileVO();
                    fileInfo.setFileBoxName(fileBoxname);
                    fileInfo.setInputFileName(inputFilename);
                    fileInfo.setOutputFileName(outputFilename);
                    fileInfo.setSize(String.valueOf(file.getFileSize()));
                    fileInfo.setRelativePath(filePath);
                    fileInfo.setAbsolutePath(contextPath + filePath);                    
                }
            }            
        } catch (FileNotFoundException ex) {            
            throw ex;
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
                fos = null;
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
                fis = null;
            }
            file.destroy();
        }
        return fileInfo;
    }  
    
}
