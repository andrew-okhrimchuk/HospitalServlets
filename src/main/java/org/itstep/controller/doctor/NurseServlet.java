package org.itstep.controller.doctor;

import org.itstep.config.TemplateEngineUtil;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.entity.Nurse;
import org.itstep.service.NurseService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

@WebServlet("/doctor/nurse")
public class NurseServlet extends HttpServlet {
    Logger log = Logger.getLogger(NurseServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start NurseServlet doGet ");
        NurseService medicationLogService = new NurseService();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession sess = request.getSession();
        Locale locale = (Locale) sess.getAttribute("locale");
        List<Nurse> myNurses = new ArrayList();
        List<Nurse> lisrFreeNurses = new ArrayList();
        long userId = 0;
        try {
            userId = Long.parseLong(request.getParameter("user_id"));
            log.info("Find my userId = " + userId);

            myNurses = medicationLogService.findByPatientId(userId);
            log.info("Find my Nurse  = " + myNurses);
            lisrFreeNurses = medicationLogService.findNursesByPatientsIsNotContaining(userId);
            log.info("Find lisrFreeNurses = " + lisrFreeNurses);

        } catch (ServiceExeption e) {
            log.info(e.getMessage());
            context.setVariable("errorMessage", e.getMessage());
        }

        ResourceBundle resource = ResourceBundle.getBundle("login", locale);
        context.setVariable("hospitallistid", request.getParameter("hospitallistid"));
        context.setVariable("myNurses", myNurses);
        context.setVariable("lisrFreeNurses", lisrFreeNurses);
        context.setVariable("user_id", userId);
        context.setVariable("login", resource);
        context.setVariable("locale", locale.getLanguage());
        engine.process("doctor/nurses.html", context, response.getWriter());
    }
}