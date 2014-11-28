package kr.go.rndcall.mgnt.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpsRedirectFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest
                && response instanceof HttpServletResponse) {
            HttpServletRequest httpReq = (HttpServletRequest) request;
            String redirectTarget = httpReq.getRequestURL().toString();
            redirectTarget = redirectTarget.replaceFirst("http", "https");
            redirectTarget = redirectTarget.replaceFirst("80", "443");
            if (!request.isSecure()) {
                ((HttpServletResponse) response).sendRedirect(redirectTarget);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        
    }
}