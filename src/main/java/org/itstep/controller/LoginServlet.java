package org.itstep.controller;

import org.itstep.config.TemplateEngineUtil;
import org.itstep.dao.DaoFactory;
import org.itstep.dao.UserDao;
import org.itstep.model.entity.User;
import org.itstep.model.entity.enums.Role;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    Logger log = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession sess = request.getSession();
        Locale locale = (Locale) sess.getAttribute("locale");

        ResourceBundle resource = ResourceBundle.getBundle("login", locale);
        context.setVariable("login", resource);
        engine.process("login.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebContext context = new WebContext(request, response, request.getServletContext());
        DaoFactory factory = DaoFactory.getInstance();
        UserDao dao = factory.createUserDao();
        context.removeVariable("error");

        Optional<User> user = dao.findByUsername(request.getParameter("username"));
        if (user.isPresent() && user.get().getPassword().equals(request.getParameter("password"))) {
            log.info("Login and pass succes!");
            Role role = user.get().getRole();
            setUserToSessionAndContext(request, context, user.get());

            response.setStatus(HttpServletResponse.SC_FOUND);//302
            response.setHeader("Location", getUrl(role));
        } else {
            log.info("Not found login and pass");
            log.info("Go to Redirect(\"/login\" ");
            response.sendRedirect("/login");
        }
    }

    private void setUserToSessionAndContext(HttpServletRequest request, WebContext context, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole());
        context.setVariable("username", user.getUsername());

        HashSet<String> loggedUsers = (HashSet<String>) session.getServletContext()
                .getAttribute("loggedUsers");
        if (loggedUsers == null) {
            loggedUsers = new HashSet<>();
        }
        loggedUsers.add(user.getUsername());
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
    }

    private String getUrl(Role role) {
        return  new StringBuilder().append("http://localhost:8888/").append(role.name().toLowerCase()).append("/patients").toString();
    }

}