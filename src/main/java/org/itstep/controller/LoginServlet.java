package org.itstep.controller;

import org.itstep.config.TemplateEngineUtil;
import org.itstep.controller.dao.DaoFactory;
import org.itstep.controller.dao.UserDao;
import org.itstep.controller.filters.ResourceBundleFilter;
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
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        DaoFactory factory = DaoFactory.getInstance();
        UserDao dao = factory.createUserDao();
        context.removeVariable("error");

        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        log.info("username = " + username);
        log.info("pass = " + pass);
        Optional<User> user = dao.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(pass)) {
            Role role = user.get().getRole();
            String template = role.toString().toLowerCase() + ".html";
            context.setVariable("username", user.get().getUsername());
            log.info("username = " + user.get().getUsername());
            log.info("role = " + role);
            log.info("template = " + template);
            engine.process(template, context, response.getWriter());
        } else {
            response.sendRedirect("/login");
        }
    }
}