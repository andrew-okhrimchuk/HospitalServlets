package org.itstep.controller.doctor.medicallog;

import org.itstep.config.TemplateEngineUtil;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.entity.MedicationLog;
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

@WebServlet("/doctor/medicationLog/add")
public class AddMedicationLogServlet extends HttpServlet {
    Logger log = Logger.getLogger(AddMedicationLogServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start AddMedicationLogServlet doGet ");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession sess = request.getSession();
        Locale locale = (Locale) sess.getAttribute("locale");

        ResourceBundle resource = ResourceBundle.getBundle("login", locale);
        context.setVariable("hospitallistid", request.getParameter("hospitallistid"));
        context.setVariable("user_id", request.getParameter("user_id"));
        context.setVariable("login", resource);
        context.setVariable("locale", locale.getLanguage());
        engine.process("doctor/medicationLog-add.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start HospitalListServlet doPost");
        WebContext context = new WebContext(request, response, request.getServletContext());
        logParams(request);
        MedicationLog medicationLog = getMedicationLog(request);

        if (medicationLog.isValidAdd()) {
            try {
                MedicationLogService medicationLogService = new MedicationLogService();
                medicationLogService.save(medicationLog);

                response.setStatus(HttpServletResponse.SC_FOUND);//302
                response.setHeader("Location", getMedicationLogPath(request).toString());
                context.setVariable("errorMessage", "Save OK!");
                return;
            } catch (ServiceExeption e) {
                context.setVariable("errorMessage", e.getMessage());
                e.printStackTrace();
            }
        } else log.info("MedicationLog not valid" + medicationLog);
        response.sendRedirect(getStringBuilderAdd(request).toString());
    }

    private StringBuilder getMedicationLogPath(HttpServletRequest request) {
        return new StringBuilder()
                .append("/doctor/medicationLog?user_id=")
                .append(request.getParameter("user_id"))
                .append("&hospitallistid=")
                .append(request.getParameter("hospitallistid"));
    }
    private StringBuilder getStringBuilderAdd(HttpServletRequest request) {
        return new StringBuilder()
                .append("/doctor/medicationLog/add?user_id=")
                .append(request.getParameter("user_id"))
                .append("&hospitallistid=")
                .append(request.getParameter("hospitallistid"));
    }

    private MedicationLog getMedicationLog(HttpServletRequest request) {
        HttpSession sess = request.getSession();

        MedicationLog medicationLog = new MedicationLog();
        medicationLog.setHospitallistid(isThere(request, "hospitallistid") ? Long.valueOf(request.getParameter("hospitallistid")) : null);
        medicationLog.setDescription(isThere(request, "description") ? request.getParameter("description") : null);
        medicationLog.setDateCreate( LocalDateTime.now());
        medicationLog.setDoctorName((String) sess.getAttribute("username"));
        log.info("getMedicationLog = "+ medicationLog);
        return medicationLog;
    }

    private void logParams(HttpServletRequest request) {
        log.info(request.getParameter("hospitallistid"));
        log.info(request.getParameter("description"));
        log.info(request.getParameter("doctorname"));
        log.info(request.getParameter("user_id"));
    }

    private boolean isThere(HttpServletRequest request, String param) {
        return request.getParameter(param) != null && !request.getParameter(param).equals("");
    }

}