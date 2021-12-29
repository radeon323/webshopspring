//package com.luxoft.oleksandr_shevchenko.webshop.web.servlets;
//
//import com.luxoft.oleksandr_shevchenko.webshop.service.SecurityService;
//import jakarta.servlet.http.*;
//
//import java.io.IOException;
//
//public class LogoutServlet extends HttpServlet {
//
//    private final SecurityService securityService;
//
//    public LogoutServlet(SecurityService securityService) {
//        this.securityService = securityService;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String userToken = securityService.getUserToken(req);
//        Cookie cookie = new Cookie("user-token", userToken);
//        cookie.setValue(null);
//        cookie.setMaxAge(0);
//
//        HttpSession session = req.getSession();
//        session.removeAttribute("name");
//        //session.invalidate();
//        resp.addCookie(cookie);
//        resp.sendRedirect("/products");
//    }
//
//}
