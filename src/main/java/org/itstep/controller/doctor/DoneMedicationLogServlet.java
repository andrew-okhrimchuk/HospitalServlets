package org.itstep.controller.doctor;

import org.itstep.exeption.ServiceExeption;
import org.itstep.model.entity.MedicationLog;
import org.itstep.service.MedicationLogService;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@WebServlet("/doctor/medicationLog/done")
public class DoneMedicationLogServlet extends HttpServlet {
    Logger log = Logger.getLogger(DoneMedicationLogServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start DoneMedicationLogServlet doPost");
        WebContext context = new WebContext(request, response, request.getServletContext());
        logParams(request);
        MedicationLog medicationLog = getMedicationLog(request);

        if (medicationLog.isValidDone()) {
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
        response.sendRedirect(getMedicationLogPath(request).toString());
    }

    private StringBuilder getMedicationLogPath(HttpServletRequest request) {
        return new StringBuilder()
                .append("/doctor/medicationLog?user_id=")
                .append(request.getParameter("user_id"))
                .append("&hospitallistid=")
                .append(request.getParameter("hospitallistid"));
    }


    private MedicationLog getMedicationLog(HttpServletRequest request) {
        HttpSession sess = request.getSession();
        MedicationLog medicationLog = new MedicationLog();
        medicationLog.setId(isThere(request, "medicationlogid") ? Long.valueOf(request.getParameter("medicationlogid")) : null);
        medicationLog.setDateEnd( LocalDateTime.now());
        medicationLog.setExecutor((String) sess.getAttribute("username"));
        return medicationLog;
    }

    private void logParams(HttpServletRequest request) {
        log.info(request.getParameter("hospitallistid"));
        log.info(request.getParameter("user_id"));
    }

    private boolean isThere(HttpServletRequest request, String param) {
        return request.getParameter(param) != null && !request.getParameter(param).equals("");
    }

}