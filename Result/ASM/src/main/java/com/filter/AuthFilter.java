package com.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebFilter("/*")
public class AuthFilter implements Filter {
    Logger logger = Logger.getLogger(AuthFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getServletPath();

        if(path.equals("/")) {resp.sendRedirect("/home"); return;}

        List<String> excludedAbsolutePaths = List.of("/login", "/logout", "/home", "/register");
        for(String excludedPath : excludedAbsolutePaths){
            if(excludedPath.equals(path)) {
                chain.doFilter(request, response);
                return;
            }
        }

        List<String> excludedWildcardPaths = List.of("/assets");
        for(String excludedPath : excludedWildcardPaths){
            if(path.startsWith(excludedPath)) {
                chain.doFilter(request, response);
                return;
            }
        }

        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("user") != null;

        if(loggedIn) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
