package org.itstep.controller.Command;

import org.itstep.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        // ToDo delete current user (context & session)
        CommandUtility.deleteUserRole(request, User.ROLE.UNKNOWN, "Guest");
        return "redirect:/index.jsp";
    }
}
