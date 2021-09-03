package org.itstep.controller.doctor.nurse;

import org.itstep.exeption.ServiceExeption;
import org.itstep.service.NurseService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/doctor/nurse/add")
public class AddNurseServlet extends HttpServlet {
    Logger log = Logger.getLogger(AddNurseServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start AddNurseServlet doGet ");
        logParams( request);
        NurseService nurseService = new NurseService();
        long userId = 0;
        long nurseId = 0;
        try {
            userId = Long.parseLong(request.getParameter("user_id"));
            nurseId = Long.parseLong(request.getParameter("nurse_id"));
            nurseService.addPatientToNurse(userId, nurseId);

        } catch (ServiceExeption e) {
            log.info(e.getMessage());
        }

        response.sendRedirect(getNursePath(request).toString());

    }

    private StringBuilder getNursePath(HttpServletRequest request) {
        return new StringBuilder()
                .append("/doctor/nurse?user_id=")
                .append(request.getParameter("user_id"))
                .append("&hospitallistid=")
                .append(request.getParameter("hospitallistid"));
    }

    private void logParams(HttpServletRequest request) {
        log.info(request.getParameter("hospitallistid"));
        log.info(request.getParameter("user_id"));
        log.info(request.getParameter("nurse_id"));
    }
}