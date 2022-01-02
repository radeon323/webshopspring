package com.luxoft.oleksandr_shevchenko.webshop.web.filter;

import com.luxoft.oleksandr_shevchenko.webshop.service.SecurityService;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@WebFilter(filterName = "securityFilter", urlPatterns = "/*", dispatcherTypes = {DispatcherType.REQUEST})
public class SecurityFilter extends GenericFilterBean {

    private final SecurityService securityService;
    private List<String> allowedPaths = List.of("/login", "/logout", "/register");

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)  servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)  servletResponse;
        System.out.println("in security filter");
        String requestURI = httpServletRequest.getRequestURI();
        for (String allowedPath : allowedPaths) {
            if (requestURI.startsWith(allowedPath)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        if (requestURI.equals("/products")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (securityService.isAuth(httpServletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpServletResponse.sendRedirect("/login");
        }

    }



}


