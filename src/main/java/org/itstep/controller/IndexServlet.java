package org.itstep.controller;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.itstep.config.TemplateEngineUtil;
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
}