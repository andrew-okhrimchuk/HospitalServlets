package org.itstep.controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.itstep.config.TemplateEngineUtil;
import org.itstep.controller.dao.DaoFactory;
import org.itstep.controller.dao.UserDao;
import org.itstep.model.entity.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@WebServlet("/")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sess=request.getSession();
        Locale locale = (Locale) sess.getAttribute("locale");
        ResourceBundle resource = ResourceBundle.getBundle("login", locale);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext(), locale);
        context.setVariable("login", resource);
        engine.process("index.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DaoFactory factory = DaoFactory.getInstance();
        UserDao dao = factory.createUserDao();

      /*  User user= dao.findByUsername("Amanta Smit");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        context.setVariable("recipient", user.getUsername());
        engine.process("login.html", context, response.getWriter());*/
    }
}