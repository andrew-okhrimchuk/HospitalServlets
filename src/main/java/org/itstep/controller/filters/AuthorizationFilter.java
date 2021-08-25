package org.itstep.controller.filters;

import org.itstep.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        System.out.println("AuthorizationFilter start");
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        if (!getPath(req).equals("login") && checkAuthorization(req)) {
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, res);
            System.out.println("checkAuthorization false");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getPath(HttpServletRequest req) {
        String path = req.getRequestURI();
        return path.replaceAll(".*/coffee/", "");
    }

    private boolean checkAuthorization(HttpServletRequest req) {
        String path = getPath(req);
        User.ROLE role = (User.ROLE) req.getSession().getAttribute("role");
        return !path.equals(role.name().toLowerCase());
    }

    @Override
    public void destroy() {
    }
}
