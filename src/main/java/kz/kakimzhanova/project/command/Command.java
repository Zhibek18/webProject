package kz.kakimzhanova.project.command;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request);
}
