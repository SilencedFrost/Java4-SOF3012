package com.filter;


import com.dto.UserDTO;
import com.service.LogService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

@WebFilter({"/*"})
public class LogFilter implements Filter {
    Logger logger = Logger.getLogger(LogFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        HttpSession session = req.getSession(false);
        logger.info("trying to get session");

        if(session == null) {chain.doFilter(request, response); return;}

        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        logger.info("trying to get user");

        if(userDTO == null) {chain.doFilter(request, response); return;}

        new LogService().create(req.getRequestURI(), userDTO.getUserId());
        logger.info("log created for user: " + userDTO.getFullName());

        chain.doFilter(request, response);
    }
}
