package kr.go.rndcall.mgnt.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class MakeExcelServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    protected void performTask(
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response) throws Exception {

        logger.debug(">>> " + this.getClass().getName() + "MakeExcel Start");

        String target = request.getParameter("target");
        String fileName = request.getParameter("fileName");

        javax.servlet.ServletOutputStream servletoutputstream = null;
        try {
            response.setContentType("application/octet-stream");
            response.setHeader(
                    "Content-disposition",
                    "attachment;filename=" + Util.toDB(fileName));
            servletoutputstream = response.getOutputStream();
            MakeExcelFile makeExcelFile = new MakeExcelFile();
            makeExcelFile.makeExcel(target, request, servletoutputstream);
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.println(
                    "<script>alert('File Not Found');history.back();</script>");
            return;
        } finally {
            if (servletoutputstream != null) {
                servletoutputstream.flush();
                servletoutputstream.close();
            }
        }
        logger.debug(">>> " + this.getClass().getName() + "MakeExcel End");

        return;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
            doPost(req, resp);
    }

            protected void doPost(HttpServletRequest req,
                                  HttpServletResponse resp) throws
            ServletException, IOException {
            try {
        performTask(req, resp);
    } catch (Exception e) {
        throw new ServletException(e.getMessage());
    }
    }

}
