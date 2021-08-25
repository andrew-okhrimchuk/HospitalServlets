package org.itstep.controller.Command;

import org.itstep.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
      /*  System.out.println("LoginCommand execute старт ");
        User.ROLE role = (User.ROLE) request.getSession().getAttribute("role");
        System.out.println(" request.getSession().getAttribute(\"role\") = " + role);
        String roleName = role.name().toLowerCase();

        if (roleName.equals("admin")){
            System.out.println(" admin ");
            return "/WEB-INF/admin/adminbasis.jsp";
        } else if(roleName.equals("user")) {
            System.out.println(" user ");

            return "/WEB-INF/user/userbasis.jsp";
        } else {
            System.out.println(" else ");

            return "/login.jsp";
        }*/
        return null;
    }
    private String getPath(HttpServletRequest req) {
        String path = req.getRequestURI();
        return path.replaceAll(".*/coffee/", "");
    }

}
