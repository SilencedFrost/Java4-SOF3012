package com.filter;

import com.dto.UserDTO;
import com.service.LogService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

public class Filter1 implements Filter {
    Logger logger = Logger.getLogger(Filter1.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        logger.info("set encoding");

        HttpServletRequest req = (HttpServletRequest) request;

        req.setAttribute("hello", "This is filter 1");

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
