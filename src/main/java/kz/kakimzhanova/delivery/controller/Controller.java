package kz.kakimzhanova.delivery.controller;

import kz.kakimzhanova.delivery.command.Command;
import kz.kakimzhanova.delivery.command.CommandFactory;
import kz.kakimzhanova.delivery.command.ConfigurationManager;
import kz.kakimzhanova.delivery.pool.ConnectionPool;
import kz.kakimzhanova.delivery.exception.ConnectionPoolException;
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
            logger.log(Level.FATAL, e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try {
            processRequest(req, resp);
        }catch (ServletException | IOException e){
            logger.log(Level.ERROR, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        try {
            processRequest(req, resp);
        }catch (ServletException | IOException e){
            logger.log(Level.ERROR, e);
        }
    }
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pagePath;
        String page;
        Command command = CommandFactory.defineCommand(req.getParameter("command"));

        pagePath = command.execute(req);

        if (pagePath != null) {
            page = ConfigurationManager.getString(pagePath);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } else{
            page = ConfigurationManager.getString("path.page.index");
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
            logger.log(Level.ERROR, e);
        }
    }
}
