package org.itstep.controller.admin;

import org.itstep.config.TemplateEngineUtil;
import org.itstep.model.dto.SelectDTO;
import org.itstep.exeption.ServiceExeption;
import org.itstep.model.entity.Patient;
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
import java.util.ResourceBundle;
import java.util.logging.Logger;

@WebServlet("/admin/patients/old")
public class OldPatientsServlet extends HttpServlet {
    Logger log = Logger.getLogger(OldPatientsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Start OldPatientsServlet doGet ");
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
        selectDTO.setShowAllCurrentPatients(false);
        PatientService patientService = new PatientService();
        try {
            List<Patient> patients = patientService.getAll(selectDTO);
            log.info("Find Patients count = " + patients);
            selectDTO.setPatient(patients);
            selectDTO.setCurrentPage(currentPage);
            selectDTO.setPageSize(pageSize);
        } catch (ServiceExeption e) {
            log.info(e.getMessage());
            context.setVariable("errorMessage", e.getMessage());
        }

        ResourceBundle resource = ResourceBundle.getBundle("login", locale);
        context.setVariable("login", resource);
        context.setVariable("selectDTO", selectDTO);
        context.setVariable("locale", locale.getLanguage());
        context.setVariable("df", DateTimeFormatter.ofPattern(locale.getLanguage().equalsIgnoreCase("ru") ? "dd-MM-yyyy" : "MM-dd-yyy"));
        engine.process("admin/patients.html", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
       /* TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        DaoFactory factory = DaoFactory.getInstance();
        UserDao dao = factory.createUserDao();
        context.removeVariable("error");

        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        log.info("username = " + username);
        log.info("pass = " + pass);
        Optional<User> user = dao.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(pass)) {
            Role role = user.get().getRole();
            String template = role.toString().toLowerCase() + ".html";
            context.setVariable("username", user.get().getUsername());
            log.info("username = " + user.get().getUsername());
            log.info("role = " + role);
            log.info("template = " + template);
            engine.process(template, context, response.getWriter());
        } else {
            response.sendRedirect("/login");
        }*/
    }
}