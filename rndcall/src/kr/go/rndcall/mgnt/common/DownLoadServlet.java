package kr.go.rndcall.mgnt.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class DownLoadServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    protected void performTask(
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response) throws Exception {

    	DesCipher dc = new DesCipher();

        String fileNM = request.getParameter("fileNM");
        String filePath = request.getParameter("filePath");
        String saveFileNM = "";
        String desCipher = "";

        if (request.getParameter("desCipher") != null ) desCipher = request.getParameter("desCipher");

        if (filePath != null){
        	filePath = filePath.replaceAll("\\.\\.","");
        	//filePath = filePath.replaceAll("C:/","");
        }
        
        if (request.getParameter("saveFileNM") != null) {
        	if (desCipher.equals("Y")){
        		saveFileNM = dc.Decode(request.getParameter("saveFileNM"));
        		saveFileNM = saveFileNM.replaceAll("\\.\\.","");
        		saveFileNM = saveFileNM.replaceAll("C:/","");
        	} else {
        		saveFileNM = Crypt.decrypt(request.getParameter("saveFileNM"));
        		saveFileNM = saveFileNM.replaceAll("\\.\\.","");
        		saveFileNM = saveFileNM.replaceAll("C:/","");
        	}

        }
//        response.setCharacterEncoding("UTF-8");

        javax.servlet.ServletOutputStream servletoutputstream1 = null;

        try {
            String rootPath = new FileUpload().getContextPath();
            String fullFilePath = rootPath + filePath;
            if(saveFileNM != null && !saveFileNM.equals("")) fullFilePath += File.separator + saveFileNM;
            java.io.File tempFile = new java.io.File(fullFilePath);

            int filesize = (int) tempFile.length();

            String agentType = request.getHeader("Accept-Encoding");

            try {
                if (!tempFile.exists() || !tempFile.canRead()) {
                    PrintWriter out = response.getWriter();
                    out.println(
                            "<script>alert('File Not Found');history.back();</script>");
                    return;
                }
            } catch (Exception e) {
                PrintWriter out = response.getWriter();
                out.println(
                        "<script>alert('File Not Found');history.back();</script>");
                return;
            }

            //response.setContentType("application/octet-stream;");
            response.setContentType("application/smnet;charset=utf-8");
            response.setHeader("Accept-Ranges", "bytes");

            String realFileNm = "";
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0,pre-check=0");
            // char-set에 따라 실 파일명 encoding 처리 (2007-10-26 김종송)
//            System.out.println("fileNM :::: " + fileNM);
//            System.out.println("fileNM :::: " + new String(fileNM.getBytes("utf-8"), Configuration.getInstance().get("conf.download.encoding")));
//            System.out.println("fileNM :::: " + new String(fileNM.getBytes("EUC-KR"), "UTF-8"));
//            System.out.println("fileNM :::: " + URLEncoder.encode(fileNM, "utf-8"));
            response.setHeader(
                    "Content-disposition",
                    "attachment;filename="  + URLEncoder.encode(fileNM, "utf-8") + ";"); 
//                    + new String(fileNM.getBytes("utf-8"), Configuration.getInstance().get("conf.download.encoding"))); 
            servletoutputstream1 = response.getOutputStream();
            dumpFile(tempFile, servletoutputstream1);

        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println(
                    "<script>alert('File Not Found');history.back();</script>");
            return;
        } finally {
            if (servletoutputstream1 != null) {
                servletoutputstream1.flush();
                servletoutputstream1.close();
            }
        }

        return;
    }

    private void dumpFile(File realFile, OutputStream outputstream) {
        byte readByte[] = new byte[1024];
        BufferedInputStream bufferedinputstream = null;
        try {
            bufferedinputstream = new BufferedInputStream(new FileInputStream(realFile));
            int i;
            while ((i = bufferedinputstream.read(readByte, 0, 1024)) != -1) {
                outputstream.write(readByte, 0, i);
            }

        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (bufferedinputstream != null) {
                try {
                    bufferedinputstream.close();
                } catch (IOException ie) {
                }

            }
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        try {
            performTask(req, resp);
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }

}
