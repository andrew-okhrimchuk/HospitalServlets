package org.itstep.controller.admin;

import org.itstep.config.TemplateEngineUtil;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.dto.DoctorDTO;
import org.itstep.model.dto.SelectDTO;
import org.itstep.model.entity.Doctor;
import org.itstep.model.entity.Patient;
import org.itstep.model.entity.enums.Role;
import org.itstep.model.entity.enums.Speciality;
import org.itstep.service.doctor.DoctorService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

@WebServlet("/admin/doctors")
public class CurrentDoctorsServlet extends HttpServlet {
    Logger log = Logger.getLogger(CurrentDoctorsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start CurrentPatientsServlet doGet ");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession sess = request.getSession();
        Locale locale = (Locale) sess.getAttribute("locale");
        SelectDTO selectDTO =  SelectDTO.newBuilder().build();

        int currentPage = 1;
        int pageSize = 15;

        if (request.getParameter("page1") != null){
            currentPage = Integer.parseInt(request.getParameter("page1"));
        }
        if (request.getParameter("size") != null){
            pageSize = Integer.parseInt(request.getParameter("size"));
        }
        DoctorService doctorService = new DoctorService();
        try {
            List<DoctorDTO> doctors = doctorService.getAll();
            List speciality = Speciality.getAllSpeciality();
            speciality.remove("All");
            log.info("Find Doctor count = " + doctors);
            selectDTO.setDoctors(doctors);
            selectDTO.setCurrentPage(currentPage);
            selectDTO.setPageSize(pageSize);
            selectDTO.setSpecialities(speciality);
        } catch (ServiceExeption  e) {
            log.info(e.getMessage());
            context.setVariable("errorMessage", e.getMessage());
        }

        ResourceBundle resource = ResourceBundle.getBundle("login", locale);
        context.setVariable("login", resource);
        context.setVariable("SelectDTO", selectDTO);
        context.setVariable("locale", locale.getLanguage());
        context.setVariable("df", DateTimeFormatter.ofPattern(locale.getLanguage().equalsIgnoreCase("ru") ? "dd-MM-yyyy" : "MM-dd-yyy"));
        engine.process("admin/doctors.html", context, response.getWriter());
    }
}