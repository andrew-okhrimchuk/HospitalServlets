package org.itstep.controller.Command;

import org.itstep.model.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class CommandUtility {
    static void setUserRole(HttpServletRequest request,
                     User.ROLE role, String name) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        context.setAttribute("userName", name);
        session.setAttribute("role", role);
    }

    public  static boolean checkUserIsLogged(HttpServletRequest request, String userName){
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        if(loggedUsers.stream().anyMatch(userName::equals)){
            return true;
        }
        loggedUsers.add(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }

    static void deleteUserRole(HttpServletRequest request,
                            User.ROLE role, String name) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        context.setAttribute("userName", name);
      //  session.removeAttribute("role", role);
    }
}
