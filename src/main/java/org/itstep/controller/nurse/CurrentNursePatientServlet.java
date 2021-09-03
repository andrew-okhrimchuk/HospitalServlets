package org.itstep.controller.nurse;

import org.itstep.config.TemplateEngineUtil;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.dto.SelectDTO;
import org.itstep.model.entity.Nurse;
import org.itstep.model.entity.Patient;
import org.itstep.service.NurseService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

@WebServlet("/nurse/patients")
public class CurrentNursePatientServlet extends HttpServlet {
    Logger log = Logger.getLogger(CurrentNursePatientServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start CurrentNursePatientServlet doGet ");
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        HttpSession sess = request.getSession();
        Locale locale = (Locale) sess.getAttribute("locale");
        SelectDTO selectDTO =  SelectDTO.newBuilder().build();
        String nurseName =((String) sess.getAttribute("username"));
        selectDTO.setShowAllCurrentPatients(true);
        PatientService patientService = new PatientService();
        try {
            List<Patient> patients = patientService.findAllByNurseName(nurseName);
            log.info("Find my Patients count = " + patients);
            selectDTO.setPatient(patients);
        } catch (ServiceExeption  e) {
            log.info(e.getMessage());
            context.setVariable("errorMessage", e.getMessage());
        }

        ResourceBundle resource = ResourceBundle.getBundle("login", locale);
        context.setVariable("login", resource);
        context.setVariable("selectDTO", selectDTO);
        context.setVariable("locale", locale.getLanguage());
        context.setVariable("df", DateTimeFormatter.ofPattern(locale.getLanguage().equalsIgnoreCase("ru") ? "dd-MM-yyyy" : "MM-dd-yyy"));
        engine.process("nurse/patients.html", context, response.getWriter());
    }
}