package org.itstep.controller.filters;

import org.itstep.controller.dao.DaoFactory;
import org.itstep.controller.dao.UserDao;
import org.itstep.model.entity.User;
import org.itstep.model.entity.enums.Role;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;

@WebFilter(filterName="2", value = "/*")
public class AuthenticationFilter implements Filter {
    Logger log = Logger.getLogger(AuthenticationFilter.class.getName());
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("", "/login", "/logout", "/register")));

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        log.info("Start filter3" );
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String username = req.getParameter("username");
        String pass = req.getParameter("password");

        String path = getPath(req);
        boolean allowedPath = ALLOWED_PATHS.contains(path);

        HttpSession session = req.getSession();
        DaoFactory factory = DaoFactory.getInstance();
        UserDao dao = factory.createUserDao();

        if (allowedPath) {
            log.info("allowedPath" +  path);
            filterChain.doFilter(req, res);
            return;
        }
        else if (nonNull(session) &&
                nonNull(session.getAttribute("username")) &&
                nonNull(session.getAttribute("password"))) {
            log.info("Session is exist");
            if (checkRule(session.getAttribute("role").toString(), path)) {
                log.info("checkRule" +  path);
                filterChain.doFilter(req, res);
                return;
            }
        }
        else if (dao.findByUsername(username).isPresent()) {
            final User user = dao.findByUsername(username).get();
            log.info("role = " + user.getRole());
            if (pass.equals(user.getPassword()) && checkRule(user.getRole().toString(), path)) {
                req.getSession().setAttribute("username", pass);
                req.getSession().setAttribute("password", username);
                req.getSession().setAttribute("role", user.getRole());
                ServletContext context = request.getServletContext();
                context.setAttribute("userName", username);
                filterChain.doFilter(req, res);
                return;
            }
//TODO !!!! выгрузить из контекста !!!
            /*if (CommandUtility.checkUserIsLogged(req, name)) {
                req.getRequestDispatcher("/error.jsp").forward(req, res);
                return;
            }*/
            //Enemy user.
        }
        log.info("Login and pass not exist.");
        res.sendRedirect("/login");
    }

    @Override
    public void destroy() {
    }
    @Override
    public void init(FilterConfig filterConfig) {
    }

    private boolean checkRule(String role, String path) {
        return role.equals(path.toUpperCase());
    }

    private String getPath(HttpServletRequest req) {
        String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");

        if (path.length() > 5 && path.substring(1, 6).contains("admin")) {
            return Role.ADMIN.toString();
        }
        if (path.length() > 5 && path.substring(1, 6).contains("patie")) {
            return Role.PATIENT.toString();
        }
        if (path.length() > 5 && path.substring(1, 6).contains("docto")) {
            return Role.DOCTOR.toString();
        }
        if (path.length() > 5 && path.substring(1, 6).contains("nurse")) {
            return Role.NURSE.toString();
        }
        return path;
    }
}
