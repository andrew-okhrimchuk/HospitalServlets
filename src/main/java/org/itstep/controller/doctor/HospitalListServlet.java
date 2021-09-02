package org.itstep.controller.doctor;

import org.itstep.config.TemplateEngineUtil;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.dto.SelectDTO;
import org.itstep.model.entity.HospitalList;
import org.itstep.model.entity.Patient;
import org.itstep.service.HospitalListService;
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
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

@WebServlet("/doctor/hospital-list/edit")
public class HospitalListServlet extends HttpServlet {
    Logger log = Logger.getLogger(HospitalListServlet.class.getName());
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start HospitalListServlet doGet ");
        HospitalListService hospitalListService = new HospitalListService();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession sess = request.getSession();
        Locale locale = (Locale) sess.getAttribute("locale");
        SelectDTO selectDTO = SelectDTO.newBuilder().build();

        int userId = 0;
        try {
            userId = Integer.parseInt(request.getParameter("user_id"));
            log.info("Find my userId = " + userId);

            Optional<HospitalList> lists = hospitalListService.findByParientIdAndDoctorName(userId, (String) sess.getAttribute("username"));
            log.info("Find my HospitalListService count = " + lists);
            selectDTO.setHospitalList(lists.orElseGet(HospitalList::new));
        } catch (ServiceExeption e) {
            log.info(e.getMessage());
            context.setVariable("errorMessage", e.getMessage());
        }

        ResourceBundle resource = ResourceBundle.getBundle("login", locale);
        context.setVariable("user_id", userId);
        context.setVariable("login", resource);
        context.setVariable("selectDTO", selectDTO);
        context.setVariable("locale", locale.getLanguage());
        context.setVariable("df", DateTimeFormatter.ofPattern(locale.getLanguage().equalsIgnoreCase("ru") ? "dd-MM-yyyy" : "MM-dd-yyy"));
        engine.process("doctor/hospital-list.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start HospitalListServlet doPost");
        WebContext context = new WebContext(request, response, request.getServletContext());
        logParams(request);
        HospitalList hospitalList = getHospitalList(request);

        if (hospitalList.isValid()) {
            try {
                HospitalListService hospitalListService = new HospitalListService();
                hospitalListService.save(hospitalList);

                context.setVariable("message", "Save OK!");
                response.setStatus(HttpServletResponse.SC_FOUND);//302
                response.setHeader("Location", "/doctor/hospital-list/edit?user_id=" + request.getParameter("user_id"));
                return;
            } catch (ServiceExeption e) {
                context.setVariable("errorMessage", e.getMessage());
                e.printStackTrace();
            }
        }

        log.info("hospitalList not valid" + hospitalList);
        response.sendRedirect("/doctor/hospital-list/edit?user_id=" + request.getParameter("user_id"));
    }

    private HospitalList getHospitalList(HttpServletRequest request) {
        HttpSession sess = request.getSession();
        HospitalList hospitalList = new HospitalList();

        hospitalList.setId(isThere(request, "id") ? Long.parseLong(request.getParameter("id")) : null);
        hospitalList.setPrimaryDiagnosis(isThere(request, "primaryDiagnosis") ? request.getParameter("primaryDiagnosis") : null);
        hospitalList.setFinalDiagnosis(isThere(request, "finalDiagnosis") ? request.getParameter("finalDiagnosis") : null);
        hospitalList.setMedicine(isThere(request, "medicine") ? request.getParameter("medicine") : null);
        hospitalList.setOperations(isThere(request, "operations") ? request.getParameter("operations") : null);
        hospitalList.setDateCreate(isThere(request, "dateCreate") ? LocalDateTime.parse(request.getParameter("dateCreate"), df) : LocalDateTime.now());
        hospitalList.setDoctorName((String) sess.getAttribute("username"));
        hospitalList.setPatientId(isThere(request, "user_id") ?
                Patient.newBuilder()
                        .setId(Long.parseLong(request.getParameter("user_id")))
                        .build()
                : null);
        if (hospitalList.getId() == null) {
            hospitalList.setDateCreate(LocalDateTime.now());
        }
        return hospitalList;
    }

    private void logParams(HttpServletRequest request) {
        log.info(request.getParameter("id"));
        log.info(request.getParameter("primaryDiagnosis"));
        log.info(request.getParameter("finalDiagnosis"));
        log.info(request.getParameter("medicine"));
        log.info(request.getParameter("operations"));
        log.info(request.getParameter("user_id"));
    }

    private boolean isThere(HttpServletRequest request, String param) {
        return request.getParameter(param) != null && !request.getParameter(param).equals("");
    }

}