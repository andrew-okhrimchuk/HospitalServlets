package org.itstep.controller.filters;

import org.itstep.controller.Command.CommandUtility;
import org.itstep.controller.dao.UserDAO;
import org.itstep.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        final String name = req.getParameter("name");
        final String password = req.getParameter("pass");

        @SuppressWarnings("unchecked") final AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) req.getServletContext().getAttribute("dao");

        HttpSession session = req.getSession();
        ServletContext context = request.getServletContext();
        System.out.println("AuthenticationFilter start");
        System.out.println(req.getParameter("name"));
        System.out.println(req.getParameter("pass"));
        System.out.println(session);
        System.out.println(session);
        System.out.println(session.getAttribute("role"));
        System.out.println(context.getAttribute("loggedUsers"));

        if (name == null || name.equals("") || password == null || password.equals("")) {
            req.getRequestDispatcher("/login.jsp").forward(req, res);
            return;
        }

        //Logged user.
        if (nonNull(session) &&
                nonNull(session.getAttribute("name")) &&
                nonNull(session.getAttribute("pass"))) {
            System.out.println("Session is exist");
            //New user.
        } else if (dao.get().userIsExist(name, password)) {
            System.out.println("New session");
            final User.ROLE role = dao.get().getRoleByLoginPassword(name, password);
            System.out.println("role = " + role);
            req.getSession().setAttribute("pass", password);
            req.getSession().setAttribute("name", name);
            req.getSession().setAttribute("role", role);
            context.setAttribute("userName", name);

            if (CommandUtility.checkUserIsLogged(req, name)) {
                req.getRequestDispatcher("/error.jsp").forward(req, res);
                return;
            }
            //Enemy user.
        } else {
            System.out.println("Login and pass not exist.");
            req.getRequestDispatcher("/login.jsp").forward(req, res);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
