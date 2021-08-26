package org.itstep.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;

@WebFilter(filterName="1", value = "/*")
public class ResourceBundleFilter implements Filter {
    Logger log = Logger.getLogger(ResourceBundleFilter.class.getName());

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        log.info("Start filter2");
        HttpServletRequest req=(HttpServletRequest)request;
        HttpSession sess=req.getSession();

        Locale locale = Locale.ENGLISH;


        if (req.getParameter("lang") != null && req.getParameter("lang").equals("ru")){
            locale= new java.util.Locale("ru");
            sess.setAttribute("locale", locale);
        }
        if (req.getParameter("lang") != null && req.getParameter("lang").equals("en")){
            locale= new java.util.Locale("en");
            sess.setAttribute("locale", locale);
        }
        else if (sess.getAttribute("locale") == null){
            locale= new java.util.Locale("ru");
            sess.setAttribute("locale", locale);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg){
    }
    @Override
    public void destroy() {
    }

}
