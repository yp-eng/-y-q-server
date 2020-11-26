package com.example.myproject.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "myFilter",value = "/*")
public class FilterConfig implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterConfig.class);

    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
        LOGGER.info("=================开始==================");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {



        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.info("=================退出==================");
    }
}
