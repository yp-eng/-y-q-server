package com.example.myproject.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class FilterConfig implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterConfig.class);

    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
        LOGGER.info("=================开始==================");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("=================进入==================");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //处理 不过滤业务
//        if(){
//
//        }
        //放行
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        LOGGER.info("=================退出==================");
    }
}
