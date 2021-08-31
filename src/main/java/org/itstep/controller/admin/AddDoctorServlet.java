package org.itstep.controller.admin;

import org.itstep.config.TemplateEngineUtil;
import org.itstep.dao.DaoFactory;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.dto.DoctorDTO;
import org.itstep.model.dto.PatientDTO;
import org.itstep.model.dto.SelectDTO;
import org.itstep.model.entity.Patient;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

@WebServlet("/admin/doctors/add")
public class AddDoctorServlet extends HttpServlet {
    Logger log = Logger.getLogger(AddDoctorServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start AddDoctorServlet doGet ");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession sess = request.getSession();
        Locale locale = (Locale) sess.getAttribute("locale");
        SelectDTO selectDTO = SelectDTO.newBuilder().build();
        List<String> speciality = Speciality.getAllSpeciality();
        speciality.remove(Speciality.ALL);
        selectDTO.setSpecialities(speciality);

        ResourceBundle resource = ResourceBundle.getBundle("login", locale);
        context.setVariable("user", new DoctorDTO());
        context.setVariable("login", resource);
        context.setVariable("add", true);
        context.setVariable("SelectDTO", selectDTO);
        context.setVariable("locale", locale.getLanguage());
        log.info("context:" + selectDTO + locale.getLanguage());
        engine.process("admin/doctor-add.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start AddDoctorServlet doPost ");

        WebContext context = new WebContext(request, response, request.getServletContext());
        DoctorService doctorService = new DoctorService();

        log.info(request.getParameter("Username"));
        log.info(request.getParameter("Password"));
        log.info(request.getParameter("special"));

        DoctorDTO patientDTO = DoctorDTO.newBuilder()
                .setPassword(request.getParameter("Password"))
                .setUsername(request.getParameter("Username"))
                .setSpeciality(request.getParameter("special") )
                .build();

        if (patientDTO.isValid()) {
            try {
                doctorService.save(patientDTO);
                response.setStatus(HttpServletResponse.SC_FOUND);//302
                response.setHeader("Location", "/admin/doctors");
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