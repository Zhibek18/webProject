package kz.kakimzhanova.delivery.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Security filter forbids user to access to jsp pages directly from browser
 */
@WebFilter(urlPatterns = {"/jsp/*"}, initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class SecurityFilter implements Filter {
    private String indexPath;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter("INDEX_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        response.sendRedirect(request.getContextPath() + indexPath);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        indexPath = null;
    }
}
