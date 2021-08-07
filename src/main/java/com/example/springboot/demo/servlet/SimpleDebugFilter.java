package com.example.springboot.demo.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

/**
 * @author guo
 * @date 2021/8/7
 */
@Slf4j
public class SimpleDebugFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("[SimpleDebugFilter] Request coming ===================");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info("[SimpleDebugFilter] Request Header: {}: {}", headerName, httpServletRequest.getHeader(headerName));
        }

        chain.doFilter(request, response);

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Collection<String> resHeaderNames = httpServletResponse.getHeaderNames();
        for (String resHeaderName : resHeaderNames) {
            log.info("[SimpleDebugFilter] Response Header: {}: {}", resHeaderName, httpServletResponse.getHeader(resHeaderName));
        }

        log.info("[SimpleDebugFilter] Request completed ===================");
    }
}
