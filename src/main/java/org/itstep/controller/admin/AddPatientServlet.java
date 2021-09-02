package org.itstep.controller.admin;

import org.itstep.config.TemplateEngineUtil;
import org.itstep.dao.DaoFactory;
import org.itstep.model.dto.DoctorDTO;
import org.itstep.model.dto.PatientDTO;
import org.itstep.model.dto.SelectDTO;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.entity.Patient;
import org.itstep.service.DoctorService;
import org.itstep.service.PatientService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

@WebServlet("/admin/patients/add")
public class AddPatientServlet extends HttpServlet {
    Logger log = Logger.getLogger(AddPatientServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start AddPatientServlet doGet ");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession sess = request.getSession();
        Locale locale = (Locale) sess.getAttribute("locale");
        SelectDTO selectDTO = SelectDTO.newBuilder().build();

        int currentPage = 1;
        int pageSize = 15;

        if (request.getParameter("page1") != null) {
            currentPage = Integer.parseInt(request.getParameter("page1"));
        }
        if (request.getParameter("size") != null) {
            pageSize = Integer.parseInt(request.getParameter("size"));
        }
        if (request.getParameter("isSortByDateOfBirth") != null) {
            selectDTO.setSortByDateOfBirth(Boolean.valueOf(request.getParameter("isSortByDateOfBirth")));
        }
        selectDTO.setShowAllCurrentPatients(true);
        PatientService patientService = new PatientService();
        DoctorService doctorService = new DoctorService();
        List<DoctorDTO> doctorDTO = new ArrayList<>();
        try {
            List<Patient> patients = patientService.getAll(selectDTO);
            doctorDTO = doctorService.findAllWithCount();
            log.info("Find Patients count = " + patients);
            selectDTO.setPatient(patients);
            selectDTO.setCurrentPage(currentPage);
            selectDTO.setPageSize(pageSize);
        } catch (ServiceExeption e) {
            log.info(e.getMessage());
            context.setVariable("errorMessage", e.getMessage());
        }

        ResourceBundle resource = ResourceBundle.getBundle("login", locale);
        context.setVariable("user", new Patient());
        context.setVariable("login", resource);
        context.setVariable("add", true);
        context.setVariable("selectDTO", selectDTO);
        context.setVariable("doctorDTO", doctorDTO);

        context.setVariable("locale", locale.getLanguage());
        context.setVariable("df", DateTimeFormatter.ofPattern(locale.getLanguage().equalsIgnoreCase("ru") ? "dd-MM-yyyy" : "MM-dd-yyy"));
        engine.process("admin/patient-add.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start doPost");

        WebContext context = new WebContext(request, response, request.getServletContext());
        DaoFactory factory = DaoFactory.getInstance();
        PatientService patientService = new PatientService();
        logParams(request);
        PatientDTO patientDTO = getPatientDTO(request);

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

    private PatientDTO getPatientDTO(HttpServletRequest request) {
        return PatientDTO.newBuilder()
                .setActualPatient(request.getParameter("isIscurrentpatient").equals("on"))
                .setBirthDate(request.getParameter("dateOfBirth"))
                .setPassword(request.getParameter("Password"))
                .setUsername(request.getParameter("Username"))
                .setDoctorDTO(request.getParameter("doctor") !=null ? DoctorDTO.newBuilder().setId(Long.valueOf(request.getParameter("doctor"))).build() : null)
                .build();
    }

    private void logParams(HttpServletRequest request) {
        log.info(request.getParameter("Username"));
        log.info(request.getParameter("dateOfBirth"));
        log.info(request.getParameter("Password"));
        log.info(request.getParameter("isIscurrentpatient"));
        log.info(request.getParameter("doctor"));
    }
}