package org.itstep.controller.doctor;

import org.itstep.config.TemplateEngineUtil;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.dto.SelectDTO;
import org.itstep.model.entity.HospitalList;
import org.itstep.model.entity.MedicationLog;
import org.itstep.model.entity.Patient;
import org.itstep.service.HospitalListService;
import org.itstep.service.MedicationLogService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

@WebServlet("/doctor/medicationLog")
public class MedicationLogServlet extends HttpServlet {
    Logger log = Logger.getLogger(MedicationLogServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start HospitalListServlet doGet ");
        MedicationLogService medicationLogService = new MedicationLogService();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession sess = request.getSession();
        Locale locale = (Locale) sess.getAttribute("locale");
        SelectDTO selectDTO = SelectDTO.newBuilder().build();

        long userId = 0;
        try {
            userId = Long.parseLong(request.getParameter("user_id"));
            log.info("Find my userId = " + userId);

            List<MedicationLog> lists = medicationLogService.findByParientId(userId);
            log.info("Find my MedicationLog count = " + lists);
            selectDTO.setMedicationLog(lists);
        } catch (ServiceExeption e) {
            log.info(e.getMessage());
            context.setVariable("errorMessage", e.getMessage());
        }

        ResourceBundle resource = ResourceBundle.getBundle("login", locale);
        context.setVariable("hospitallistid", request.getParameter("hospitallistid"));
        context.setVariable("user_id", userId);
        context.setVariable("login", resource);
        context.setVariable("SelectDTO", selectDTO);
        context.setVariable("locale", locale.getLanguage());
        context.setVariable("df", DateTimeFormatter.ofPattern(locale.getLanguage().equalsIgnoreCase("ru") ? "dd-MM-yyyy HH:mm" : "MM-dd-yyyy HH:mm"));
        engine.process("doctor/medicationLog.html", context, response.getWriter());
    }
}