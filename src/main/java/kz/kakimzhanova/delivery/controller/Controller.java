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

/**
 * Controller handles client requests
 */
@WebServlet(urlPatterns = "/controller")
public class Controller extends HttpServlet {
    private static Logger logger = LogManager.getLogger();
    private static final String INDEX_PATH = "path.page.index";
    private static final String PARAM_COMMAND = "command";
    private static final String PARAM_NULL_PAGE_ERROR = "nullPage";
    private static final String PARAM_NULL_PAGE_ERROR_MESSAGE = "nullPage.error";
    private static final String PARAM_PAGE_PATH = "pagePath";

    public Controller(){
        super();
    }

    /**
     * init initializes connection pool
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        try {
            ConnectionPool.getInstance().initPoolData();
        } catch (ConnectionPoolException e) {
            logger.log(Level.FATAL, e);
            throw new ServletException("Connection pool was not initialized: " + e);
        }
    }

    /**
     * doGet handles get request
     * @param req-request
     * @param resp-response
     * @see Controller#processGetRequest(HttpServletRequest, HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        try {
            processGetRequest(req, resp);
        }catch (ServletException | IOException e){
            logger.log(Level.ERROR, e);
        }
    }

    /**
     * doPost handles post request
     * @param req - request
     * @param resp - response
     * @see Controller#processPostRequest(HttpServletRequest, HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        try {
            processPostRequest(req, resp);
        }catch (IOException e){
            logger.log(Level.ERROR, e);
        }
    }

    /**
     * processGet request executes command then forwards to received page
     * @param req - request
     * @param resp - response
     * @throws ServletException
     * @throws IOException
     */
    private void processGetRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pagePath;
        String page;
        if (req.getParameterMap().containsKey(PARAM_PAGE_PATH)) {
            pagePath = req.getParameter(PARAM_PAGE_PATH);
        } else {
            Command command = CommandFactory.defineCommand(req.getParameter(PARAM_COMMAND));
            pagePath = command.execute(req);
        }
        if (pagePath != null) {
            page = ConfigurationManager.getString(pagePath);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } else{
            page = ConfigurationManager.getString(INDEX_PATH);
            req.getSession().setAttribute(PARAM_NULL_PAGE_ERROR,PARAM_NULL_PAGE_ERROR_MESSAGE );
            resp.sendRedirect(req.getContextPath() + page);
        }
    }

    /**
     * processPostRequest executes command and then redirects to doGet method, to avoid repeating command execution when user updates the page
     * @param req
     * @param resp
     * @throws IOException
     */
    private void processPostRequest(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        String pagePath;
        String page;
        Command command = CommandFactory.defineCommand(req.getParameter(PARAM_COMMAND));
        pagePath = command.execute(req);
        if (pagePath != null) {
            resp.sendRedirect("controller?pagePath=" + pagePath);
        } else{
            page = ConfigurationManager.getString(INDEX_PATH);
            req.getSession().setAttribute(PARAM_NULL_PAGE_ERROR,PARAM_NULL_PAGE_ERROR_MESSAGE );
            resp.sendRedirect(req.getContextPath() + page);
        }
    }

    /**
     * destroy method disposes connection pool
     */
    @Override
    public void destroy() {
        try {
            ConnectionPool.getInstance().dispose();
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, e);
        }
        super.destroy();
    }
}

