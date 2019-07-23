package kz.kakimzhanova.project.servlet;

import kz.kakimzhanova.project.command.Command;
import kz.kakimzhanova.project.command.CommandFactory;
import kz.kakimzhanova.project.connection.ConnectionPool;
import kz.kakimzhanova.project.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/controller")
public class Controller extends HttpServlet {
    private static Logger logger = LogManager.getLogger();
    public Controller(){
        super();
    }

    @Override
    public void init() throws ServletException {
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (ConnectionPoolException e) {
            logger.log(Level.WARN, e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            processRequest(req, resp);
        }catch (ServletException | IOException e){
            logger.log(Level.WARN, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            processRequest(req, resp);
        }catch (ServletException | IOException e){
            logger.log(Level.WARN, e);
        }
    }
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        Command command = CommandFactory.defineCommand(req.getParameter("command"));

        page = command.execute(req);

        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } else{
            page = "/index.jsp";
            req.getSession().setAttribute("nullPage", "Message nullpage");
            resp.sendRedirect(req.getContextPath() + page);
        }
    }
    @Override
    public void destroy() {
        super.destroy();
        try {
            ConnectionPool.getInstance().dispose();
        } catch (ConnectionPoolException e) {
            logger.log(Level.WARN, e);
        }
    }
}

