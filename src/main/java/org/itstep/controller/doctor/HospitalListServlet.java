package org.itstep.controller.doctor;

import org.itstep.config.TemplateEngineUtil;
import org.itstep.dao.DaoFactory;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.dto.DoctorDTO;
import org.itstep.model.dto.PatientDTO;
import org.itstep.model.dto.SelectDTO;
import org.itstep.model.entity.HospitalList;
import org.itstep.service.hospitallist.HospitalListService;
import org.itstep.service.patient.PatientService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

@WebServlet("/doctor/hospital-list/edit")
public class HospitalListServlet extends HttpServlet {
    Logger log = Logger.getLogger(HospitalListServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start HospitalListServlet doGet ");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession sess = request.getSession();
        Locale locale = (Locale) sess.getAttribute("locale");
        SelectDTO selectDTO =  SelectDTO.newBuilder().build();

        HospitalListService hospitalListService = new HospitalListService();
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            Optional<HospitalList> lists = hospitalListService.findByParientIdAndDoctorName( userId,(String) sess.getAttribute("username"));
            log.info("Find my HospitalListService count = " + lists);
            selectDTO.setHospitalList(lists.get());
        } catch (ServiceExeption  e) {
            log.info(e.getMessage());
            context.setVariable("errorMessage", e.getMessage());
        }

        ResourceBundle resource = ResourceBundle.getBundle("login", locale);
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
        DaoFactory factory = DaoFactory.getInstance();
        PatientService patientService = new PatientService();

        log.info(request.getParameter("Username"));
        log.info(request.getParameter("dateOfBirth"));
        log.info(request.getParameter("Password"));
        log.info(request.getParameter("isIscurrentpatient"));
        log.info(request.getParameter("doctor"));

        PatientDTO patientDTO = PatientDTO.newBuilder()
                .setActualPatient(request.getParameter("isIscurrentpatient").equals("on"))
                .setBirthDate(request.getParameter("dateOfBirth"))
                .setPassword(request.getParameter("Password"))
                .setUsername(request.getParameter("Username"))
                .setDoctorDTO(request.getParameter("doctor") !=null ? DoctorDTO.newBuilder().setId(Long.valueOf(request.getParameter("doctor"))).build() : null)
                .build();

        if (patientDTO.isValid()) {
            try {
                patientService.save(patientDTO);
                response.setStatus(HttpServletResponse.SC_FOUND);//302
                response.setHeader("Location", "/admin/patients");
                context.setVariable("errorMessage", "Save OK!");
                return;
            } catch (ServiceExeption e) {
                context.setVariable("errorMessage", e.getMessage());
                e.printStackTrace();
            }
        }
        response.sendRedirect("/admin/patients/add");
    }

}