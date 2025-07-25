package com.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebFilter({"/", "/user/*", "/admin/*"})
public class AuthFilter implements Filter {
    Logger logger = Logger.getLogger(AuthFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getServletPath();

        if(path.equals("/")) {resp.sendRedirect("/home"); return;}

        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("user") != null;

        if(loggedIn) {
            chain.doFilter(request, response);
        } else {
            if(!path.equals("/logout")) req.getSession().setAttribute("targetUrl", path);
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
