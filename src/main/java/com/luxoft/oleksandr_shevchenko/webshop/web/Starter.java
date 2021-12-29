//package com.luxoft.oleksandr_shevchenko.webshop.web;
//
//import com.luxoft.oleksandr_shevchenko.webshop.dao.jdbc.JdbcProductDao;
//import com.luxoft.oleksandr_shevchenko.webshop.dao.jdbc.JdbcUserDao;
//import com.luxoft.oleksandr_shevchenko.webshop.service.ProductService;
//import com.luxoft.oleksandr_shevchenko.webshop.service.SecurityService;
//import com.luxoft.oleksandr_shevchenko.webshop.service.UserService;
//import com.luxoft.oleksandr_shevchenko.webshop.web.filter.SecurityFilter;
//import com.luxoft.oleksandr_shevchenko.webshop.web.servlets.*;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.servlet.FilterHolder;
//import org.eclipse.jetty.servlet.ServletContextHandler;
//import org.eclipse.jetty.servlet.ServletHolder;
//import javax.servlet.DispatcherType;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.EnumSet;
//import java.util.List;
//
//public class Starter {
//    public static void main(String[] args) throws Exception {
//
//        JdbcProductDao jdbcProductDao = new JdbcProductDao();
//        JdbcUserDao jdbcUserDao = new JdbcUserDao();
//
//        List<String> userTokens = Collections.synchronizedList(new ArrayList<>());
//
//        ProductService productService = new ProductService(jdbcProductDao);
//        UserService userService = new UserService(jdbcUserDao);
//        SecurityService securityService = new SecurityService(userTokens, userService);
//
//
//        ShowAllProductsServlet showAllProductsServlet = new ShowAllProductsServlet(productService, securityService, userTokens);
//
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//
//        context.addServlet(new ServletHolder(showAllProductsServlet), "/products");
//        context.addServlet(new ServletHolder(showAllProductsServlet), "/*");
//
//        context.addServlet(new ServletHolder(new AddProductServlet(productService, userTokens)), "/products/add");
//        context.addServlet(new ServletHolder(new EditProductServlet(productService, userTokens)), "/products/edit");
//        context.addServlet(new ServletHolder(new LoginServlet(userService, userTokens)), "/login");
//        context.addServlet(new ServletHolder(new LogoutServlet(securityService)), "/logout");
//        context.addServlet(new ServletHolder(new RegisterServlet(userService)), "/register");
//
//        context.addFilter(new FilterHolder(new SecurityFilter(securityService)), "/*", EnumSet.of(DispatcherType.REQUEST));
//
//        Server server = new Server(3000);
//        server.setHandler(context);
//
//        server.start();
//    }
//}