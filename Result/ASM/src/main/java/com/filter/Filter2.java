package com.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.logging.Logger;

public class Filter2 implements Filter {
    Logger logger = Logger.getLogger(Filter2.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        logger.info((String) req.getAttribute("hello"));

        chain.doFilter(request, response);
    }
}
