package kz.kakimzhanova.project.servlet;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.command.CommandFactory;
import kz.kakimzhanova.project.entity.Item;

import javax.security.auth.login.Configuration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/controller")
public class Controller extends HttpServlet {
    public Controller(){
        super();
    }

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        processRequest(req, resp);
        //resp.getWriter().print("This is " + this.getClass().getName() + ", using GET method");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        processRequest(req, resp);
//        String input = req.getParameter("input1");
//        input = input.toUpperCase();
//        req.setAttribute("result", input);
//        Item item = new Item(77);
//        req.setAttribute("item1",item);
//        req.getRequestDispatcher("jsp/main.jsp").forward(req, resp);
        //resp.getWriter().print("This is " + this.getClass().getName() + ", using POST method");
    }
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        Command command = CommandFactory.defineCommand(req.getParameter("command"));
        page = command.execute(req);

        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } else{
            page = "index.jsp";
            req.getSession().setAttribute("nullPage", "Message nullpage");
            resp.sendRedirect(req.getContextPath() + page);
        }
    }
    @Override
    public void destroy() {
        super.destroy();
    }
}

