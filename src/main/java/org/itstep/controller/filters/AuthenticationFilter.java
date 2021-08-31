package org.itstep.controller.filters;

import org.itstep.dao.DaoFactory;
import org.itstep.dao.UserDao;
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
            Arrays.asList("", "/login", "/logout", "/register", "/favicon.ico")));

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        log.info("Start filter2" );
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String username = req.getParameter("username");

        String path = getPath(req);
        log.info("current path = " + path);
        boolean allowedPath = ALLOWED_PATHS.contains(path);

        HttpSession session = req.getSession();
        DaoFactory factory = DaoFactory.getInstance();
        UserDao dao = factory.createUserDao();

        log.info("findByUsername = " + dao.findByUsername(username).isPresent());
        log.info("username = " + username);
        log.info("password = " + req.getParameter("password"));

        if (allowedPath) {
            log.info("allowedPath " +  path);

            if (isUserInSession(session) && !path.equalsIgnoreCase("/favicon.ico")) {
                clearSession(session);
            }
            filterChain.doFilter(req, res);
            return;
        }
        else if (isUserInSession(session)) {
            log.info("Session is exist");
            String role = session.getAttribute("role").toString();

            if (checkRule(role, path)) {
                log.info("checkRule" +  path);
                filterChain.doFilter(req, res);
                return;
            }
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

    private boolean isUserInSession(HttpSession session) {
        return nonNull(session) &&
                nonNull(session.getAttribute("username"));
    }

    private void clearSession(HttpSession session){
        String username = (String) session.getAttribute("username");
        session.removeAttribute("username");

        HashSet<String> loggedUsers = (HashSet<String>) session.getServletContext()
                .getAttribute("loggedUsers");
        if (!loggedUsers.isEmpty()) {
            loggedUsers.remove(username);
        }
    }

    static boolean checkUserIsLogged(HttpServletRequest request, String userName){
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        if(loggedUsers.stream().anyMatch(userName::equals)){
            return true;
        }
        loggedUsers.add(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }
}
