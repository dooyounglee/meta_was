package com.meta.cmm.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Filter implements javax.servlet.Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.debug("--------------------------------------------------------");
        log.debug("requestURI        : {}", httpRequest.getRequestURI());
		log.debug("method            : {}", httpRequest.getMethod());
		log.debug("contentType       : {}", httpRequest.getContentType());
        // log.debug("query             : {}", httpRequest.getParameterMap());
        log.debug("--------------------------------------------------------");
        
        chain.doFilter(request, response);
    }
    

}
